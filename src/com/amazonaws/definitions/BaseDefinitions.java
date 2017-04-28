package com.amazonaws.definitions;

import com.amazonaws.services.sqs.AmazonSQS;

public class BaseDefinitions {
	
	protected AmazonSQS sqsServer;
	
	public BaseDefinitions( AmazonSQS sqsServer)
	{
		this.sqsServer = sqsServer;
	}
}
