package metrics.primaryMetrics.classesMetrics;

import java.util.ArrayList;

import vimc.metamodel.entity.MClass;
import metrics.vimcInterface.MetricInterface;

/**
 * @author Oana Gugea
 *
 */

public class BaseClassOverridingRatio extends MetricInterface {
	/**
	 * @param
	 * 		source: first element is the class for which the metric is being compute
	 * 		destination: not used in this metric
	 * 
	 * @return Double: number of methods of the measured class that override
	 *         methods from the base class, divided by the total number of methods
	 *         in the class.
	 */

	@Override
	public double calculateMetric(ArrayList<Object> source, ArrayList<Object> destination) {
		MClass mClass = (MClass) source.get(0);
		return mClass.xBaseClassOverridingRatio();
	}

}
