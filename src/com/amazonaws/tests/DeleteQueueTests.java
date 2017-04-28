package com.amazonaws.tests;

import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

import com.amazonaws.definitions.QueueDefinitions;

public class DeleteQueueTests extends BaseTestSuite {
	String queueName = "testQueue" + getRandomNumber();
	Map<String, String> mapValues = new HashMap<String, String>();
	
	@Test
	public void deleteQueueTest() {
		QueueDefinitions queueDefinitions = new QueueDefinitions(sqsServer); 
		queueDefinitions.createQueue(queueName, mapValues);
		Assert.assertEquals("Delete Queue Unsuccessful",queueDefinitions.deleteQueue(queueName), (200));
		Assert.assertEquals("Queue size not correct", 0 , queueDefinitions.getQueueUrls().size());	
	}
}
