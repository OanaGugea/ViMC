package xmetrics.utils;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaModelException;

import vimc.metamodel.entity.MClass;
import vimc.metamodel.entity.MPackage;
import vimc.metamodel.factory.Factory;


public final class UtilityClass {
	
	public static ArrayList<IType[]> getITypesFromIPackageFragment (IPackageFragment iPackageFragment) throws JavaModelException{
		
		ICompilationUnit[] compilationUnits= iPackageFragment.getCompilationUnits();
		ArrayList<IType[]> iTypes = new ArrayList<IType[]>();
		for(ICompilationUnit compUnit : compilationUnits){
			iTypes.add(compUnit.getAllTypes());
		}
		return iTypes;
	}
	
	/**
	 * @author Tamara Avramovic
	 * @return the superclass of the class
	 */
	public static IType getSuperClassForClass(IType myClass) {
		
		try {
			String mySuperclassName = myClass.getSuperclassName();
	
			// the package in which the current class is located
			IPackageFragment iPackageFragment = myClass.getPackageFragment();
			// the Java Project for the current classes hierarchy
			IJavaProject iJavaProject = iPackageFragment.getJavaProject();
						
			IPackageFragment[] allPackages = iJavaProject.getPackageFragments();
			for (IPackageFragment p : allPackages) {
				MPackage mPackage = Factory.getInstance().createMPackage(p);
				List<MClass> mClassesInPackage = mPackage.classesGroup().getElements();
				// for all classes in each package
				for (MClass eachMClass : mClassesInPackage) {
					String nameOfClassFound;
					nameOfClassFound = eachMClass.getUnderlyingObject().getElementName();
					// count the number of classes which have as super class the class given as argument
					if (nameOfClassFound != null) {
						if (nameOfClassFound.equals(mySuperclassName)) {
							return eachMClass.getUnderlyingObject();
						}
					}
				}
			}
		}
		catch (JavaModelException e) {
			e.printStackTrace();
		}
		return null;
	}
}
