package view.constructors;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.IPackageFragmentRoot;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.JavaModelException;

import vimc.metamodel.entity.MClass;
import vimc.metamodel.entity.MPackage;
import vimc.metamodel.factory.Factory;
import view.model.ExpressionTerm;

public class NewMetricClassGenerator {
	
	private static String NAME_OF_THIS_PLUGIN = "ViMC";
	
	private String newMetricsName;
	private ArrayList<ExpressionTerm> expressionTermList;
	
	public NewMetricClassGenerator(String metricName, String expression) {
		this.newMetricsName = metricName;
		expressionTermList = parseExpression(expression);
	}
	
	public void generateNewMetricClass() {
		String classCode = createClassCode();
		System.out.println("[ViMC] Info: A new metric with the following code will be generated ...\n\n");
		System.out.println(classCode);
		
		IPackageFragment usersMetricsPackage = getNewMetricsPackage();
		try {
			ICompilationUnit compilationUnit = 
					usersMetricsPackage.createCompilationUnit(
							newMetricsName + ".java", classCode, false, null);
			compilationUnit.save(null, true);
			System.out.println(compilationUnit.getPath().toString());
			
		} catch (JavaModelException e) {
			System.out.println("[ViMC] Error : Could not generate class for the new metric!");
			e.printStackTrace();
		}
	}
	
	private IPackageFragment getNewMetricsPackage() {
		IWorkspace thisWorkspace = ResourcesPlugin.getWorkspace();
		IWorkspaceRoot thisWorkspacesRoot = thisWorkspace.getRoot();
		IProject thisProject = thisWorkspacesRoot.getProject(NAME_OF_THIS_PLUGIN);
		IJavaProject thisJavaProject = JavaCore.create(thisProject);
		
		IFolder srcFolder = thisProject.getFolder("src");
		IPackageFragmentRoot packageFragmentRoot = 
				thisJavaProject.getPackageFragmentRoot(srcFolder);
		IPackageFragment usersMetricsPackage = packageFragmentRoot.getPackageFragment("usersmetrics");
		return usersMetricsPackage;
	}
	
	private String createClassCode() {
		StringBuilder newMetricsCode = new StringBuilder("");
		newMetricsCode.append("package usersmetrics;\n\n");
		newMetricsCode.append("import java.util.ArrayList;\n");
		newMetricsCode = addImportsForMetrics(newMetricsCode);
		newMetricsCode.append("import metrics.vimcInterface.MetricInterface;\n\n");
		newMetricsCode.append("import metrics.vimcInterface.MetricNotInitialised;\n\n");
		newMetricsCode.append("public class ");
		newMetricsCode.append(newMetricsName);
		newMetricsCode.append(" extends MetricInterface {\n\n");
		newMetricsCode.append("	@Override\n");
		newMetricsCode.append("	protected double calculateMetric(ArrayList<Object> source, ArrayList<Object> destination) {\n");
		newMetricsCode.append("		try {\n");
		newMetricsCode = constructTryBody(newMetricsCode);
		newMetricsCode.append("		} catch (MetricNotInitialised e) {\n");
		newMetricsCode.append("		    System.out.println(\"Metric Not Initialised\");\n");
		newMetricsCode.append("		    e.printStackTrace();\n");
		newMetricsCode.append("		}\n");
		newMetricsCode.append("		return 0;\n");
		newMetricsCode.append("	}\n");
		newMetricsCode.append("}\n");
		
		return newMetricsCode.toString();
	}
	
	private StringBuilder addImportsForMetrics(StringBuilder newMetricsCode) {
		
		for(ExpressionTerm eachTerm : expressionTermList) {
			String metricName = eachTerm.getMetricName();
			String packagePath = getPackagePathFor(metricName);
			newMetricsCode.append("import ");
			newMetricsCode.append(packagePath + ";\n");
		}
		return newMetricsCode;
	}

