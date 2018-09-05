package xmetrics.packages.metrics;

import com.salexandru.xcore.utils.interfaces.IPropertyComputer;
import com.salexandru.xcore.utils.metaAnnotation.PropertyComputer;

import vimc.metamodel.entity.MPackage;

@PropertyComputer
public class ToString implements IPropertyComputer<String, MPackage>{
	
	@Override
	public String compute(MPackage mPackage){
		return mPackage.getUnderlyingObject().getElementName();
	}
}
