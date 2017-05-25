package com.example.episodicusers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.hypermedia.DynamicServiceInstanceProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.hateoas.hal.Jackson2HalModule;

@Configuration
public class AppConfig {

    public AppConfig(@Qualifier("_halObjectMapper") ObjectMapper mapper) {
        mapper.registerModule(new Jackson2HalModule());
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    }

    @Bean
    public DynamicServiceInstanceProvider dynamicServiceInstanceProvider(DiscoveryClient client) {
        return new DynamicServiceInstanceProvider(client, "episodic-shows");
    }

}
