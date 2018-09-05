package xmetrics.classes.metrics;

import com.salexandru.xcore.utils.interfaces.IPropertyComputer;
import com.salexandru.xcore.utils.metaAnnotation.PropertyComputer;

import vimc.metamodel.entity.MClass;

/**
 * 
 * @author Tamara Avramovic
 * Computes the number of protected members (attributes and methods) a class has.
 *
 */
@PropertyComputer
public class XNoOfProtectedMembers implements IPropertyComputer<Double, MClass>{
	
	/**
	 * @param mClass: the class for which the number of protected members
	 * is computed
	 * @return the sum between the number of protected attributes and 
	 * protected methods
	 */
	@Override
	public Double compute(MClass mClass) {
		return mClass.xNoOfProtectedAttributes() + mClass.xNoOfProtectedMethods();
	}

}
