package metrics.RESTDependencyMetrics;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.jdt.core.IAnnotation;
import org.eclipse.jdt.core.IMethod;
import org.eclipse.jdt.core.JavaModelException;

import metrics.vimcInterface.MetricInterface;
import vimc.metamodel.entity.MClass;
import vimc.metamodel.entity.MMethod;

public class ClassRESTDependency extends MetricInterface {
	private ArrayList<String> superClassNames = new ArrayList<String>();
	private ArrayList<String> methodsAnnotations = new ArrayList<String>();
	private String fileName = "/Users/oana.gugea/git/ViMC/src/metrics/RESTDependencyMetrics/dependencyPatterns.txt";

	private void readFile() {
		try {
			BufferedReader myReader = new BufferedReader(new FileReader(new File(fileName)));
			String rowData;
			while ((rowData = myReader.readLine()) != null) {
				String[] data = rowData.split(":");
				switch (data[0]) {
				case "extends": {
					superClassNames.add(data[1]);
					break;
				}
				case "method": {
					methodsAnnotations.add(data[1]);
					break;
				}
				}
			}
			myReader.close();
		} catch (FileNotFoundException e) {
			System.out.println("An error occurred.");
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("An error occurred.");
			e.printStackTrace();
		}
	}

	@Override
	protected double calculateMetric(ArrayList<Object> source, ArrayList<Object> destination) {
		// source: class file in which we search for identifying REST API dependency
		// patterns

		this.readFile();

		MClass mClass = (MClass) source.get(0);
		double restMethods = 0;
		double methods = 0;

		try {
			String superClassName = mClass.getUnderlyingObject().getSuperclassName();
			if (superClassNames.contains(superClassName)) {
				List<MMethod> allMethods = mClass.methodsGroup().getElements();
				for (MMethod met : allMethods) {
					methods++;
					IMethod m = met.getUnderlyingObject();
					IAnnotation[] allAnnotations = m.getAnnotations();
					for (IAnnotation a : allAnnotations) {
						if (methodsAnnotations.contains(a.getElementName())) {
							restMethods++;
						}
					}
				}
			}
		} catch (JavaModelException e) {
			e.printStackTrace();
		}
		if (methods != 0) {
			return (restMethods / methods);
		}
		return 0;
	}

}
