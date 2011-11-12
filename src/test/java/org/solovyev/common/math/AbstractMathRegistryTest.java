package org.solovyev.common.math;

import junit.framework.Assert;
import org.jetbrains.annotations.NotNull;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * User: serso
 * Date: 11/12/11
 * Time: 2:03 PM
 */
public class AbstractMathRegistryTest {
	@Test
	public void testOrder() throws Exception {
		final List<MathEntity> list = new ArrayList<MathEntity>();
		list.add(new MathEntityImpl("tanh"));
		list.add(new MathEntityImpl("atan"));
		list.add(new MathEntityImpl("atanh"));
		Collections.sort(list, new AbstractMathRegistry.MathEntityComparator());
		Assert.assertEquals("atanh", list.get(0).getName());
		Assert.assertEquals("atan", list.get(1).getName());
		Assert.assertEquals("tanh", list.get(2).getName());
	}

	private class MathEntityImpl implements MathEntity {

		@NotNull
		private final String name;

		public MathEntityImpl(@NotNull String name) {
			this.name = name;
		}

		@NotNull
		@Override
		public String getName() {
			return this.name;
		}

		@Override
		public boolean isSystem() {
			throw new UnsupportedOperationException();
		}

		@NotNull
		@Override
		public Integer getId() {
			throw new UnsupportedOperationException();
		}

		@Override
		public boolean isIdDefined() {
			throw new UnsupportedOperationException();
		}

		@Override
		public void setId(@NotNull Integer id) {
			throw new UnsupportedOperationException();
		}

		@Override
		public void copy(@NotNull MathEntity that) {
			throw new UnsupportedOperationException();
		}
	}
}
