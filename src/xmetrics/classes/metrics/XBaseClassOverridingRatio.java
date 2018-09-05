package xmetrics.classes.metrics;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jdt.core.IMethod;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaModelException;

import com.salexandru.xcore.utils.interfaces.IPropertyComputer;
import com.salexandru.xcore.utils.metaAnnotation.PropertyComputer;

import vimc.metamodel.entity.MClass;
import vimc.metamodel.entity.MMethod;
import vimc.metamodel.factory.Factory;
import xmetrics.utils.UtilityClass;

/**
 * 
 * @author Tamara Avramovic
 * Computes the number of methods of the measured class that override methods from the base
 * class, divided by the total number of methods in the class.
 */
@PropertyComputer
public class XBaseClassOverridingRatio implements IPropertyComputer<Double, MClass> {

	@Override
	public Double compute(MClass mClass) {
		
		List<MMethod> allPersonalMethods = mClass.methodsGroup().getElements();
	
		IType superClass = UtilityClass.getSuperClassForClass(
				mClass.getUnderlyingObject());
		
		System.out.println("Name of superclass obtained: " + superClass.getFullyQualifiedName());
		
		if(superClass != null){
			
			try {
				IMethod[] allMethodsOfSuperclass = superClass.getMethods();
				List<MMethod> listOfMMethodsFromSuperclass = new ArrayList<>();
				
				for(int i=0; i< allMethodsOfSuperclass.length ; i++) {
					MMethod mMethod = Factory.getInstance().createMMethod(allMethodsOfSuperclass[i]);
					listOfMMethodsFromSuperclass.add(mMethod);
				}
				
				if (listOfMMethodsFromSuperclass.size() == 0) {
					return new Double(0);
				}
				
				int noOfOverridingMethods = 0;
				
				for (MMethod aMethodInMyClass : allPersonalMethods) {
				
					//get the signature and name of the method in my class
					String signatureOfaMethodInMyClass = 
							aMethodInMyClass.getUnderlyingObject().getSignature();
					String nameOfaMethodInMyClass = 
							aMethodInMyClass.getUnderlyingObject().getElementName();
					String sourceOfMyMethod = aMethodInMyClass.getUnderlyingObject().getSource();
					
					for (MMethod aMethodInSuperClass : listOfMMethodsFromSuperclass) {
						
						//get the signature and name of the method in the superclass
						String signatureOfaMethodInSuperClass = 
								aMethodInSuperClass.getUnderlyingObject().getSignature();
						String nameOfaMethodInSuperClass = 
								aMethodInSuperClass.getUnderlyingObject().getElementName();
						
						if (nameOfaMethodInMyClass.equals(nameOfaMethodInSuperClass)
								&& signatureOfaMethodInMyClass.equals(signatureOfaMethodInSuperClass)
								&& sourceOfMyMethod != null) {
							noOfOverridingMethods ++;
						}
					}
					
					double result = (noOfOverridingMethods/(allPersonalMethods.size()));
					return result;
				}
			} catch (JavaModelException e1) {
				e1.printStackTrace();
			}
		}
		
		return new Double(0);
	}

}