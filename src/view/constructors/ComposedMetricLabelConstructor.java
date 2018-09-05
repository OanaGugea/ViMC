package view.constructors;

public class ComposedMetricLabelConstructor {
	
	private final static String MULTIPLICATION_OPERATOR = "*";
	
	private StringBuilder metricFormulaBuilder;
	
	public ComposedMetricLabelConstructor() {
		metricFormulaBuilder = new StringBuilder("");
	}
	
	public void addMultiplierToFormula(Double multiplierValue) {
		metricFormulaBuilder.append(Double.toString(multiplierValue));
		metricFormulaBuilder.append(MULTIPLICATION_OPERATOR);
	}
	
	public void addMetricNameToFormula(String metricName) {
		metricFormulaBuilder.append(metricName);
	}
	
	public void addOperatorToFormula(String operator) {
		metricFormulaBuilder.append(operator);
	}
	
	public String getFormula() {
		return metricFormulaBuilder.toString();
	}
}
