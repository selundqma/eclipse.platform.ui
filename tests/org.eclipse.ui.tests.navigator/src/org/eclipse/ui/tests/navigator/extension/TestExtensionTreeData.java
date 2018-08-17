/*******************************************************************************
 * Copyright (c) 2003, 2009 IBM Corporation and others.
 *
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 * IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.ui.tests.navigator.extension;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.eclipse.core.resources.IFile;

public class TestExtensionTreeData {

	private final Map<String, TestExtensionTreeData> children = new HashMap<>();

	private TestExtensionTreeData parent;

	private String name;

	private Properties model;

	private IFile container;

	public TestExtensionTreeData(TestExtensionTreeData aParent, String aName,
			Properties theModel, IFile aFile) {
		parent = aParent;
		name = aName;
		model = theModel;
		container = aFile;
	}

	public TestExtensionTreeData getParent() {
		return parent;
	}

	public TestExtensionTreeData[] getChildren() {
		Set updatedChildren = new HashSet();
		if (model != null) {
			String childrenString = model.getProperty(getName());
			if (childrenString != null) {
				String[] childrenElements = childrenString.split(",");
				for (int i = 0; i < childrenElements.length; i++) {
					if (children.containsKey(childrenElements[i])) {
						updatedChildren.add(children.get(childrenElements[i]));
					} else {
						TestExtensionTreeData newChild = new TestExtensionTreeData(
								this, childrenElements[i], model, container);
						children.put(newChild.getName(), newChild);
						updatedChildren.add(newChild);
					}
				}
			}
		}
		return (TestExtensionTreeData[]) updatedChildren
				.toArray(new TestExtensionTreeData[updatedChildren.size()]);
	}

	public String getName() {
		return name;
	}

	@Override
	public int hashCode() {
		return name.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		return obj instanceof TestExtensionTreeData
				&& ((TestExtensionTreeData) obj).getName().equals(name);
	}

	@Override
	public String toString() {
		StringBuilder toString = new StringBuilder(getName()).append(":");

		toString.append("[");
		// update local children to remove any stale kids
		for (TestExtensionTreeData child : children.values()) {
			toString.append(child.toString());
		}
		toString.append("]");
		return toString.toString();
	}

	/**
	 * @return
	 */
	public IFile getFile() {
		return container;
	}

}
