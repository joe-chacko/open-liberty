/*******************************************************************************
 * Copyright (c) 2024 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *******************************************************************************/
package com.ibm.ws.threading;

import static java.util.Objects.requireNonNull;

import java.util.EnumMap;

import org.osgi.framework.ServiceReference;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import com.ibm.wsspi.threading.TaskContext;
import com.ibm.wsspi.threading.TaskContext.Key;
import com.ibm.wsspi.threading.TaskContext.Type;
import com.ibm.wsspi.threading.TaskInterceptor;

/**
 *
 */
@Component(service = TaskContextFactory.class)
public class TaskContextFactory {

	@Activate
	public TaskContextFactory(@Reference ServiceReference<TaskInterceptor> s) {

	}

	public static class Builder {
		final Type type;
		final EnumMap<Key, String> map = new EnumMap<>(Key.class);

		Builder(Type type) {
			this.type = type;
		}

		public Builder set(Key key, String value) {
			Object existing = map.putIfAbsent(requireNonNull(key), value);
			if (null != existing) {
				throw new IllegalStateException("Cannot overwrite existing value '" + existing + "' for key '" + key
												+ "' with new value '" + value + "'");
			}
			return this;
		}

		public TaskContext build() {
			return new TaskContextImpl(this);
		}
	}

	public TaskContextFactory.Builder forType(Type type) {
		return new TaskContextFactory.Builder(type);
	}

}
