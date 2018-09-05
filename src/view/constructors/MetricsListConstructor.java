package view.constructors;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.JavaModelException;

import vimc.metamodel.entity.MClass;
import vimc.metamodel.entity.MPackage;
import vimc.metamodel.factory.Factory;

public class MetricsListConstructor {
	
	private static String NAME_OF_THIS_PLUGIN = "ViMC";
	private static String METRIC_INTERFACE = "MetricInterface";
	private String[] listOfAllMetricsImplemented = null;

	/**
	 * Returns all the metrics from the plug-in project (the default metrics
	 * and the metrics that the user implemented), which extend the
	 * MetricInterface abstract class.
	 * @return String[] - array containing the names of all the metrics
	 * in the project
	 */
	public String[] getListOfAllMetricsImplemented() {
		computeList();
		return listOfAllMetricsImplemented;
	}
	
	private void computeList() {
		
		IWorkspace thisWorkspace = ResourcesPlugin.getWorkspace();
		IWorkspaceRoot thisWorkspacesRoot = thisWorkspace.getRoot();

		IProject thisProject = thisWorkspacesRoot.getProject(NAME_OF_THIS_PLUGIN);
		if(!thisProject.isOpen()) {
			try {
				thisProject.open(null);
			} catch (CoreException e) {
				e.printStackTrace();
			}
		}
		IJavaProject thisJavaProject = JavaCore.create(thisProject);
		
		ArrayList<String> allMetrics = new ArrayList<String>();
		if(thisJavaProject != null)
		{
			try {
				IPackageFragment[] allPackages = thisJavaProject.getPackageFragments();
				// for all packages in the current project
				for (IPackageFragment p : allPackages) {
					MPackage mPackage = Factory.getInstance().createMPackage(p);
					List<MClass> mClassesInPackage = mPackage.classesGroup().getElements();
					// for all classes in each package
					for (MClass eachMClass : mClassesInPackage) {
						String nameOfSuperClass;
						nameOfSuperClass = eachMClass.getUnderlyingObject().getSuperclassName();
						// add to the list the name of the class which extends MetricInterface
						if (nameOfSuperClass != null) {
							if (nameOfSuperClass.equals(METRIC_INTERFACE)) {
								allMetrics.add(eachMClass.getUnderlyingObject().getElementName());
							}
						}
					}
				}
			} catch (JavaModelException e) {
				e.printStackTrace();
			}
			
			listOfAllMetricsImplemented = new String[allMetrics.size()];
			
			int i=0;
			for(String eachMetricName : allMetrics) {
				listOfAllMetricsImplemented[i] = eachMetricName;
				i++;
			}
		}
		else
			System.out.println("Plugin project not found!");
	}

}
