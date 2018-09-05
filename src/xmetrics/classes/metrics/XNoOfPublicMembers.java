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
public class XNoOfPublicMembers implements IPropertyComputer<Double, MClass>{

	@Override
	public Double compute(MClass entity) {
		return entity.xNoOfPublicAttributes() + entity.xNoOfPublicMethods();
	}

}