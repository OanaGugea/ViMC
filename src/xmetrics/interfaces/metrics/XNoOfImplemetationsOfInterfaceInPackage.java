package xmetrics.interfaces.metrics;

import java.util.List;

import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.JavaModelException;

import com.salexandru.xcore.utils.interfaces.IPropertyComputer;
import com.salexandru.xcore.utils.metaAnnotation.PropertyComputer;

import vimc.metamodel.entity.MInterface;
import vimc.metamodel.entity.MPackage;
import vimc.metamodel.factory.Factory;

/**
 * @author Oana Gugea
 */
@PropertyComputer
public class XNoOfImplemetationsOfInterfaceInPackage implements IPropertyComputer<Double, MInterface> {

	/**
	 * @param mInterface: the interface for which the metric is calculated
	 * @return: the number of interfaces in package which extend the current interface
	 */
	@Override
	public Double compute(MInterface mInterface) {

		int noOfInterfaceExtensions = 0;
		String thisInterfaceName = mInterface.getUnderlyingObject().getElementName();
		// the package which contains the current interface
		IPackageFragment iPackageFragment = mInterface.getUnderlyingObject().getPackageFragment();
		MPackage mPackage = Factory.getInstance().createMPackage(iPackageFragment);
		List<MInterface> mInterfacesInPackage = mPackage.interfacesGroup().getElements();
		// for each interface in the package
		for (MInterface eachMInterface : mInterfacesInPackage) {
			String[] nameOfInterfaces;
			try {
				// count the number of interfaces which have as super interface the interface given as argument
				nameOfInterfaces = eachMInterface.getUnderlyingObject().getSuperInterfaceNames();
				for (String nameOfSuperInterface : nameOfInterfaces) {
					if (nameOfSuperInterface != null) {
						if (nameOfSuperInterface.equals(thisInterfaceName)) {
							noOfInterfaceExtensions++;
						}
					}
				}
			} catch (JavaModelException e) {
				e.printStackTrace();
			}
		}

		return (double) noOfInterfaceExtensions;
	}

}
