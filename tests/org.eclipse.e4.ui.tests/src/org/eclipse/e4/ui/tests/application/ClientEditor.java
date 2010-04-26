/*******************************************************************************
 * Copyright (c) 2010 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 ******************************************************************************/

package org.eclipse.e4.ui.tests.application;

import javax.inject.Inject;

import org.eclipse.e4.ui.model.application.ui.MDirtyable;
import org.eclipse.e4.workbench.ui.Persist;

public class ClientEditor {

	@Inject
	private MDirtyable dirtyable;

	private boolean saveCalled = false;

	private boolean throwException = false;

	public void setThrowException(boolean throwException) {
		this.throwException = throwException;
	}

	@Persist
	void doSave() {
		saveCalled = true;
		if (throwException) {
			throw new RuntimeException();
		}

		dirtyable.setDirty(false);
	}

	public boolean wasSaveCalled() {
		return saveCalled;
	}

}
