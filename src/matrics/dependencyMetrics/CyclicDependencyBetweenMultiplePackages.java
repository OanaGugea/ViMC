package matrics.dependencyMetrics;

import java.util.ArrayList;
import java.util.List;

import metrics.vimcInterface.MetricInterface;
import metrics.vimcInterface.MetricNotInitialised;
import vimc.metamodel.entity.MClass;
import vimc.metamodel.entity.MPackage;

public class CyclicDependencyBetweenMultiplePackages extends MetricInterface {

	@Override
	protected double calculateMetric(ArrayList<Object> source, ArrayList<Object> destination) {
		// source: list of packages
		// destination: not used

		double cyclicDependency = 0;
		double temporaryDependeny = 0;
		NoOfSourceClassInstancesInDestinationClass metric = new NoOfSourceClassInstancesInDestinationClass();

		for (int i = 0; i < source.size(); i++) {
			MPackage sourcePackage = (MPackage) source.get(i);
			List<MClass> classesInSourcePackage = sourcePackage.classesGroup().getElements();

			MPackage destinationPackege = (MPackage) source.get(i + 1);
			List<MClass> classesInDestinationPackage = destinationPackege.classesGroup().getElements();

			try {
				for (MClass sourceClass : classesInSourcePackage) {
					ArrayList<Object> sourceClassList = new ArrayList<Object>();
					sourceClassList.add(sourceClass);

					for (MClass destinationClass : classesInDestinationPackage) {
						ArrayList<Object> destinationClassList = new ArrayList<Object>();
						destinationClassList.add(destinationClass);

						metric.calculate(sourceClassList, destinationClassList);
						temporaryDependeny += metric.getMetricValue();
					}
				}
				if (temporaryDependeny == 0) {
					return 0.0;
				}
				cyclicDependency += temporaryDependeny;
				temporaryDependeny = 0;

			} catch (MetricNotInitialised e) {
				System.out.println("Metric Not Initialised");
				e.printStackTrace();
			}
		}

		return cyclicDependency == 0.0 ? 0.0 : 1.0;
	}

}
