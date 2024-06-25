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

import java.util.function.Consumer;

import com.ibm.wsspi.threading.TaskContext;
import com.ibm.wsspi.threading.TaskContext.Key;

/**
 *
 */
public interface TaskContextFactory {
	interface TaskContextSetter {
		/**
		 * Set a piece of context information for a task, if it is not already set.
		 *
		 * @param key   represents the type of information being supplied
		 * @param value the piece of context information, must not be <code>null</code>
		 * @return this object
		 */
		TaskContextSetter set(Key key, String value);

	}

	interface TaskContextZapper extends AutoCloseable {
		@Override
		void close();
	}

	/**
	 * Create the task context for the current thread and pass a way to create it.
	 * Using command/query separation pattern
	 *
	 * @param type the type of context
	 * @return an interface that allows the context to be populated
	 * @throws IllegalStateException if a task context exists for this thread
	 */
	TaskContextZapper create(TaskContext.Type type, Consumer<TaskContextSetter> action);
}
