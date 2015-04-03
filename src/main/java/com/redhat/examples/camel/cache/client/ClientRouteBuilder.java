package com.redhat.examples.camel.cache.client;

import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.cache.CacheConstants;

public class ClientRouteBuilder extends RouteBuilder {

	@Override
	public void configure() throws Exception {
		// TODO Auto-generated method stub
		from("timer://foo?repeatCount=1&fixedRate=true&period=5000")
			.log("Starting")
			.setHeader("InUserKey", constant("jbossUser"))
			.setHeader("InPassKey", constant("jbossPass"))
			.to("direct-vm:GetCachedUserAndPass")
			.log("${headers.InUserKey}")
			.log("${headers.InPassKey}");			
	}
}