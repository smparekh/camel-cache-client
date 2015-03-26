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
		from("timer://foo?repeatCount=2&fixedRate=true&period=5000")
			.log("Starting")
			//.to("log:foo?showAll=true")
			.log(simple("${property[CamelTimerCounter]}").toString())
			//.log("${property.Exchange.TIMER_COUNTER}")
			// Iteration 1: Check current Value
			// Iteration 2: Change value to EAP
			.choice()
				.when(property("CamelTimerCounter").isEqualTo(1))
				.to("direct:checkValue")
				.otherwise()
				.to("direct:changeValue")
			.end();
	    
		from("direct:checkValue")
			.setHeader(CacheConstants.CACHE_OPERATION, constant(CacheConstants.CACHE_OPERATION_GET))
			.setHeader(CacheConstants.CACHE_KEY, constant("jboss"))
			.to("direct-vm:CacheKeyCheck")
		    // Check if entry was not found
		    .choice()
		    	.when(header(CacheConstants.CACHE_ELEMENT_WAS_FOUND).isNull())
		        // If not found, get the payload and put it to cache
		        .log("No entry was found")
		        .otherwise()
		        .log("Found value: ${body}")
		    .end();
		
		from("direct:changeValue")
			.log("Changing value")
			.process(
				new Processor() {
					public void process(Exchange exchange) {
						Message in = exchange.getIn();
						in.setBody("EAP");
					}
				}
			)
			.setHeader(CacheConstants.CACHE_OPERATION, constant(CacheConstants.CACHE_OPERATION_UPDATE))
	        .setHeader(CacheConstants.CACHE_KEY, constant("jboss"))
	        .to("direct-vm:CacheChangeValue");
	}
}