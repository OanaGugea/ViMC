package view.constructors;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
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
import vimc.metamodel.entity.MInterface;
import vimc.metamodel.entity.MMethod;
import vimc.metamodel.entity.MPackage;
import vimc.metamodel.factory.Factory;

public class ResultCalculator {
	
	private static String NAME_OF_THIS_PLUGIN = "ViMC";
	
	private String selectedMetric;
	private ArrayList<Object> sourceParameters;
	private ArrayList<Object> destinationParameters;
		
	public ResultCalculator(String selectedMetric){
		this.selectedMetric = selectedMetric;
	}
	
	public void setSourceParameters(Object[] sourceParams) {
		this.sourceParameters = new ArrayList<Object>();
		for(Object sourceParam : sourceParams) {
			if(sourceParam instanceof MMethod) {
				this.sourceParameters.add((MMethod) sourceParam);
			}
			else if(sourceParam instanceof MClass) {
				this.sourceParameters.add((MClass) sourceParam);
			}
			else if(sourceParam instanceof MInterface) {
				this.sourceParameters.add((MInterface) sourceParam);
			}
			else if(sourceParam instanceof MPackage) {
				this.sourceParameters.add((MPackage) sourceParam);
			}
		}
	}
	
	public void setDestinationParameters(Object[] destinationParams) {
		this.destinationParameters = new ArrayList<Object>();
		for(Object destinationParam : destinationParams) {
			if(destinationParam instanceof MMethod) {
				this.destinationParameters.add((MMethod) destinationParam);
			}
			else if(destinationParam instanceof MClass) {
				this.destinationParameters.add((MClass) destinationParam);
			}
			else if(destinationParam instanceof MInterface) {
				this.destinationParameters.add((MInterface) destinationParam);
			}
			else if(destinationParam instanceof MPackage) {
				this.destinationParameters.add((MPackage) destinationParam);
			}
		}
	}

	public String computeResult() {
		MClass metricMClass = findMClassForSelectedMetric();
		try {
			Class<?> clazz = Class.forName(
					metricMClass.getUnderlyingObject().getFullyQualifiedName());
			Object metricInstance = clazz.newInstance();
			
			Class<?>[] parameterTypes = null;
			Method[] allmethods = clazz.getMethods();
			
			for(Method aMethod : allmethods) {
				if(aMethod.getName().equals("calculate")) {
					parameterTypes = aMethod.getParameterTypes();
					break;
				}
			}
						
			Method calculate = clazz.getMethod("calculate", parameterTypes);
			calculate.invoke(metricInstance, sourceParameters, destinationParameters);
			
			Class noparams[] = {};
			Method getResult = clazz.getMethod("getMetricValue", noparams);
			
			Object[] noparameters = null;
			return Double.toString((double)getResult.invoke(metricInstance, noparameters));
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		return "";
	}
	
	private MClass findMClassForSelectedMetric() {
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
		
		MClass metricMClass = null;
		IJavaProject thisJavaProject = JavaCore.create(thisProject);
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
						if(selectedMetric.equals(eachMClass.getUnderlyingObject().getElementName())) {
							metricMClass = eachMClass;
							break;
						}
					}
				}
			} catch (JavaModelException e) {
				e.printStackTrace();
			}
		}
		return metricMClass;
	}
	
}
