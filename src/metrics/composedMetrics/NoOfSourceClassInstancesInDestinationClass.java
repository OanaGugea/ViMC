package metrics.composedMetrics;

import java.util.ArrayList;

import org.eclipse.jdt.core.IField;
import org.eclipse.jdt.core.JavaModelException;

import metrics.vimcInterface.MetricInterface;
import vimc.metamodel.entity.MClass;

public class NoOfSourceClassInstancesInDestinationClass extends MetricInterface {

	@SuppressWarnings("static-access")
	@Override
	public double calculateMetric(ArrayList<Object> source, ArrayList<Object> destination) {
		// destination: class in which we search for references to source class
		
		MClass destinationClass = (MClass) destination.get(0);
		MClass sourceClass = (MClass) source.get(0);
		
		int noOfSourceInstancesInDestination = 0;
		
		try {
			IField[] destinationFields = destinationClass.getUnderlyingObject().getFields();
			for(IField field: destinationFields) {
				if(field.TYPE == sourceClass.getUnderlyingObject().TYPE) {
					noOfSourceInstancesInDestination++;
				}
			}
	
		} catch (JavaModelException e) {
			e.printStackTrace();
		} 

		return noOfSourceInstancesInDestination;
	}

}
