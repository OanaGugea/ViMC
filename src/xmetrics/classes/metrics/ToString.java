package xmetrics.classes.metrics;

import com.salexandru.xcore.utils.interfaces.IPropertyComputer;
import com.salexandru.xcore.utils.metaAnnotation.PropertyComputer;

import vimc.metamodel.entity.MClass;

@PropertyComputer
public class ToString implements IPropertyComputer<String, MClass>{
	
	@Override
	public String compute(MClass mclass){
		return mclass.getUnderlyingObject().getElementName();
	}
}
