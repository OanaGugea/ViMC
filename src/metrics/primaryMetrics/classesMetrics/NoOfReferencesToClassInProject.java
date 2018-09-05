package metrics.primaryMetrics.classesMetrics;

import java.util.ArrayList;

import vimc.metamodel.entity.MClass;
import metrics.vimcInterface.MetricInterface;

/**
 * @author Oana Gugea
 *
 */

public class NoOfReferencesToClassInProject extends MetricInterface {
	
	/**
	 * @param source:first element is the class for which the metric is being compute
	 *        destination: not used in this metric
	 * 
	 * @return Double: value representing the number of references to the given
	 *         class in the current project
	 */

	@Override
	public double calculateMetric(ArrayList<Object> source, ArrayList<Object> destination) {
		MClass mClass = (MClass) source.get(0);
		return mClass.xNoOfReferencesToClassInProject();
	}


}
