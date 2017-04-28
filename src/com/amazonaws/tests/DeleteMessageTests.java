package com.amazonaws.tests;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

import com.amazonaws.definitions.MessageDefinitions;
import com.amazonaws.definitions.QueueDefinitions;
import com.amazonaws.services.sqs.model.Message;

public class DeleteMessageTests extends BaseTestSuite{
	String randomNo = getRandomNumber();
	String queueName = "testQueue1"+randomNo;
	Map<String, String> mapValues = new HashMap<String, String>();
	
	@Test
	public void deleteMessageTest() throws Exception {
		
		QueueDefinitions queueDefinitions = new QueueDefinitions(sqsServer); 
		MessageDefinitions messageDefinitions = queueDefinitions.createQueue(queueName,mapValues);
		String myQueueUrl = queueDefinitions.getQueueUrl(queueName);
		messageDefinitions.sendMessageToQueue(myQueueUrl, "My Message");
		Message message1 = messageDefinitions.recieveMessagesFromQueue(myQueueUrl, 1).get(0);
		messageDefinitions.deleteMessageFromQueue(myQueueUrl, message1);
		Thread.sleep(1100);
		List<Message> message2 = messageDefinitions.recieveMessagesFromQueue(myQueueUrl, 1);
		Assert.assertEquals("Incorrect message received : ", "My Message", message1.getBody());
		Assert.assertEquals("Message Queue not empty : ", 0, message2.size());	
	}
}
