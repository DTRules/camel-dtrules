package org.apache.camel.component.dtrules;

import org.apache.camel.Consumer;
import org.apache.camel.Processor;
import org.apache.camel.Producer;
import org.apache.camel.impl.DefaultEndpoint;

public class DTRulesEndpoint extends DefaultEndpoint {

	@Override
	public Consumer createConsumer(Processor arg0) throws Exception {
		throw new UnsupportedOperationException(
				"There is no consumer for DTRules implemented.");
	}

	@Override
	public Producer createProducer() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isSingleton() {
		// TODO Auto-generated method stub
		return false;
	}

}
