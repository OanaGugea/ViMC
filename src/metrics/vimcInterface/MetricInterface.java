package metrics.vimcInterface;

import java.util.ArrayList;

/**
 * @author Oana Gugea
 */

public abstract class MetricInterface {

	private boolean initialised = false;
	private double metricValue;

	public void calculate(ArrayList<Object> source, ArrayList<Object> destination) {
		metricValue = calculateMetric(source, destination);
		setInitialised();
	}

	/**
	 * 
	 * @param source
	 * @param destination
	 * @return metric value
	 * 
	 * this method needs to be overridden on every metric implementation
	 */
	protected abstract double calculateMetric(ArrayList<Object> source, ArrayList<Object> destination);

	public void setInitialised() {
		this.initialised = true;
	}

	public boolean isInitialised() {
		return initialised;
	}

	public double getMetricValue() throws MetricNotInitialised {
		if (!initialised)
			throw new MetricNotInitialised();
		return metricValue;
	}

	public void setMetricValue(double metricValue) {
		this.metricValue = metricValue;
	}

}
