package xmetrics.classes.metrics;

import com.salexandru.xcore.utils.interfaces.IPropertyComputer;
import com.salexandru.xcore.utils.metaAnnotation.PropertyComputer;

import vimc.metamodel.entity.MClass;

@PropertyComputer
public class XNoOfMethods implements IPropertyComputer <Double, MClass>{

	@Override
	public Double compute(MClass mClass) {
		return (double) mClass.methodsGroup().getElements().size();
	}

}
