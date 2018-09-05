package xmetrics.classes.metrics;

import com.salexandru.xcore.utils.interfaces.IPropertyComputer;
import com.salexandru.xcore.utils.metaAnnotation.PropertyComputer;

import vimc.metamodel.entity.MClass;

/**
 * 
 * @author Tamara Avramovic
 *
 */
@PropertyComputer
public class XNoOfConstructors implements IPropertyComputer <Double, MClass>{
	
	/**
	 * @return Integer: value representing the number of constructors in a MClass
	 */
	@Override
	public Double compute(MClass entity) {
		return (double) entity.constructorsGroup().getElements().size();
	}

}

