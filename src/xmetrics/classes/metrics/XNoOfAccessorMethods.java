package xmetrics.classes.metrics;

import java.util.List;

import org.eclipse.jdt.core.IField;
import org.eclipse.jdt.core.IMethod;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.Signature;

import com.salexandru.xcore.utils.interfaces.IPropertyComputer;
import com.salexandru.xcore.utils.metaAnnotation.PropertyComputer;

import vimc.metamodel.entity.MClass;
import vimc.metamodel.entity.MMethod;

/**
 * @author Oana Gugea
 */
@PropertyComputer
public class XNoOfAccessorMethods implements IPropertyComputer<Double, MClass> {

	/**
	 * @param method:
	 *            check if it's name starts with get
	 * @return: true or false, depending if the method given as a parameter is
	 *          accessor method or not
	 * @throws JavaModelException
	 */
	private boolean isAccessor(IMethod method) throws JavaModelException {
		if (isAccessMethod("get", method)) { // if name starts with get is accessor method
			return method.getNumberOfParameters() == 0 && !Signature.SIG_VOID.equals(method.getReturnType());
		}
		return false;
	}

	/**
	 * @param method:
	 *            check if it's name starts with set
	 * @return: true or false, depending if the method given as a parameter is
	 *          mutator method or not
	 * @throws JavaModelException
	 */
	private boolean isMutator(IMethod method) throws JavaModelException {
		if (isAccessMethod("set", method)) { // if name starts with set is mutator method
			return method.getNumberOfParameters() == 1 && Signature.SIG_VOID.equals(method.getReturnType());
		}
		return false;
	}

	/**
	 * A method is accessor if it sets or returns the value of a field in that class
	 * 
	 * @param prefix:
	 *            get for accessor methods and set for mutator methods
	 * @param method:
	 *            for the return type of the method is checked for a field whith the
	 *            same type
	 * @return: true or false, depending if the method can be consider as setting or
	 *          getting the value of a field or not
	 * @throws JavaModelException
	 */
	private boolean isAccessMethod(String prefix, IMethod method) throws JavaModelException {
		IType type = method.getDeclaringType();
		for (IField field : type.getFields()) { // for ever field of IType:
			if (method.getElementName().equalsIgnoreCase(prefix + field.getElementName())) {
				return true; // is access method if name scheme fits for one field
			}
		}
		return false; // is not an access method if no field fits
	}

	/**
	 * @param mClass:
	 *            a class for which every method is tested if is accessor method
	 * @return: the total number of accessor methods found in the class given as
	 *          parameter
	 * @throws JavaModelException
	 */
	@Override
	public Double compute(MClass mClass) {
		List<MMethod> allMethods = mClass.methodsGroup().getElements();
		int sum = 0;
		for (MMethod met : allMethods) {
			IMethod m = met.getUnderlyingObject();
			try {
				if (isAccessor(m) || isMutator(m))
					sum++;
			} catch (JavaModelException e) {
				e.printStackTrace();
			}
		}
		return (double) sum;
	}
}