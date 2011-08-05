package org.apache.camel.component.dtrules;

import org.apache.camel.EndpointInject;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.junit.Test;

public class DTRulesProducerTest extends CamelTestSupport {

	@EndpointInject(uri = "mock://result")
    private MockEndpoint mock;
	
    @Override
    public void setUp() throws Exception {
        super.setUp();
        this.mock.reset();
    }
    
    @Override
    public void tearDown() throws Exception {
    	super.tearDown();
    	this.mock.reset();
    }
    
    @Test public void testSimpleRule(){
    	template.sendBody("direct://foo", this.getCase());
    }
	
    @Override
    protected RouteBuilder createRouteBuilder() throws Exception {
        return new RouteBuilder() {
            public void configure() {
                from("direct://foo")    
                  .toF("dtrules://foo?directory=%s/src/test/resources/repository&name=DTRules.xml&decisionTable=abc&resultEntity=result", System.getProperty("user.dir")) 
                  .to("log://A")
                  .to("mock://result");     
            }
        };
    }
    
    private String getCase() {
    	return String.format(
    			"<person id=\"%s\">\n" +
    			"  <person_ID>%s</person_ID>\n" +
    			"  <name>%s</name>\n" +
    			"  <age>%s</age>\n" +
    			"  <genderCode>%s</genderCode>\n" +
    			"</person>", "101", "101", "hans", 21, "male");
    }
	
}
