package org.solovyev.common.collections;

import org.jetbrains.annotations.NotNull;

import java.util.*;

/**
 * User: serso
 * Date: 11/12/11
 * Time: 2:28 PM
 */
public class SortedList<T> implements List<T> {

	@NotNull
	private List<T> list = new ArrayList<T>();

	@NotNull
	private final Comparator<? super T> comparator;

	public SortedList(@NotNull Comparator<? super T> comparator) {
		this.comparator = comparator;
	}

	public SortedList(@NotNull List<T> list, @NotNull Comparator<? super T> comparator) {
		this.list = list;
		this.comparator = comparator;
	}

	@Override
	public int size() {
		return list.size();
	}

	@Override
	public boolean isEmpty() {
		return list.isEmpty();
	}

	@Override
	public boolean contains(Object o) {
		return list.contains(o);
	}

	@Override
	public Iterator<T> iterator() {
		final Iterator<T> it = list.iterator();
		return new Iterator<T>() {
			@Override
			public boolean hasNext() {
				return it.hasNext();
			}

			@Override
			public T next() {
				return it.next();
			}

			@Override
			public void remove() {
				it.remove();
				// todo serso: think
				Collections.sort(list, comparator);
			}
		};
	}

	@Override
	public Object[] toArray() {
		return list.toArray();
	}

	@Override
	public <T> T[] toArray(T[] a) {
		return list.toArray(a);
	}

	@Override
	public boolean add(T t) {
		boolean result = list.add(t);
		Collections.sort(list, comparator);
		return result;
	}

	@Override
	public boolean remove(Object o) {
		boolean result = list.remove(o);
		Collections.sort(list, comparator);
		return result;
	}

	@Override
	public boolean containsAll(Collection<?> c) {
		return this.list.containsAll(c);
	}

	@Override
	public boolean addAll(Collection<? extends T> c) {
		boolean result = this.list.addAll(c);
		Collections.sort(list, comparator);
		return result;
	}

	@Override
	public boolean addAll(int index, Collection<? extends T> c) {
		boolean result = this.list.addAll(index, c);
		Collections.sort(list, comparator);
		return result;
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		boolean result = this.list.removeAll(c);
		Collections.sort(list, comparator);
		return result;
	}

	@Override
	public boolean retainAll(Collection<?> c) {
		boolean result = this.list.retainAll(c);
		Collections.sort(list, comparator);
		return result;
	}

	@Override
	public void clear() {
		this.list.clear();
	}

	@Override
	public T get(int index) {
		return this.list.get(index);
	}

	@Override
	public T set(int index, T element) {
		T result = this.list.set(index, element);
		Collections.sort(list, comparator);
		return result;
	}

	@Override
	public void add(int index, T element) {
		this.list.add(index, element);
		Collections.sort(list, comparator);
	}

	@Override
	public T remove(int index) {
		T result = this.list.remove(index);
		Collections.sort(list, comparator);
		return result;
	}

	@Override
	public int indexOf(Object o) {
		return this.list.indexOf(o);
	}

	@Override
	public int lastIndexOf(Object o) {
		return this.list.lastIndexOf(o);
	}

	@Override
	public ListIterator<T> listIterator() {
		return listIterator(0);
	}

	@Override
	public ListIterator<T> listIterator(int index) {
		final ListIterator<T> it = this.list.listIterator(index);
		return new ListIterator<T>() {
			@Override
			public boolean hasNext() {
				return it.hasNext();
			}

			@Override
			public T next() {
				return it.next();
			}

			@Override
			public boolean hasPrevious() {
				return it.hasPrevious();
			}

			@Override
			public T previous() {
				return it.previous();
			}

			@Override
			public int nextIndex() {
				return it.nextIndex();
			}

			@Override
			public int previousIndex() {
				return it.previousIndex();
			}

			@Override
			public void remove() {
				it.remove();
				Collections.sort(list, comparator);
			}

			@Override
			public void set(T t) {
				it.set(t);
				Collections.sort(list, comparator);
			}

			@Override
			public void add(T t) {
				it.add(t);
				Collections.sort(list, comparator);
			}
		};
	}

	@Override
	public List<T> subList(int fromIndex, int toIndex) {
		return this.list.subList(fromIndex, toIndex);
	}
}
