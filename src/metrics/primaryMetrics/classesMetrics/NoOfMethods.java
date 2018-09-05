package metrics.primaryMetrics.classesMetrics;

import java.util.ArrayList;

import vimc.metamodel.entity.MClass;
import metrics.vimcInterface.MetricInterface;

/**
 * @author Oana Gugea
 */

public class NoOfMethods extends MetricInterface{

	/**
	 * @param
	 * 		source: first element is the class for which the metric is being compute
	 * 		destination: not used in this metric
	 * 
	 * @return Double: the total number of methods in the given class
	 */
	@Override
	protected double calculateMetric(ArrayList<Object> source, ArrayList<Object> destination) {
		MClass mClass = (MClass) source.get(0);
		return mClass.xNoOfMethods();
	}

}
