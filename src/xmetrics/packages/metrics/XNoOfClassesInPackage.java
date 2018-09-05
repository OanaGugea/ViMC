package xmetrics.packages.metrics;

import com.salexandru.xcore.utils.interfaces.IPropertyComputer;
import com.salexandru.xcore.utils.metaAnnotation.PropertyComputer;

import vimc.metamodel.entity.MPackage;

/**
 * 
 * @author Tamara Avramovic
 *
 */
@PropertyComputer
public class XNoOfClassesInPackage implements IPropertyComputer<Double, MPackage>{
	
	@Override
	public Double compute(MPackage mPackage){
		return (double) mPackage.classesGroup().getElements().size();
	}
}