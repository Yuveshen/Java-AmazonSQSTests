package com.amazonaws.tests;

import java.io.File;
import java.util.Random;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;

import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClient;

public class BaseTestSuite {
	
	protected static AmazonSQS sqsServer;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		sqsServer = new AmazonSQSClient(new BasicAWSCredentials("x", "x"));
		sqsServer.setEndpoint("http://localhost:9324");
	}

    public static String getRandomNumber(){
        Random random = new Random();
        return Integer.toString(random.nextInt(10000));
    }

}
