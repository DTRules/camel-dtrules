package org.apache.camel.component.dtrules;

import java.util.Map;

import org.apache.camel.Endpoint;
import org.apache.camel.impl.DefaultComponent;

public class DTRulesComponent extends DefaultComponent {

	@Override
	/**
	 * The endpoint uri looks like below, while 'foo' is the ruleSet name.</br></br>
	 * 
	 * uri --> dtrules://foo?directory=/home/tester/dtrules&name=DTRules.xml&decisionTable=abc&resultEntity=result&trace=true
	 */
	protected Endpoint createEndpoint(String uri, String remaining, Map<String, Object> parameters) throws Exception {
		final DTrulesConfiguration config = new DTrulesConfiguration();
		
		// set parameters like name and uri
		setProperties(config, parameters);
		
		// set rule set name
		config.setRuleset(remaining);
		
		// create endpoint
		DTRulesEndpoint endpoint = new DTRulesEndpoint(uri, this, config);
		return endpoint;
	}

}
