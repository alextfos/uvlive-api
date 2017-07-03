/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.uvlive.controllers.response;

import java.util.ArrayList;

public class ConversationsListResponse extends BaseResponse {

    private ArrayList<ConversationResponse> conversations;
    
    public ConversationsListResponse () {
        conversations = new ArrayList<>();
    }
    
    public ConversationsListResponse (ArrayList<ConversationResponse> conversations) {
        this();
        ConversationResponse cTmp;
        for (ConversationResponse c:conversations) {
            cTmp = new ConversationResponse();
            cTmp.setIdConversation(c.getIdConversation());
            cTmp.setName(c.getName());
            this.conversations.add(cTmp);
        }
    }
    
    public ArrayList<ConversationResponse> getConversations() {
        return conversations;
    }
    
    public void add(ConversationResponse conversation ) {
        conversations.add(conversation);
    }
}

