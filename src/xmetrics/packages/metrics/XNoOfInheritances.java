package xmetrics.packages.metrics;

import java.util.List;

import org.eclipse.jdt.core.JavaModelException;

import com.salexandru.xcore.utils.interfaces.IPropertyComputer;
import com.salexandru.xcore.utils.metaAnnotation.PropertyComputer;

import vimc.metamodel.entity.MClass;
import vimc.metamodel.entity.MInterface;
import vimc.metamodel.entity.MPackage;

/**
 * @author Tamara Avramovic
 */
@PropertyComputer
public class XNoOfInheritances implements IPropertyComputer<Double, MPackage> {

	@Override
	public Double compute(MPackage mPackage) {
		int noOfClassesInheritances = 0;
		int noOfInterfaceInheritances = 0;
		
		try {
			List<MClass> allClasses = mPackage.classesGroup().getElements();
			for (MClass aClass : allClasses){
				if(aClass.getUnderlyingObject().getSuperclassName() != null){
					noOfClassesInheritances++;
				}	
			}
			
			List<MInterface> allInterfaces = mPackage.interfacesGroup().getElements();
			for(MInterface aInterface : allInterfaces){
				String[] superInterfaces = aInterface.getUnderlyingObject().getSuperInterfaceNames();
				if(superInterfaces != null){
					noOfInterfaceInheritances = noOfInterfaceInheritances + superInterfaces.length;
				}
			}
		} catch (JavaModelException e) {;
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return (double) (noOfClassesInheritances + noOfInterfaceInheritances);
	}

}