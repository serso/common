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
 * ---------------------------------------------------------------------
 * Contact details
 *
 * Email: se.solovyev@gmail.com
 * Site:  http://se.solovyev.org
 */

package org.solovyev.common.definitions;

import java.util.List;
import java.util.ArrayList;

/**
 * User: serso
 * Date: 12.04.2009
 * Time: 0:14:01
 */
public class MultiIdentity<T> implements MultiIdentifiable<T>{

    // list of ids
    private List<T> ids = null;

    public final static Integer defaulUsedId = 0;

    // number of id which is used as default
    private Integer currentUsedId = defaulUsedId;

    // simple constructor
    public MultiIdentity() {
    }

    // constructor with id
    public MultiIdentity( T id ) {
        this.ids = new ArrayList<T>();
        this.addNewId(id);
    }

    // constructor with ids
    public MultiIdentity( List<T> ids, Integer currentUsedId ) {
        this.ids = ids;
        this.currentUsedId = currentUsedId;
    }

    public List<T> getIds() {
        return ids;
    }

    public void setIds(List<T> ids) {
        this.ids = ids;
    }

    /**
    * @return Integer number of current used id
    */
    public Integer getCurrentUsedId() {
        return currentUsedId;
    }

    /**
    * @param currentUsedId Number of id which will be used as current
    */
    public void setCurrentUsedId(Integer currentUsedId) {
        this.currentUsedId = currentUsedId;
    }

    /**
    * @return current id
    */
    public T getId() {
        return ids.get(this.currentUsedId);
    }

    /**
     * @param numberOfId number of id which is desired to get
     * @return id with numberOfId number
    */
    public T getId(Integer numberOfId) {
        return ids.get( numberOfId );
    }

    /**
     * @param id id which will be set at current number of used id
     */
    public void setId( T id ) {
        this.setId( id, this.currentUsedId );    
    }

    /**
     * @param usedId number of id to be set
     * @param id id
     */
    public void setId( T id, Integer usedId ) {
        this.ids.set(usedId, id);
    }

    /**
     * @return int number of ids
     */
    public int getNumberOfIds() {
        return this.ids.size();
    }

    /**
    * addsNewId
    */
    public void addNewId() {
        this.ids.add(null);
    }

    public void addNewId( T id ) {
        this.ids.add(id);
    }
}
