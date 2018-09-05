package xmetrics.classes.metrics;

import org.eclipse.jdt.core.Flags;
import org.eclipse.jdt.core.IField;
import org.eclipse.jdt.core.JavaModelException;

import com.salexandru.xcore.utils.interfaces.IPropertyComputer;
import com.salexandru.xcore.utils.metaAnnotation.PropertyComputer;

import vimc.metamodel.entity.MClass;

/**
 * 
 * @author Tamara Avramovic
 * Computes the total number of public attributes from the current class.
 */
@PropertyComputer
public class XNoOfPublicAttributes implements IPropertyComputer<Double, MClass>{
	
	/**
	 * @param mClass: the class for which the number of public attributes is computed
	 * @return the number of public attributes of a class, declared in the class
	 */
	@Override
	public Double compute(MClass mClass) {
		int noOfPublicFields = 0;
		try {
			IField[] allFields = mClass.getUnderlyingObject().getFields();
		
			for(IField aField : allFields){
				if(Flags.isPublic(aField.getFlags())){
					noOfPublicFields++;
				}
			}
		
		} catch (JavaModelException e) {
			
			e.printStackTrace();
		}
		return (double) noOfPublicFields;
	}

}