package org.apache.camel.component.dtrules;

import java.util.Map;

import org.apache.camel.Exchange;
import org.apache.camel.impl.DefaultProducer;
import org.apache.camel.util.ObjectHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dtrules.entity.IREntity;
import com.dtrules.infrastructure.RulesException;
import com.dtrules.mapping.Mapping;
import com.dtrules.session.IRSession;
import com.dtrules.session.RuleSet;
import com.dtrules.session.RulesDirectory;

public class DTRulesProducer extends DefaultProducer {

	private static final Logger LOG = LoggerFactory.getLogger(DTRulesProducer.class);
	
	private Mapping mapping;
	private IRSession session;
	private DTrulesConfiguration config;
	private boolean dt = false;
	private boolean re = false;
	
	public static final String DECISION_TABLE 	= "dtrules.decisionTable";
	public static final String RESULT_ENTITY 	= "dtrules.resultEntity";
	
	public DTRulesProducer(DTRulesEndpoint endpoint) {
		super(endpoint);
		this.config = endpoint.getConfig();
		
		// try to init dtrules
		try {
			this.initDTRules();
		} catch (RulesException e) {
			LOG.error(String.format("Cannot establish DTRules session with the following parameters: directory --> '%s' name --> '%s' ruleset --> '%s'", 
									this.config.getDirectory(), 
									this.config.getName(), 
									this.config.getRuleset()), e);
		}
		
		// check if decision table and result entity have been set through configuration
		this.checkConfiguration();
	}

	@Override
	public void process(Exchange exchange) throws Exception {
		
		// check headers
		Map<String, Object> headers = exchange.getIn().getHeaders();
		
		String table = null;
		String entity = null;
		
		// decision table
		if(!this.dt) {
			if(!headers.containsKey(DECISION_TABLE)) {
				throw new IllegalArgumentException("Decision table is missing. Please provide it over url or in the header.");
			} else {
				table = (String) headers.get(DECISION_TABLE);
			}
		} else {
			table = this.config.getDecisionTable();
		}
		
		// result entity
		if(!this.re) {
			if(!headers.containsKey(RESULT_ENTITY)) {
				throw new IllegalArgumentException("Result entity is missing. Please provide it over url or in the header.");
			} else {
				entity = (String) headers.get(RESULT_ENTITY);
			}
		} else {
			entity = this.config.getResultEntity();
		}
		
		String data = exchange.getIn().getBody(String.class);
		exchange.getIn().setBody(this.fireRules(data, table, entity));
	}
	
	/**
	 * creates a ruleset from the given configuration
	 * @throws RulesException 
	 */
	private void initDTRules() throws RulesException {
		RulesDirectory rd      = new RulesDirectory(
				this.config.getDirectory(),
				this.config.getName());
		
		RuleSet rs = rd.getRuleSet(this.config.getRuleset());
		this.session = rs.newSession();
		this.mapping = this.session.getMapping();
		
	}
	
	/**
	 * fires a rule for the given entity
	 * @throws RulesException 
	 */
	private String fireRules(String data, String table, String entity) throws RulesException {
		this.mapping.loadStringData(this.session, this.config.getRuleset(), data);
		this.session.execute(table);
		IREntity result = this.session.getState().findEntity(entity);
		return result.toString();
	}
	
	/**
	 * checks if decision table and result entity have been set over
	 * the url properties.
	 */
	private void checkConfiguration() {
		
		if(ObjectHelper.isNotEmpty(this.config.getDecisionTable())){
			this.dt = true;
		}
		
		if(ObjectHelper.isNotEmpty(this.config.getResultEntity())) {
			this.re = true;
		}
	}

}
