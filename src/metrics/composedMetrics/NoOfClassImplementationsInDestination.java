package metrics.composedMetrics;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jdt.core.JavaModelException;

import vimc.metamodel.entity.MClass;
import vimc.metamodel.entity.MPackage;
import metrics.vimcInterface.MetricInterface;

public class NoOfClassImplementationsInDestination extends MetricInterface {

	@Override
	public double calculateMetric(ArrayList<Object> source, ArrayList<Object> destination) {
		// source: list with classes for which we search for sub classes
		// destination: list of packages in which we search for class implementations
		MClass mClass = null;
		MPackage mPackage = null;
		int noOfSubClasses = 0;

		for (int i = 0; i < source.size(); i++) {
			mClass = (MClass) source.get(i);
			// current class name
			String superClassName = mClass.getUnderlyingObject().getElementName();
			for (int j = 0; j < destination.size(); j++) {
				// for all packages in the destination list
				mPackage = (MPackage) destination.get(j);
				List<MClass> mClassesInPackage = mPackage.classesGroup().getElements();
				// for all classes in the package
				for (MClass eachMClass : mClassesInPackage) {
					String nameOfClass;
					try {
						nameOfClass = eachMClass.getUnderlyingObject().getSuperclassName();
						// count the number of classes which have as super class the class given as
						// argument
						if (nameOfClass != null) {
							if (nameOfClass.equals(superClassName)) {
								noOfSubClasses++;
							}
						}
					} catch (JavaModelException e) {
						e.printStackTrace();
					}
				}
			}
		}

		return noOfSubClasses;
	}

}
