package xmetrics.classes.metrics;

import org.eclipse.jdt.core.JavaModelException;

import com.salexandru.xcore.utils.interfaces.IPropertyComputer;
import com.salexandru.xcore.utils.metaAnnotation.PropertyComputer;

import vimc.metamodel.entity.MClass;

@PropertyComputer
public class XNoOfInterfacesImplemented implements IPropertyComputer <Double, MClass>{

	@Override
	public Double compute(MClass entity) {
		try {
			return (double) entity.getUnderlyingObject().getSuperInterfaceNames().length;
		} catch (JavaModelException e) {
			e.printStackTrace();
		}
		return null;
	}

}