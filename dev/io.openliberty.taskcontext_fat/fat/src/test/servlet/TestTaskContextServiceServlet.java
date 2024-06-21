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

import javax.servlet.annotation.WebServlet;

import org.junit.Test;

import componenttest.app.FATServlet;

@SuppressWarnings("serial")
@WebServlet("/TTCSS")
public class TestTaskContextServiceServlet extends FATServlet {
    // @Test
    // public void testHttpServletRequest(HttpServletRequest request,
    // HttpServletResponse resp) throws Exception {
    // resp.getWriter().println("Running test method 'testHttpServletRequest'");
    // }
    //
    // @Test
    // public void testTestServiceIsActivated() {
    // assertNotNull("TestService should have been activated",
    // TestService.getInstance());
    // }
    //
    // @Test
    // public void testTestServiceHasBeenInjectedWithTaskContextService() {
    // assertEquals("TestService should have exactly one TaskContextService", 1,
    // TestService.getInstance().contextProviders.size());
    // }
    //
    // @Test
    // public void testTaskContextNotAvailableInServlet() {
    // assertNull("TaskContext should be null when retrieved from a servlet",
    // TestService.getInstance().contextProviders.iterator().next().getTaskContext());
    // }

    @Test
    public void testHttpContext() {
        System.out.println("Testing HTTP");
    }

}
