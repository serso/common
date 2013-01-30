package org.solovyev.common.msg;

import org.jetbrains.annotations.NotNull;

/**
 * User: serso
 * Date: 10/18/11
 * Time: 11:18 PM
 */

/**
 * Container for messages
 */
public interface MessageRegistry {

    /**
     * Adds message to the registry.
     * Note: according to the implementation this method doesn't guarantee that new message will be added
     * in underlying container (e.g. if such message already exists)
     *
     * @param message message to be added
     */
	void addMessage(@NotNull Message message);

    /**
     *
     * @return true if there is any message available in the registry
     */
    boolean hasMessage();

    /**
     * Method returns message from registry and removes it from underlying container
     * Note: this method must be called after {@link MessageRegistry#hasMessage()}
     *
     * @return message from the registry
     */
	@NotNull
	Message getMessage();
}
