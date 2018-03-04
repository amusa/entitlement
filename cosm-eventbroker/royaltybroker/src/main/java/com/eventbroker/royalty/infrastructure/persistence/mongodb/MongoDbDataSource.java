package com.eventbroker.royalty.infrastructure.persistence.mongodb;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoDatabase;

import java.io.IOException;
import java.util.Properties;

@ApplicationScoped
public class MongoDbDataSource {

	private MongoDatabase mongoCosmDataBase;
	
	@MONGODB
	@Inject
    Properties mongoDbProperties;

    @PostConstruct
    private void initProperties() {
     
        	String dbURIString = mongoDbProperties.getProperty("mongodb.url");
        	MongoClient mongoClient = new MongoClient(new MongoClientURI(dbURIString));
        	mongoCosmDataBase = mongoClient.getDatabase("royalty");
        	
        
    }

   
    @Produces
    @RequestScoped
    public MongoDatabase exposeMongoDbSource() throws IOException {
        
        return mongoCosmDataBase;
    }

}


