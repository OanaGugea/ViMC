package xmetrics.methods.metrics;

import com.salexandru.xcore.utils.interfaces.IPropertyComputer;
import com.salexandru.xcore.utils.metaAnnotation.PropertyComputer;

import vimc.metamodel.entity.MMethod;

@PropertyComputer
public class ToString implements IPropertyComputer<String, MMethod>{

	@Override
	public String compute(MMethod entity) {
		return entity.getUnderlyingObject().getElementName();
	}

}
