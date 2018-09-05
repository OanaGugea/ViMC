package xmetrics.classes.metrics;

import java.util.List;

import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.JavaModelException;

import com.salexandru.xcore.utils.interfaces.IPropertyComputer;
import com.salexandru.xcore.utils.metaAnnotation.PropertyComputer;

import vimc.metamodel.entity.MClass;
import vimc.metamodel.entity.MPackage;
import vimc.metamodel.factory.Factory;

/**
 * @author Oana Gugea
 * 
 *         The total number of classes in the current project which extend the
 *         current class
 */
@PropertyComputer
public class XNoOfSubClassesOfClassInProject implements IPropertyComputer<Double, MClass> {

	/**
	 * @param: mClass:
	 *             the current class
	 * 
	 * @return: the total number of classes in project which extend the current class
	 */
	@Override
	public Double compute(MClass mClass) {

		int noOfClassExtensions = 0;
		String superClassName = mClass.getUnderlyingObject().getElementName();
		// the package in which the current class is located
		IPackageFragment iPackageFragment = mClass.getUnderlyingObject().getPackageFragment();
		// the Java Project for the current classes hierarchy
		IJavaProject iJavaProject = iPackageFragment.getJavaProject();
		try {
			IPackageFragment[] allPackages = iJavaProject.getPackageFragments();
			// for all packages in the current project
			for (IPackageFragment p : allPackages) {
				MPackage mPackage = Factory.getInstance().createMPackage(p);
				List<MClass> mClassesInPackage = mPackage.classesGroup().getElements();
				// for all classes in each package
				for (MClass eachMClass : mClassesInPackage) {
					String nameOfClass;
					nameOfClass = eachMClass.getUnderlyingObject().getSuperclassName();
					// count the number of classes which have as super class the class given as argument
					if (nameOfClass != null) {
						if (nameOfClass.equals(superClassName)) {
							noOfClassExtensions++;
						}
					}
				}
			}
		} catch (JavaModelException e) {
			e.printStackTrace();
		}

		return (double) noOfClassExtensions;
	}
}
