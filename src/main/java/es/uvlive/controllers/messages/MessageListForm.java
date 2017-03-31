/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.uvlive.controllers.messages;

import es.uvlive.controllers.BaseForm;
import java.util.ArrayList;

/**
 *
 * @author atraver
 */
public class MessageListForm extends BaseForm {
    private ArrayList<MessageForm> messages;

    public ArrayList<MessageForm> getMessages() {
        return messages;
    }

    public void setMessages(ArrayList<MessageForm> messages) {
        this.messages = messages;
    }
}
