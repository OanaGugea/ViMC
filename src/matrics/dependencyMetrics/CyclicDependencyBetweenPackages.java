package matrics.dependencyMetrics;

import java.util.ArrayList;
import java.util.List;

import metrics.vimcInterface.MetricInterface;
import metrics.vimcInterface.MetricNotInitialised;
import vimc.metamodel.entity.MClass;
import vimc.metamodel.entity.MPackage;

public class CyclicDependencyBetweenPackages extends MetricInterface{

	@Override
	protected double calculateMetric(ArrayList<Object> source, ArrayList<Object> destination) {
		// source: package
		// destination: package
		
		double cyclicDependency = 0;
		CyclicDepndencyBetweenClasses metric = new CyclicDepndencyBetweenClasses();
		
		MPackage sourcePackage = (MPackage) source.get(0);
		List<MClass> classesInSourcePackage = sourcePackage.classesGroup().getElements();
		
		MPackage destinationPackege = (MPackage) destination.get(0);
		List<MClass> classesInDestinationPackage = destinationPackege.classesGroup().getElements();
		
		try {
			for (MClass sourceClass : classesInSourcePackage) {
				ArrayList<Object> sourceClassList = new ArrayList<Object>();
				sourceClassList.add(sourceClass);
				
				for (MClass destinationClass : classesInDestinationPackage) {
					ArrayList<Object> destinationClassList = new ArrayList<Object>();
					destinationClassList.add(destinationClass);
					
					metric.calculate(sourceClassList, destinationClassList);
					cyclicDependency += metric.getMetricValue();
				}
			}
			
		} catch (MetricNotInitialised e) {
			System.out.println("Metric Not Initialised");
			e.printStackTrace();
		}

		return cyclicDependency;
	}

}
