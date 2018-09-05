package metrics.primaryMetrics.methodsMetrics;

import java.util.ArrayList;

import vimc.metamodel.entity.MMethod;
import metrics.vimcInterface.MetricInterface;

/**
 * @author Oana Gugea
 */

public class NoOfArguments extends MetricInterface {
	
	/**
	 * @param
	 * 		source: first element is the method for which the metric is being compute
	 * 		destination: not used in this metric
	 * 
	 * @return Double: the number of arguments of the given method
	 */

	@Override
	public double calculateMetric(ArrayList<Object> source, ArrayList<Object> destination) {
		MMethod mMethod = (MMethod) source.get(0);
		return mMethod.xNoOfArguments();
	}

}
