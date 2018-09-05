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
 * Computes the total number of private attributes from the current class.
 */
@PropertyComputer
public class XNoOfPrivateAttributes implements IPropertyComputer<Double, MClass>{
	
	/**
	 * @param mClass: the class for which the number of private attributes is computed
	 * @return the number of private attributes of a class, declared in the class
	 */
	@Override
	public Double compute(MClass mClass) {
		int noOfPrivateFields = 0;
		try {
			IField[] allFields = mClass.getUnderlyingObject().getFields();
		
			for(IField aField : allFields){
				if(Flags.isPrivate(aField.getFlags())){
					noOfPrivateFields++;
				}
			}
		
		} catch (JavaModelException e) {
			
			e.printStackTrace();
		}
		return (double) noOfPrivateFields;
	}
	
}