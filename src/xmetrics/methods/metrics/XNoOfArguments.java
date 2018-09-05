package xmetrics.methods.metrics;

import org.eclipse.jdt.core.IMethod;

import com.salexandru.xcore.utils.interfaces.IPropertyComputer;
import com.salexandru.xcore.utils.metaAnnotation.PropertyComputer;

import vimc.metamodel.entity.MMethod;

@PropertyComputer
public class XNoOfArguments implements IPropertyComputer<Double, MMethod>{

	@Override
	public Double compute(MMethod entity) {
		IMethod m = entity.getUnderlyingObject();
		return (double) m.getNumberOfParameters();
	}

}
