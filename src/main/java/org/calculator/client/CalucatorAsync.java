package org.calculator.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 *
 * @author Rafal Jablonowski
 */
public interface CalucatorAsync {
    public void myMethod(String s, AsyncCallback<String> callback);

}
