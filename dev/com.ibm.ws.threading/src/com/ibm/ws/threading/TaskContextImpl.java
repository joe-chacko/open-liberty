/*
 * Copyright (c) 2024 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 */
package com.ibm.ws.threading;

import static java.util.Collections.unmodifiableMap;

import java.util.EnumMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.Callable;
import java.util.stream.Stream;

import com.ibm.wsspi.threading.CallableWithContext;
import com.ibm.wsspi.threading.RunnableWithContext;
import com.ibm.wsspi.threading.TaskContext;

public class TaskContextImpl implements TaskContext {
    private final Type type;
    private final Map<Key, String> map;

    TaskContextImpl(TaskContextFactory.Builder b) {
        this.type = b.type;
        this.map = unmodifiableMap(new EnumMap<>(b.map));
    }

    public Runnable wrap(Runnable r) {
        return new RunnableWithContext() {

            @Override
            public Optional<TaskContext> getTaskContext() {
                return Optional.of(TaskContextImpl.this);
            }

            @Override
            public void run() {
                r.run();
            }
        };
    }

    public <T> CallableWithContext<T> wrap(Callable<T> c) {
        return new CallableWithContext<T>() {

            @Override
            public T call() throws Exception {
                return c.call();
            }

            @Override
            public Optional<TaskContext> getTaskContext() {
                return Optional.of(TaskContextImpl.this);
            }
        };
    }

    @Override
    public Type type() {
        return type;
    }

    @Override
    public String get(Key key) {
        return map.get(key);
    }

    @Override
    public Stream<Key> keys() {
        return map.keySet().stream();
    }

}
