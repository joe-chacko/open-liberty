/*
 * Copyright (c) 2024 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 */
package com.ibm.ws.threading.internal;

import java.util.Optional;
import java.util.function.Consumer;

import org.osgi.framework.ServiceReference;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import com.ibm.ws.threading.TaskContextFactory;
import com.ibm.wsspi.threading.ExecutorServiceTaskInterceptor;
import com.ibm.wsspi.threading.TaskContext;
import com.ibm.wsspi.threading.TaskContext.Type;
import com.ibm.wsspi.threading.TaskContextService;

@Component
public class TaskContextServiceImpl implements TaskContextFactory, TaskContextService {
	private final ThreadLocal<TaskContext> ctx;

	@Activate
	// In order for this component to be activated provide an
	// ExecutorServiceTaskInterceptor
	public TaskContextServiceImpl(@Reference ServiceReference<ExecutorServiceTaskInterceptor> required,
			@Reference TaskContextHolder holder) {
		this.ctx = holder;
	}

	/**
	 * This is used for testing only (therefore not public)
	 */
	TaskContextServiceImpl() {
		this(null, new TaskContextHolder());
	}

	@Override
	public TaskContext getTaskContext() {
		return ctx.get();
	}

	@Override
	public TaskContextZapper create(Type type, Consumer<TaskContextSetter> action) {
		Optional.ofNullable(ctx.get()).ifPresent(tc -> {
			throw new IllegalStateException("Cannot create context if it already exists. Existing context of type "
											+ tc.type() + " must be zapped first.");
		});
		TaskContextImpl tc = new TaskContextImpl(type);
		ctx.set(tc);
		action.accept(tc);
		return ctx::remove;
	}

	/**
	 * This component allows the TaskContext state to be shared/persisted across
	 * TaskContextServiceImpl instances
	 */
	@Component(service = TaskContextHolder.class)
	public static class TaskContextHolder extends ThreadLocal<TaskContext> {

	}
}
