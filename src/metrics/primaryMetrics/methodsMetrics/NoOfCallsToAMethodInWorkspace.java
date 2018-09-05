package metrics.primaryMetrics.methodsMetrics;

import java.util.ArrayList;

import vimc.metamodel.entity.MMethod;
import metrics.vimcInterface.MetricInterface;

/**
 * @author Oana Gugea
 *
 */

public class NoOfCallsToAMethodInWorkspace extends MetricInterface {
	
	/**
	 * @param
	 * 		source: first element is the method for which the metric is being compute
	 * 		destination: not used in this metric
	 * 
	 * @return Double: the number of calls to the given method in the current workspace
	 */

	@Override
	public double calculateMetric(ArrayList<Object> source, ArrayList<Object> destination) {
		MMethod mMethod = (MMethod) source.get(0);
		return mMethod.xNoOfCallsToAMethodInWorkspace();
	}

}
