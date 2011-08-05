package org.apache.camel.component.dtrules;

import org.apache.camel.Consumer;
import org.apache.camel.Processor;
import org.apache.camel.Producer;
import org.apache.camel.impl.DefaultEndpoint;
import org.apache.camel.util.ObjectHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DTRulesEndpoint extends DefaultEndpoint {
	
	private static final Logger LOG = LoggerFactory.getLogger(DTRulesEndpoint.class);

	private final DTrulesConfiguration config;
	
	public DTRulesEndpoint(String uri, DTRulesComponent component, DTrulesConfiguration config) {
		super(uri, component);
		this.config = config;
		
		// the rule directory has to be set
        if (ObjectHelper.isEmpty(this.config.getDirectory())) {
            throw new IllegalArgumentException("Rule directory is missing.");
        }
	}

	@Override
	public Consumer createConsumer(Processor processor) throws Exception {
		throw new UnsupportedOperationException(
				"There is no consumer for DTRules implemented.");
	}

	@Override
	public Producer createProducer() throws Exception {
		return new DTRulesProducer(this);
	}

	@Override
	public boolean isSingleton() {
		return true;
	}

	public DTrulesConfiguration getConfig() {
		return config;
	}

}
