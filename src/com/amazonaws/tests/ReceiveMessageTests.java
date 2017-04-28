package com.amazonaws.tests;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

import com.amazonaws.definitions.MessageDefinitions;
import com.amazonaws.definitions.QueueDefinitions;
import com.amazonaws.services.sqs.model.Message;

public class ReceiveMessageTests extends BaseTestSuite{
	String randomNo = getRandomNumber();
	String queueName = "testQueue1"+randomNo;
	Map<String, String> mapValues = new HashMap<String, String>();

	@Test
	public void recieveSpecifiedNumberOfMessagesTest() throws Exception {
		mapValues.clear();
		int messageNo = 4;
		QueueDefinitions queueDefinitions = new QueueDefinitions(sqsServer); 
		MessageDefinitions messageDefinitions = queueDefinitions.createQueue(queueName,mapValues);
		String myQueueUrl = queueDefinitions.getQueueUrl(queueName);
		messageDefinitions.sendMultipleMessagesToQueue(myQueueUrl, messageNo);
		List<Message> messages = messageDefinitions.recieveMessagesFromQueue(myQueueUrl, messageNo);
		Assert.assertEquals("Incorrect messages received : ", messageNo, messages.size());
	}
	
	@Test
	public void recieveAllMessagesIfLessThanSpecifiedAvailableTest() throws Exception {
		mapValues.clear();
		int sendMessageNo = 9;
		int recieveMessageNo = 10;
		QueueDefinitions queueDefinitions = new QueueDefinitions(sqsServer); 
		MessageDefinitions messageDefinitions = queueDefinitions.createQueue(queueName,mapValues);
		String myQueueUrl = queueDefinitions.getQueueUrl(queueName);
		messageDefinitions.sendMultipleMessagesToQueue(myQueueUrl, sendMessageNo);
		List<Message> messages = messageDefinitions.recieveMessagesFromQueue(myQueueUrl, recieveMessageNo);
		Assert.assertEquals("Incorrect messages received : ", sendMessageNo, messages.size());
	}
}  