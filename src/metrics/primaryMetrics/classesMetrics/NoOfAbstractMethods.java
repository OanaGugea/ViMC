package metrics.primaryMetrics.classesMetrics;

import java.util.ArrayList;

import vimc.metamodel.entity.MClass;
import metrics.vimcInterface.MetricInterface;

/**
 * @author Oana Gugea
 */
public class NoOfAbstractMethods extends MetricInterface {
	
	/**
	 * @param
	 * 		source: first element is the class for which the metric is being compute
	 * 				for all methods in the given class checks if they are abstract or
	 *           	not
	 * 		destination: not used in this metric
	 * 
	 * @return Double: the total number of abstract methods in the class given as a
	 *          parameter
	 */

	@Override
	public double calculateMetric(ArrayList<Object> source, ArrayList<Object> destination) {
		MClass mClass = (MClass) source.get(0);
		return mClass.xNoOfAbstractMethods();
	}

}
