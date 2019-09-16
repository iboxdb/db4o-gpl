/* Copyright (C) 2007  Versant Inc.  http://www.db4o.com */

package com.db4o.cs.internal.messages;



/**
 * @exclude
 */
public interface MessageDispatcher extends java.io.Closeable {

	public boolean isMessageDispatcherAlive();
	
	public boolean write(Msg msg);

        @Override
        public void close();
	//public boolean close();
}
