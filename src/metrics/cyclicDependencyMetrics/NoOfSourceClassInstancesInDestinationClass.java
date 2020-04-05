package metrics.cyclicDependencyMetrics;

import java.util.ArrayList;

import org.eclipse.jdt.core.IField;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.internal.corext.util.JavaModelUtil;

import metrics.vimcInterface.MetricInterface;
import vimc.metamodel.entity.MClass;

public class NoOfSourceClassInstancesInDestinationClass extends MetricInterface {

	@SuppressWarnings("restriction")
	@Override
	public double calculateMetric(ArrayList<Object> source, ArrayList<Object> destination) {
		// destination: class in which we search for references to source class
		
		double noOfInstances = 0.0;

		MClass destinationClass = (MClass) destination.get(0);
		MClass sourceClass = (MClass) source.get(0);

		try {
			IField[] destinationFields = destinationClass.getUnderlyingObject().getFields();
			for (IField field : destinationFields) {
				String fieldType = JavaModelUtil.getResolvedTypeName(field.getTypeSignature(),
						field.getTypeRoot().findPrimaryType());

				String classType = sourceClass.getUnderlyingObject().getFullyQualifiedName();

				if (fieldType.equals(classType)) {
					noOfInstances++;
				}
			}

		} catch (JavaModelException e) {
			e.printStackTrace();
		}

		return noOfInstances;
	}

}
