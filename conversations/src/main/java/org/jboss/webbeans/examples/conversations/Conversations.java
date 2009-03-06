package org.jboss.webbeans.examples.conversations;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Named;
import javax.context.Conversation;
import javax.context.SessionScoped;
import javax.faces.model.SelectItem;
import javax.inject.Current;
import javax.inject.Produces;

import java.io.Serializable;

import org.jboss.webbeans.WebBean;
import org.jboss.webbeans.conversation.ConversationIdGenerator;
import org.jboss.webbeans.conversation.ConversationManager;
import org.jboss.webbeans.conversation.bindings.ConversationInactivityTimeout;


@SessionScoped
@Named("conversations")
public class Conversations implements Serializable {

   @Current private Conversation conversation;
   @Current private ConversationIdGenerator id;
   @Current private ConversationManager conversationManager;
   private String cid;
     
   public Conversations() 
   {
   }
   
   @Produces
   @ConversationInactivityTimeout
   @Example
   public static long getConversationTimeoutInMilliseconds()
   {
      return 10000;
   }   
   
   public void abandon() 
   {
      conversation.begin(id.nextId());
   }
   
   public void noop()
   {   
   }
   
   public Iterable<Conversation> getConversationList() 
   {
      return conversationManager.getLongRunningConversations(); 
   }
   
   public List<SelectItem> getLongRunningConversations() 
   {
      List<SelectItem> longRunningConversations = new ArrayList<SelectItem>();
      for (Conversation conversation : conversationManager.getLongRunningConversations()) 
      {
         longRunningConversations.add(new SelectItem(conversation.getId(), conversation.getId()));
      }
      return longRunningConversations;
   }

   public void longop() {
      try 
      {
         Thread.sleep(5000);
      } 
      catch (InterruptedException e) 
      {
         // non-issue
      }
   }
   
   public void switchConversation() 
   {
      conversation.begin(cid);
   }
   
   public String getCid()
   {
      return cid;
   }

   public void setCid(String cid)
   {
      this.cid = cid;
   }
   
}