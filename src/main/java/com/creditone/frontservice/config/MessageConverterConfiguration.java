package com.creditone.frontservice.config;

import com.creditone.frontservice.model.CommunicationTransaction;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jdbc.jms.support.converter.oracle.MappingAdtMessageConverter;
import org.springframework.data.jdbc.jms.support.oracle.StructDatumMapper;
import org.springframework.jms.support.converter.MessageConverter;

@Configuration
public class MessageConverterConfiguration {

    @Bean
    public MessageConverter messageConverter(){
        StructDatumMapper structDatumMapper = new StructDatumMapper(System.getenv("AQ_MESSAGE"), CommunicationTransaction.class);
        MappingAdtMessageConverter converter = new MappingAdtMessageConverter(structDatumMapper);
        return converter;
    }

}
