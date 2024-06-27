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

import static org.junit.Assert.assertNotNull;
import javax.servlet.annotation.WebServlet;

import org.junit.Test;

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
    public void testHttpContext() {
        System.out.println("Testing HTTP");
    }

}
