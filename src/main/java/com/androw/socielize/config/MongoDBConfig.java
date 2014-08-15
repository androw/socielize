package com.androw.socielize.config;

import com.mongodb.Mongo;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoFactoryBean;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import java.net.UnknownHostException;

/**
 * Created by Androw on 14/08/2014.
 */
@Configuration
@EnableMongoRepositories(basePackages = "com.androw.socielize")
public class MongoDBConfig {

    public
    @Bean
    MongoTemplate mongoTemplate(Mongo mongo) throws UnknownHostException {
        return new MongoTemplate(mongo, "socielize");
    }

    public @Bean
    MongoFactoryBean mongo() {
        MongoFactoryBean mongo = new MongoFactoryBean();
        mongo.setHost("localhost");
        return mongo;
    }

}