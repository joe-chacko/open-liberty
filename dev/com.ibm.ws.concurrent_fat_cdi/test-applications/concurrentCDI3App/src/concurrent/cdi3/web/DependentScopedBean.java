/*******************************************************************************
 * Copyright (c) 2017,2021 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package concurrent.cdi3.web;

import jakarta.enterprise.context.Dependent;

@Dependent
public class DependentScopedBean {
    private boolean value;

    public boolean getBoolean() {
        return value;
    }

    public void setBoolean(boolean value) {
        this.value = value;
    }
}
