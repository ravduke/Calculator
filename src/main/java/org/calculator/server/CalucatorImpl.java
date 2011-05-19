/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.calculator.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.calculator.client.Calucator;

/**
 * Class parses given string and return answer to the client
 * @author Rafal Jablonowski
 */
public class CalucatorImpl extends RemoteServiceServlet implements Calucator {
    public String myMethod(String s) {

        String[] equations = s.split("\\n+");

        String result = "";
        Calculate value;
        try {
            for(int i = 0 ; i < equations.length; i++){
                value = new Calculate(equations[i]);
                result += String.valueOf(value.text2num()) + "\n";
            }
            
            return "Result:\n " + String.valueOf(result);
        } catch (Exception ex) {
            Logger.getLogger(CalucatorImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "Unknown token. ";
    }
}
