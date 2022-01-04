package com.creditone.frontservice.subscriber;

import com.creditone.frontservice.model.CommunicationTransaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.listener.SessionAwareMessageListener;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

@Component
public class CommunicationReceiver implements  SessionAwareMessageListener {

    @Autowired
    MessageConverter messageConverter;

    @Override
    public void onMessage(Message message, Session session) throws JMSException {
        try{
            if(messageConverter.fromMessage(message) instanceof CommunicationTransaction){
                CommunicationTransaction msg = (CommunicationTransaction) messageConverter.fromMessage(message);
                System.out.println(msg.getSubject());
                System.out.println(msg.getText());
            } else {
                System.out.println("Doesn't belong to CommunicationTransaction");
            }
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

}
