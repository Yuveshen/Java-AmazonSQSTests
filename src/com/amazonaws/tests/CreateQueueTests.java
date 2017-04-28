package com.amazonaws.tests;

import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

import com.amazonaws.definitions.QueueDefinitions;

public class CreateQueueTests extends BaseTestSuite{

	String queueName = "testQueue" + getRandomNumber();
	Map<String, String> mapValues = new HashMap<String, String>();
	
	@Test
	public void createNewStandardQueueAndGetUrlTest() {
		mapValues.clear();
		QueueDefinitions queueDefinitions = new QueueDefinitions(sqsServer); 
		queueDefinitions.createQueue(queueName, mapValues);
		queueDefinitions.getQueueUrl(queueName);
		Assert.assertTrue("Queue not found",queueDefinitions.getQueueUrl(queueName).contains(queueName));
	}	
	
	@Test
	public void createQueueWithVisibilityTimeoutTest() {
		mapValues = null;
		mapValues.put("defaultVisibilityTimeoutAttribute", "25");
		QueueDefinitions queueDefinitions = new QueueDefinitions(sqsServer); 
		queueDefinitions.createQueue(queueName, mapValues);
		Assert.assertTrue("Queue not found",queueDefinitions.getQueueVisibilityTimeout().equals(mapValues.get("defaultVisibilityTimeoutAttribute")));	
	}
}
