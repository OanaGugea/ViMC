package metrics.primaryMetrics.classesMetrics;

import java.util.ArrayList;

import vimc.metamodel.entity.MClass;
import metrics.vimcInterface.MetricInterface;

/**
 * @author Oana Gugea 
 * 
 */

public class NoOfSubClassesOfClassInPackage extends MetricInterface {

	/**
	 * @param
	 * 		source: first element is the class for which the metric is being compute
	 * 		destination: not used in this metric
	 * 
	 * @return Double: the total number of classes in current package which extend the current
	 *          class
	 */

	@Override
	public double calculateMetric(ArrayList<Object> source, ArrayList<Object> destination) {
		MClass mClass = (MClass) source.get(0);
		return mClass.xNoOfSubClassesOfClassInPackage();
	}
}
