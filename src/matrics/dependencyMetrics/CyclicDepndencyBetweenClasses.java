package matrics.dependencyMetrics;

import java.util.ArrayList;

import metrics.vimcInterface.MetricInterface;
import metrics.vimcInterface.MetricNotInitialised;

public class CyclicDepndencyBetweenClasses extends MetricInterface {

	@Override
	protected double calculateMetric(ArrayList<Object> source, ArrayList<Object> destination) {
		// source: class
		// destination: class

		double cyclicDependency = 0;
		NoOfSourceClassInstancesInDestinationClass metric = new NoOfSourceClassInstancesInDestinationClass();

		try {
			metric.calculate(source, destination);
			cyclicDependency += metric.getMetricValue();

			if (cyclicDependency != 0) {
				metric.calculate(destination, source);
				cyclicDependency += metric.getMetricValue();
			}

		} catch (MetricNotInitialised e) {
			System.out.println("Metric Not Initialised");
			e.printStackTrace();
		}

		return cyclicDependency == 0.0 ? 0.0 : 1.0;
	}

}
