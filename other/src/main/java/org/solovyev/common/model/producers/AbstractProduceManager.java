package org.solovyev.common.model.producers;

/**
 * User: serso
 * Date: Oct 15, 2009
 * Time: 12:18:03 AM
 */
public abstract class AbstractProduceManager<T>{

    protected SQLProducer<T> producer = null;

    protected AbstractProduceManager(SQLProducer<T> producer) {
        this.producer = producer;
    }
}
