/*
 * Copyright (c) 2017 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 */
package test.servlet;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.servlet.annotation.WebServlet;

import org.junit.Test;

import com.ibm.wsspi.threading.TaskContext;
import com.ibm.wsspi.threading.TaskContext.Key;
import com.ibm.wsspi.threading.TaskContext.Type;

import componenttest.app.FATServlet;
import test.bundle.TestTaskInterceptor;

@SuppressWarnings("serial")
@WebServlet("/TTCSS")
public class TestTaskContextServiceServlet extends FATServlet {

    @Test
    public void testTestTaskInterceptorIsActivated() {
        assertNotNull("TestTaskInterceptor should have been activated",
                TestTaskInterceptor.getInstance());
    }

    @Test
    public void testTaskContextIsRetrieved() {
        TaskContext taskContext = TestTaskInterceptor.lastContext.get();
        assertNotNull("Workcontext should not be null", taskContext);
    }
    
    @Test
    public void testTaskContextTypeIsHTTP() {
        TaskContext taskContext = TestTaskInterceptor.lastContext.get();
        assertEquals(Type.HTTP, taskContext.type());
    }
    
    @Test
    public void testTaskContextHTTPKeys() {
        TaskContext taskContext = TestTaskInterceptor.lastContext.get();
        assertEquals(4, taskContext.keys().count());
    }
    
    @Test
    public void testTaskContextHTTPKeyExists() {
        TaskContext taskContext = TestTaskInterceptor.lastContext.get();
        assertTrue((taskContext.keys().filter(key -> key == Key.INBOUND_HOSTNAME)).findFirst().isPresent());
    }
}
