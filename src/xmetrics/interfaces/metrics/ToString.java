package xmetrics.interfaces.metrics;

import com.salexandru.xcore.utils.interfaces.IPropertyComputer;
import com.salexandru.xcore.utils.metaAnnotation.PropertyComputer;

import vimc.metamodel.entity.MInterface;

@PropertyComputer
public class ToString implements IPropertyComputer<String, MInterface>{
	
	@Override
	public String compute(MInterface mInterface){
		return mInterface.getUnderlyingObject().getElementName();
	}
}
