package view.model;

public class ExpressionTerm {
	
	private String multiplier;
	private String metricName;
	private String operator;
	
	public String getMultiplier() {
		return multiplier;
	}
	public void setMultiplier(String multiplier) {
		this.multiplier = multiplier;
	}
	public String getMetricName() {
		return metricName;
	}
	public void setMetricName(String metricName) {
		this.metricName = metricName;
	}
	public String getOperator() {
		return operator;
	}
	public void setOperator(String operator) {
		this.operator = operator;
	}
	
	public String toString() {
		return "\nMultiplier: " + multiplier 
				+ " ; Name: " + metricName 
				+ " ; Operator: " + operator;
	}
}
