package com.amazonaws.definitions;

import java.util.List;

import org.junit.Assert;

import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.model.DeleteMessageRequest;
import com.amazonaws.services.sqs.model.Message;
import com.amazonaws.services.sqs.model.ReceiveMessageRequest;
import com.amazonaws.services.sqs.model.SendMessageRequest;

public class MessageDefinitions extends BaseDefinitions {

	public MessageDefinitions(AmazonSQS sqsServer) {
		super(sqsServer);
	}

	public int sendMessageToQueue(String myQueueUrl, String myMessage) {
		int statusCode = 400;
		try {
			statusCode = sqsServer.sendMessage(new SendMessageRequest(myQueueUrl, myMessage)).getSdkHttpMetadata()
					.getHttpStatusCode();
		} catch (Exception e) {
			System.err.println("Error writting to server: " + e.getMessage());
		}
		return statusCode;
	}

	public String getMessageFromQueue(String myQueueUrl, String sentMessage) throws Exception {
		String qMessage = "";
		ReceiveMessageRequest receiveMessageRequest = new ReceiveMessageRequest(myQueueUrl);
		List<Message> messages = sqsServer.receiveMessage(receiveMessageRequest).getMessages();
		for (Message message : messages) {
			qMessage = message.getBody();
			if (qMessage.equals(sentMessage))
				return qMessage;
		}
		throw new Exception("Message not found in Queue");
	}

	public List<Message> getMessageFromQ(String myQueueUrl) throws Exception {
		List<Message> messages = sqsServer.receiveMessage(new ReceiveMessageRequest(myQueueUrl)).getMessages();    
        return messages;
	}

	public void sendMessagesToQueue(String myQueueUrl, String[] myMessages) {
		int count = myMessages.length;
		for (int i = 0; i < count; i++) {
			sqsServer.sendMessage(new SendMessageRequest(myQueueUrl, myMessages[i]));
		}
	}

	public List<Message> recieveMessagesFromQueue(String myQueueUrl, int messageNo) {
		List<Message> messages = sqsServer
				.receiveMessage(new ReceiveMessageRequest(myQueueUrl).withMaxNumberOfMessages(messageNo)).getMessages();
		return messages;
	}

	public void sendMultipleMessagesToQueue(String myQueueUrl, int numberOfMessages) throws Exception {
		for (int i = 0; i < numberOfMessages; i++) {
			int statusCode = sqsServer.sendMessage(new SendMessageRequest(myQueueUrl, "My Message" + i))
					.getSdkHttpMetadata().getHttpStatusCode();
			Assert.assertEquals("Message write unsuccessful with code : " + statusCode, statusCode, 200);
		}
	}

	public void deleteMessageFromQueue(String myQueueUrl, Message message) {
		sqsServer.deleteMessage(new DeleteMessageRequest(myQueueUrl, message.getReceiptHandle()));
	}

	public void sendDelayedMessageToQueue(String myQueueUrl, String message, int delay) {
		sqsServer.sendMessage(new SendMessageRequest(myQueueUrl, message).withDelaySeconds(delay));
	}
}
