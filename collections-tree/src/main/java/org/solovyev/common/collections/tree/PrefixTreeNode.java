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
import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;

/**
 * User: serso
 * Date: 8/10/13
 * Time: 6:11 PM
 */
final class PrefixTreeNode {

	private static final Character EMPTY_CHARACTER = Character.valueOf((char) 0);

	private final Map<Character, PrefixTreeNode> children = new HashMap<Character, PrefixTreeNode>();

	@Nonnull
	private Character character;

	@Nonnull
	private String string;

	private boolean lastCharacter;

	private PrefixTreeNode() {
	}

	@Nonnull
	static PrefixTreeNode newPrefixTreeNode(@Nonnull String string, int position) {
		PrefixTreeNode result = new PrefixTreeNode();
		result.character = string.charAt(position);
		result.string = string.substring(0, position + 1);
		result.lastCharacter = string.length() - 1 == position;
		return result;
	}

	@Nonnull
	static PrefixTreeNode newEmptyInstance() {
		final PrefixTreeNode result = new PrefixTreeNode();
		result.lastCharacter = false;
		result.string = "";
		result.character = EMPTY_CHARACTER;
		return result;
	}

	@Nullable
	public PrefixTreeNode getChild(@Nonnull Character character) {
		return children.get(character);
	}

	@Nonnull
	String getString() {
		return string;
	}

	@Nonnull
	Character getCharacter() {
		return character;
	}

	boolean isLastCharacter() {
		return lastCharacter;
	}

	void addString(@Nonnull String s, int position) {
		if (position < s.length()) {
			final char character = s.charAt(position);

			PrefixTreeNode child = getChild(character);
			if (child == null) {
				child = newPrefixTreeNode(s, position);
				children.put(character, child);
			}

			if (position + 1 < s.length()) {
				child.addString(s, position + 1);
			} else {
				child.lastCharacter = true;
			}
		}
	}

	public boolean containsString(@Nonnull String s, int position) {
		if (position < s.length()) {
			final PrefixTreeNode child = getChild(s.charAt(position));
			if (child == null) {
				return false;
			} else {
				if (position + 1 < s.length()) {
					return child.containsString(s, position + 1);
				} else {
					return child.lastCharacter;
				}
			}
		} else {
			return false;
		}
	}
}
