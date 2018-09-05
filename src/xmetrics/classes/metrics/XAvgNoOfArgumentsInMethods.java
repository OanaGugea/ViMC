package xmetrics.classes.metrics;

import com.salexandru.xcore.utils.interfaces.Group;
import com.salexandru.xcore.utils.interfaces.IPropertyComputer;
import com.salexandru.xcore.utils.metaAnnotation.PropertyComputer;

import vimc.metamodel.entity.MClass;
import vimc.metamodel.entity.MMethod;

@PropertyComputer
public class XAvgNoOfArgumentsInMethods implements IPropertyComputer<Double, MClass>{

	/**
	 * @return Double: value representing the average number of arguments in
	 * the methods of the entity
	 */
	@Override
	public Double compute(MClass entity) {
		Group<MMethod> allMet = entity.methodsGroup();
		double all = 0;
		for(MMethod aMet: allMet.getElements()) {
			all += aMet.xNoOfArguments();
		}
		if(allMet.getElements().size() == 0) {
			return Double.NaN;
		}
		return all/allMet.getElements().size();
	}

}