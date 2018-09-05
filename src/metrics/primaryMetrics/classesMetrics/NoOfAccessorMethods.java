package metrics.primaryMetrics.classesMetrics;

import java.util.ArrayList;

import vimc.metamodel.entity.MClass;
import metrics.vimcInterface.MetricInterface;

/**
 * @author Oana Gugea
 */

public class NoOfAccessorMethods extends MetricInterface {
	
	/**
	 * @param
	 * 		source: first element is the class for which the metric is being compute
	 * 				a class for which every method is tested if is accessor method
	 * 		destination: not used in this metric
	 * 
	 * @return Double: the total number of accessor methods found in the class given as
	 *          parameter
	 */

	@Override
	public double calculateMetric(ArrayList<Object> source, ArrayList<Object> destination) {
		MClass mClass = (MClass) source.get(0);
		return mClass.xNoOfAccessorMethods();
	}
}
