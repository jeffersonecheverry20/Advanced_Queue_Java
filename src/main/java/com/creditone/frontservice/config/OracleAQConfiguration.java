package com.creditone.frontservice.config;

import com.creditone.frontservice.subscriber.CommunicationReceiver;
import oracle.jdbc.pool.OracleDataSource;
import oracle.jms.AQjmsFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jdbc.jms.listener.oracle.AdtMessageListenerContainer;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jms.core.JmsTemplate;

import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.QueueConnectionFactory;
import javax.sql.DataSource;
import java.sql.SQLException;

@Configuration
public class OracleAQConfiguration {

    @Autowired
    CommunicationReceiver communicationReceiver;

    @Bean
    public DataSource dataSource() throws SQLException {
        System.out.println("configure datasource");
        OracleDataSource dataSource = new OracleDataSource();
        dataSource.setUser(System.getenv("AQ_USER"));
        dataSource.setPassword(System.getenv("AQ_PASSWORD"));
        dataSource.setURL(System.getenv("AQ_URL"));
        dataSource.setImplicitCachingEnabled(true);
        return dataSource;
    }

    @Bean
    public DataSourceTransactionManager transactionManager(DataSource dataSource){
        DataSourceTransactionManager manager = new DataSourceTransactionManager();
        manager.setDataSource(dataSource);
        return manager;
    }

    @Bean
    public QueueConnectionFactory connectionFactory(DataSource dataSource) throws JMSException {
        System.out.println("connectionFactory");
        return AQjmsFactory.getQueueConnectionFactory(dataSource);
    }

    @Bean
    public AdtMessageListenerContainer messageListenerContainer(ConnectionFactory connectionFactory, DataSource dataSource) {
       AdtMessageListenerContainer adtMessageListenerContainer = new AdtMessageListenerContainer();
       adtMessageListenerContainer.setDestinationName(System.getenv("AQ_DESTINATION"));
       adtMessageListenerContainer.setSessionTransacted(true);
       adtMessageListenerContainer.setConnectionFactory(connectionFactory);
       adtMessageListenerContainer.setTransactionManager(transactionManager(dataSource));
       adtMessageListenerContainer.setMessageListener(communicationReceiver);
       return adtMessageListenerContainer;
    }

    @Bean
    public JmsTemplate jmsTemplate(ConnectionFactory connectionFactory){
        JmsTemplate jmsTemplate = new JmsTemplate();
        jmsTemplate.setSessionTransacted(true);
        jmsTemplate.setConnectionFactory(connectionFactory);
        return jmsTemplate;
    }

}
