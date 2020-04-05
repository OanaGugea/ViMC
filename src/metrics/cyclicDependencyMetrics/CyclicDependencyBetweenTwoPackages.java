package metrics.cyclicDependencyMetrics;

import java.util.ArrayList;
import java.util.List;

import metrics.vimcInterface.MetricInterface;
import metrics.vimcInterface.MetricNotInitialised;
import vimc.metamodel.entity.MClass;
import vimc.metamodel.entity.MPackage;

public class CyclicDependencyBetweenTwoPackages extends MetricInterface {

	@Override
	protected double calculateMetric(ArrayList<Object> source, ArrayList<Object> destination) {
		// source: package
		// destination: package

		double cyclicDependency = 0;
		NoOfSourceClassInstancesInDestinationClass metric = new NoOfSourceClassInstancesInDestinationClass();

		MPackage sourcePackage = (MPackage) source.get(0);
		List<MClass> classesInSourcePackage = sourcePackage.classesGroup().getElements();

		MPackage destinationPackege = (MPackage) destination.get(0);
		List<MClass> classesInDestinationPackage = destinationPackege.classesGroup().getElements();

		ArrayList<Object> sourceClassList = new ArrayList<Object>();
		ArrayList<Object> destinationClassList = new ArrayList<Object>();

		try {
			for (MClass sourceClass : classesInSourcePackage) {
				sourceClassList.add(sourceClass);

				for (MClass destinationClass : classesInDestinationPackage) {
					destinationClassList.add(destinationClass);

					metric.calculate(sourceClassList, destinationClassList);
					cyclicDependency += metric.getMetricValue();

					destinationClassList.remove(destinationClass);
				}

				sourceClassList.remove(sourceClass);
			}
			if (cyclicDependency > 0) {

				cyclicDependency = 0;

				for (MClass destinationClass : classesInDestinationPackage) {
					destinationClassList.add(destinationClass);

					for (MClass sourceClass : classesInSourcePackage) {
						sourceClassList.add(sourceClass);

						metric.calculate(destinationClassList, sourceClassList);
						cyclicDependency += metric.getMetricValue();

						sourceClassList.remove(sourceClass);
					}
					destinationClassList.remove(destinationClass);
				}
			} else {
				return 0.0;
			}

		} catch (MetricNotInitialised e) {
			System.out.println("Metric Not Initialised");
			e.printStackTrace();
		}

		return cyclicDependency == 0.0 ? 0.0 : 1.0;
	}

}
