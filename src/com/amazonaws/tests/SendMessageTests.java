package com.amazonaws.tests;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

import com.amazonaws.definitions.MessageDefinitions;
import com.amazonaws.definitions.QueueDefinitions;
import com.amazonaws.services.sqs.model.Message;

public class SendMessageTests extends BaseTestSuite{
	
	String randomNo = getRandomNumber();
	String queueName = "testQueue1"+randomNo;
	Map<String, String> mapValues = new HashMap<String, String>();

	
	@Test
	public void sendMessageTest() throws Exception {
		mapValues.clear();
		String myMessage = "This is my message text 1"+randomNo;
		
		QueueDefinitions queueDefinitions = new QueueDefinitions(sqsServer); 
		MessageDefinitions messageDefinitions = queueDefinitions.createQueue(queueName,mapValues);
		queueDefinitions.createQueue(queueName,mapValues);
		String myQueueUrl = queueDefinitions.getQueueUrl(queueName);
		int statusCode = messageDefinitions.sendMessageToQueue(myQueueUrl, myMessage);
		Assert.assertEquals("Message write unsuccessful with code : "+statusCode, statusCode, 200);
		String qMessage = messageDefinitions.getMessageFromQueue(myQueueUrl, myMessage);
		Assert.assertTrue("Message not written to Queue", qMessage.equals(myMessage));
	}
	
	@Test
	public void sendMultipleMessagesTest() throws Exception {	
		mapValues.clear();
		QueueDefinitions queueDefinitions = new QueueDefinitions(sqsServer); 
		MessageDefinitions messageDefinitions = queueDefinitions.createQueue(queueName,mapValues);
		String myQueueUrl = queueDefinitions.getQueueUrl(queueName);
		messageDefinitions.sendMultipleMessagesToQueue(myQueueUrl, 5);
	}

	@Test
	public void sendMessagesWithSpecialCharachtersTest() throws Exception {
		mapValues.clear();
		String myMessage = "Special_m@ssage-!!!!"+randomNo;
		
		QueueDefinitions queueDefinitions = new QueueDefinitions(sqsServer); 
		MessageDefinitions messageDefinitions = queueDefinitions.createQueue(queueName,mapValues);
		queueDefinitions.createQueue(queueName,mapValues);
		String myQueueUrl = queueDefinitions.getQueueUrl(queueName);
		int statusCode = messageDefinitions.sendMessageToQueue(myQueueUrl, myMessage);
		Assert.assertEquals("Message write unsuccessful with code : "+statusCode, statusCode, 200);
		String qMessage = messageDefinitions.getMessageFromQueue(myQueueUrl, myMessage);
		Assert.assertTrue("Message not written to Queue", qMessage.equals(myMessage));
	}
	

	@Test
	public void sendDelayedMessageTest() throws Exception {
		mapValues.clear();
				
		QueueDefinitions queueDefinitions = new QueueDefinitions(sqsServer); 
		MessageDefinitions messageDefinitions = queueDefinitions.createQueue(queueName,mapValues);
		String myQueueUrl = queueDefinitions.getQueueUrl(queueName);
		
		messageDefinitions.sendDelayedMessageToQueue(myQueueUrl, "My Delayed Message", 1);
		List<Message> message = messageDefinitions.recieveMessagesFromQueue(myQueueUrl, 1);
		Assert.assertEquals("Message Queue not empty : ", 0, message.size());	
		Thread.sleep(2500);
		message = messageDefinitions.recieveMessagesFromQueue(myQueueUrl, 1);
		Assert.assertEquals("Message Queue not empty : ", 1, message.size());	
		Assert.assertEquals("Incorrect message received : ", "My Delayed Message", message.get(0).getBody());
	}
	
	@Test
	public void exceedingMessageLengthTestShouldThrowException() throws Exception {

		mapValues.clear();
		String myMessage = new String(new char[120000]).replace("\0", "Messages1234");
		
		QueueDefinitions queueDefinitions = new QueueDefinitions(sqsServer); 
		MessageDefinitions messageDefinitions = queueDefinitions.createQueue(queueName,mapValues);
		queueDefinitions.createQueue(queueName,mapValues);
		String myQueueUrl = queueDefinitions.getQueueUrl(queueName);
		int statusCode = messageDefinitions.sendMessageToQueue(myQueueUrl, myMessage);
		Assert.assertEquals("Message write unsuccessful with code : "+statusCode, 400, statusCode);
	}
	
}