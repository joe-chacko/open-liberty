/*******************************************************************************
 * Copyright (c) 2024 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *******************************************************************************/
package com.ibm.wsspi.threading;

import java.util.concurrent.Callable;

/**
 * Provides a mechanism to implement generic preinvoke and postinvoke logic for
 * all tasks submitted to the Liberty default executor.
 *
 * When a task is submitted to the Liberty default executor for execution, the
 * task will be offered to the wrapWithContext method of an TaskInterceptor
 * in the service registry. The task returned by the wrapWithContext method will
 * replace the original task.
 *
 * If there are additional implementations of TaskInterceptor in
 * the service registry, this process will continue iteratively, with the task
 * returned by the first TaskInterceptor being passed to the next
 * TaskInterceptor until all registered TaskInterceptor implementations are
 * given a chance to wrap the task. The task returned by the final interceptor
 * will be submitted for execution.
 *
 * In the case of multiple implementations of TaskInterceptor,
 * the order in which their wrapWithContext methods are invoked is undefined.
 *
 * Note that use of this interface should generally be avoided. The primary use
 * case is for embedders who have a specific need to get control before and
 * after every single task executed by the application server. This is not a
 * common need, and so careful consideration should be given before registering
 * an implementation.
 *
 */
public interface TaskInterceptor {
    Runnable wrapWithContext(Runnable r, WithContext wc);

    <T> Callable<T> wrapWithContext(Callable<T> c, WithContext wc);
}
