package xmetrics.classes.metrics;

import java.util.List;

import org.eclipse.jdt.core.Flags;
import org.eclipse.jdt.core.IMethod;
import org.eclipse.jdt.core.JavaModelException;

import com.salexandru.xcore.utils.interfaces.IPropertyComputer;
import com.salexandru.xcore.utils.metaAnnotation.PropertyComputer;

import vimc.metamodel.entity.MClass;
import vimc.metamodel.entity.MMethod;

/**
 * @author Oana Gugea
 */
@PropertyComputer
public class XNoOfPrivateMethods implements IPropertyComputer<Double, MClass> {

	/**
	 * @param mClass:
	 *            for all methods in the given class checks if they are private or
	 *            not
	 * @return: the total number of private methods in the class given as a
	 *          parameter
	 * @throws JavaModelException
	 */
	@Override
	public Double compute(MClass mClass) {
		List<MMethod> allMethods = mClass.methodsGroup().getElements();
		int sum = 0;
		for (MMethod met : allMethods) {
			IMethod m = met.getUnderlyingObject();
			try { // check the "isPrivate" flag for finding the level in which this method is visible
				if (Flags.isPrivate(m.getFlags()))
					sum++;
			} catch (JavaModelException e) {
				e.printStackTrace();
			}
		}
		return (double) sum;
	}

}
