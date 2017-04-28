package com.amazonaws.definitions;

import java.util.List;
import java.util.Map;

import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.model.CreateQueueRequest;
import com.amazonaws.services.sqs.model.CreateQueueResult;
import com.amazonaws.services.sqs.model.DeleteQueueRequest;
import com.amazonaws.services.sqs.model.GetQueueUrlResult;
import com.amazonaws.services.sqs.model.ListQueuesRequest;
import com.amazonaws.services.sqs.model.SendMessageRequest;

public class QueueDefinitions extends BaseDefinitions {

	CreateQueueRequest createQueueRequest;
	String myQueueUrl;

	public QueueDefinitions(AmazonSQS sqsServer) {
		super(sqsServer);
	}

	public MessageDefinitions createQueue(String queueName, Map<String, String> attributes) {
		createQueueRequest = new CreateQueueRequest(queueName).withAttributes(attributes);
		return new MessageDefinitions(sqsServer);
	}

	public String getQueueUrl(String queueName) {
		return sqsServer.createQueue(new CreateQueueRequest(queueName)).getQueueUrl();

	}


	public String getQueueVisibilityTimeout() {
		System.out.println("Timeout  = " + createQueueRequest.getAttributes().get("defaultVisibilityTimeoutAttribute"));
		return createQueueRequest.getAttributes().get("defaultVisibilityTimeoutAttribute");

	}


	public List<String> getQueueUrls() {
		return sqsServer.listQueues(new ListQueuesRequest()).getQueueUrls();
	}

	public int deleteQueue(String queueName) {
		return sqsServer.deleteQueue(new DeleteQueueRequest(getQueueUrl(queueName))).getSdkHttpMetadata()
				.getHttpStatusCode();
	}

}
