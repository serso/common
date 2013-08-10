/*
 * Copyright 2013 serso aka se.solovyev
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 * Contact details
 *
 * Email: se.solovyev@gmail.com
 * Site:  http://se.solovyev.org/java/jcl
 */

package org.solovyev.common.collections.tree;

import javax.annotation.Nonnull;

/**
 * User: serso
 * Date: 8/10/13
 * Time: 6:11 PM
 */
class PrefixTree {

	@Nonnull
	private PrefixTreeNode root;

	PrefixTree() {
		this.root = PrefixTreeNode.newEmptyInstance();
	}

	public void addString(@Nonnull String s) {
		root.addString(s, 0);
	}

	public boolean containsString(@Nonnull String s) {
		return root.containsString(s, 0);
	}

	@Nonnull
	public PrefixTreeNode getRoot() {
		return root;
	}
}