	private String getPackagePathFor(String metricName) {
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
		String packagePath = null;
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
						String nameOfEachMClass = 
							eachMClass.getUnderlyingObject().getElementName();
						if(nameOfEachMClass.equals(metricName)) {
							packagePath = 
								eachMClass.getUnderlyingObject().getFullyQualifiedName();
						}
					}
				}
			} catch (JavaModelException e) {
				e.printStackTrace();
			}
		}
		return packagePath;
	}

	private ArrayList<ExpressionTerm> parseExpression(String expression){
		
		//The regular expression for a label is the following:
		//((([0-9]+\.[0-9]+\*)*[a-zA-Z0-9]+)+[-\+\*]?)+
		
		//In order to get the multipliers, names of the metrics and operators involved,
		//we need to parse the expression (label).
		
		//In order to get the multiplier and metric names, we need to consider the
		//operators as delimiters between the pairs of (multiplier, metric name).
		Pattern operatorPattern = Pattern.compile("[+\\-\\*]");
		String[] multiplierOrMetric = operatorPattern.split(expression);
		
		//In order to get the operators, we need to consider the pairs of (multiplier, metric name)
		//as delimiters between the operators.
		Pattern multiplierAndMetricNamePattern = 
				Pattern.compile("([0-9]+\\.[0-9]+\\*)*[a-zA-Z0-9]+");
		String[] operators = multiplierAndMetricNamePattern.split(expression);
		
		//remove all empty String elements from operatorsList
		ArrayList<String> operatorsList = new ArrayList<String>(Arrays.asList(operators));

        ArrayList<String> emptyString = new ArrayList<String>();
        emptyString.add("");
        operatorsList.removeAll(emptyString);
        emptyString = null;
        
        //start constructing the ExpressionTerms List
        int numberOfTerms = operatorsList.size() + 1;
        ArrayList<ExpressionTerm> expressionTermList = 
        		new ArrayList<ExpressionTerm>(numberOfTerms);
        
        //set the operator for each term in the expression
        for(int i = 0; i < numberOfTerms; i++) {
        	ExpressionTerm eachTerm = new ExpressionTerm();
        	eachTerm.setMultiplier(null);
        	eachTerm.setMetricName(null);
        	if(i < numberOfTerms - 1) {
				eachTerm.setOperator(operatorsList.get(i));
        	}
        	else {
        		eachTerm.setOperator("");
        	}

        	expressionTermList.add(eachTerm);
        }
        
        //set the metric name for each term in the expression
        int termIndex = 0;
        for(String eachMultiplierOrMetric : multiplierOrMetric) {
        	if(Pattern.matches("[a-zA-Z0-9]+", eachMultiplierOrMetric)) {
        		expressionTermList.get(termIndex).setMetricName(eachMultiplierOrMetric);
        		termIndex++;
        	}
        }
        
        //set the multipliers for each term in the expression
        for(int i = 0; i < multiplierOrMetric.length; i++) {
        	if(Pattern.matches("[0-9]+\\.[0-9]+", multiplierOrMetric[i])) {
        		String thisMultipliersCorespondingMetric = multiplierOrMetric[i+1];
        		for(ExpressionTerm eachTerm : expressionTermList) {
        			if(eachTerm.getMetricName().equals(thisMultipliersCorespondingMetric)) {
        				eachTerm.setMultiplier(multiplierOrMetric[i]);
        				break;
        			}
        		}
        	}
        }
        
        //check for null multipliers and set them to "1"
        for(ExpressionTerm eachTerm : expressionTermList) {
        	if(eachTerm.getMultiplier() == null) {
        		eachTerm.setMultiplier("1");
        	}
        }
        
        return expressionTermList;
	}
	
	private StringBuilder constructTryBody(StringBuilder builder) {
		for(ExpressionTerm eachTerm : this.expressionTermList) {
			String metric = eachTerm.getMetricName();
			
			//create object of the metric's type
			//MetricNameHere metricnamehereMetric = new MetricNameHere();
			builder.append("\n			" + metric + " ");
			builder.append(metric.toLowerCase() + "Metric = new ");
			builder.append(metric + "();\n");
			
			//call the calculate method on that object
			//metricnamehereMetric.calculate(source, destination);
			builder.append("			" + metric.toLowerCase() + "Metric.calculate(source, destination);\n\n");
		}
		//create the return statement
		builder.append("			return ");
		for(ExpressionTerm eachTerm : this.expressionTermList) {
			builder.append(eachTerm.getMultiplier() + "*");
			builder.append(eachTerm.getMetricName().toLowerCase() + "Metric.getMetricValue()");
			builder.append(eachTerm.getOperator() + "\n				");
		}
		builder.append(";\n");
		return builder;
	}
}
