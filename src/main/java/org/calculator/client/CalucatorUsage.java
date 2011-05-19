/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.calculator.client;

import com.google.gwt.core.client.GWT;

import com.google.gwt.user.client.rpc.AsyncCallback;

import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.VerticalPanel;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.TextArea;

/**
 * Example class using the Calucator service.
 *
 * @author Rafal Jablonowski
 */
public class CalucatorUsage extends VerticalPanel {

    /*
     * Server reply textarea
     */
    private TextArea lblServerReply = new TextArea();
    /*
     * User input textarea
     */
    private TextArea  txtUserInput = new TextArea ();
    /*
     * Send input to the server button
     */
    private Button btnSend = new Button("Compute");
    /*
     * Main constructor
     */
    public CalucatorUsage() {

        txtUserInput.setSize("600px", "200px");
        txtUserInput.setFocus(true);
        lblServerReply.setSize("600px", "200px");
        lblServerReply.setReadOnly(true);
        txtUserInput.setText(
                "zehn"
                +"\nzwei + drei plus vierzehn"
                +"\neinhundertdreiundsechzig * 2 + 3 minus sieben"
                +"\n500 * 2 + 3 / 2"
                +"\nminusdreimillionsechshundertachtzehn + vier"
                +"\nvierzehn mal 4 plus 190"
                );
        add(new Label("Input your equations: "));
        add(txtUserInput);
        add(btnSend);
        add(lblServerReply);

        // Create an asynchronous callback to handle the result.
        final AsyncCallback<String> callback = new AsyncCallback<String>() {
            public void onSuccess(String result) {
                String res[] = result.split("\\s+");
                String out = "";
                for(String s : res)
                    out+=s+"\n";
                lblServerReply.setText(out);
            }

            public void onFailure(Throwable caught) {
                lblServerReply.setText("Communication failed");
            }
        };

        // Listen for the button clicks
        btnSend.addClickHandler(new ClickHandler(){
            public void onClick(ClickEvent event) {
                // Make remote call. Control flow will continue immediately and later
                // 'callback' will be invoked when the RPC completes.
                getService().myMethod(txtUserInput.getText(), callback);
            }
        });
    }
    
    public static CalucatorAsync getService() {
        // Create the client proxy. Note that although you are creating the
        // service interface proper, you cast the result to the asynchronous
        // version of the interface. The cast is always safe because the
        // generated proxy implements the asynchronous interface automatically.

        return GWT.create(Calucator.class);
    }
}
