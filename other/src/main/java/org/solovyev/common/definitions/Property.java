package org.solovyev.common.definitions;

import org.solovyev.common.Identifiable;

/**
 * User: serso
 * Date: 29.04.2009
 * Time: 23:48:39
 */
public class Property<VALUE, ID> implements Identifiable<ID> {

    private ID id = null;
    private VALUE value = null;

    public Property( VALUE value, ID id ) {
        this.setValue(value);
        this.setId(id);
    }

    public void setValue( VALUE value ) {
        this.value = value;
    }

    public VALUE getValue() {
        return this.value;
    }

    public void setId( ID id ) {
        this.id = id;
    }

    public ID getId() {
        return this.id;
    }

	public static <VALUE, ID> Property<VALUE, ID> create (VALUE value, ID id) {
		return new Property<VALUE, ID>(value, id);
	}
}
