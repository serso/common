
package org.solovyev.common.utils;

/**
 * User: serso
* Date: Jan 18, 2010
* Time: 5:10:52 PM
*/

/**
 * Used in Filter
 * @param <T> type of element in iterator
 */
public class FilterData<T> {

	private T element = null;
	private boolean wasFiltered = false;

	public FilterData(T element, boolean wasFiltered) {
		this.element = element;
		this.wasFiltered = wasFiltered;
	}

	public T getElement() {
		return element;
	}

	public T getNotFiltered () {
		return wasFiltered ? null : element;
	}

	public boolean wasFiltered() {
		return wasFiltered;
	}

	public void setElement(T element) {
		this.element = element;
	}

	public void setWasFiltered(boolean wasFiltered) {
		this.wasFiltered = wasFiltered;
	}
}
