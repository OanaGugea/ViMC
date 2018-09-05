package xmetrics.interfaces.metrics;

import org.eclipse.jdt.core.JavaModelException;

import com.salexandru.xcore.utils.interfaces.IPropertyComputer;
import com.salexandru.xcore.utils.metaAnnotation.PropertyComputer;

import vimc.metamodel.entity.MInterface;

/**
 * @author Oana Gugea
 */
@PropertyComputer
public class XNoOfExtendingInterfaces implements IPropertyComputer<Double, MInterface> {

	/**
	 * @param mInterface:
	 *            the interface for which the metric is calculated
	 * @return: the number of interfaces extended by the current interface
	 */
	@Override
	public Double compute(MInterface mInterface) {

		try {
			return (double) mInterface.getUnderlyingObject().getSuperInterfaceNames().length;
		} catch (JavaModelException e) {
			e.printStackTrace();
		}
		return (double) 0;
	}

}
