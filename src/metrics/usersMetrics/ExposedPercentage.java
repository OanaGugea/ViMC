package metrics.usersMetrics;

import java.util.ArrayList;

import metrics.primaryMetrics.classesMetrics.NoOfAccessorMethods;
import metrics.primaryMetrics.classesMetrics.NoOfConstructors;
import metrics.primaryMetrics.classesMetrics.NoOfMethods;
import metrics.vimcInterface.MetricInterface;
import metrics.vimcInterface.MetricNotInitialised;

public class ExposedPercentage extends MetricInterface {

	@Override
	public double calculateMetric(ArrayList<Object> source, ArrayList<Object> destination) {
		NoOfConstructors metric1 = new NoOfConstructors();
		metric1.calculate(source, destination);
		double noOfConstructors = 0;
		
		NoOfAccessorMethods metric2 = new NoOfAccessorMethods();
		metric2.calculate(source, destination);
		double noOfAccessorMethods = 0;
		
		NoOfMethods metric3 = new NoOfMethods();
		metric3.calculate(source, destination);
		double noOfMethods = 0;
		try {
			noOfConstructors = metric1.getMetricValue();
			noOfAccessorMethods = metric2.getMetricValue();
			noOfMethods = metric3.getMetricValue();
		} catch (MetricNotInitialised e) {
			System.out.println("Metric Not Initialised");
			e.printStackTrace();
		}

		return ((noOfConstructors + noOfAccessorMethods) / noOfMethods)*100;
	}

}
