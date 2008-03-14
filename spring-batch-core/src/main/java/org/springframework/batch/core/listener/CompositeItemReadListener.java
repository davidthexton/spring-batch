/*
 * Copyright 2006-2007 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.batch.core.listener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.springframework.batch.core.ChunkListener;
import org.springframework.batch.core.ItemReadListener;

/**
 * @author Lucas Ward
 * 
 */
public class CompositeItemReadListener implements ItemReadListener {

	private List listeners = new ArrayList();

	/**
	 * Public setter for the listeners.
	 * 
	 * @param listeners
	 */
	public void setListeners(ChunkListener[] listeners) {
		this.listeners = Arrays.asList(listeners);
	}

	/**
	 * Register additional listener.
	 * 
	 * @param itemReaderListener
	 */
	public void register(ItemReadListener itemReaderListener) {
		if (!listeners.contains(itemReaderListener)) {
			listeners.add(itemReaderListener);
		}
	}

	public void afterRead(Object item) {
		for (Iterator iterator = listeners.iterator(); iterator.hasNext();) {
			ItemReadListener listener = (ItemReadListener) iterator.next();
			listener.afterRead(item);
		}
	}

	public void beforeRead() {
		for (Iterator iterator = listeners.iterator(); iterator.hasNext();) {
			ItemReadListener listener = (ItemReadListener) iterator.next();
			listener.beforeRead();
		}
	}

	public void onReadError(Exception ex) {
		for (Iterator iterator = listeners.iterator(); iterator.hasNext();) {
			ItemReadListener listener = (ItemReadListener) iterator.next();
			listener.onReadError(ex);
		}
	}
}