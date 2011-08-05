package org.apache.camel.component.dtrules;

public class DTrulesConfiguration {

	private String directory;
	private String ruleset;
	private String decisionTable;
	private String resultEntity;
	private String name = "DTRules.xml";
	private boolean trace = false;
	
	public String getDirectory() {
		return directory;
	}
	public void setDirectory(String directory) {
		this.directory = directory;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getRuleset() {
		return ruleset;
	}
	public void setRuleset(String ruleset) {
		this.ruleset = ruleset;
	}
	public String getDecisionTable() {
		return decisionTable;
	}
	public void setDecisionTable(String decisionTable) {
		this.decisionTable = decisionTable;
	}
	public String getResultEntity() {
		return resultEntity;
	}
	public void setResultEntity(String result) {
		this.resultEntity = result;
	}
	public boolean isTrace() {
		return trace;
	}
	public void setTrace(boolean trace) {
		this.trace = trace;
	}
	
}
