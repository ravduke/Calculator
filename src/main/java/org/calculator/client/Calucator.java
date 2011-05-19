package org.calculator.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/**
 *
 * @author Rafal Jablonowski
 */
@RemoteServiceRelativePath("calucator")
public interface Calucator extends RemoteService {
    public String myMethod(String s);
}
