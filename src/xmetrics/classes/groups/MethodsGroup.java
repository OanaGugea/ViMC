package xmetrics.classes.groups;

import org.eclipse.jdt.core.IMethod;
import org.eclipse.jdt.core.JavaModelException;

import com.salexandru.xcore.utils.interfaces.Group;
import com.salexandru.xcore.utils.interfaces.IRelationBuilder;
import com.salexandru.xcore.utils.metaAnnotation.RelationBuilder;

import vimc.metamodel.entity.MClass;
import vimc.metamodel.entity.MMethod;
import vimc.metamodel.factory.Factory;

@RelationBuilder
public class MethodsGroup implements IRelationBuilder <MMethod, MClass>{
	
	/**
	 * @param entity: a MClass which is parsed to get all its methods
	 * @return Group<MMethod>: a group of all the MMethods in entity
	 */
	@Override
	public Group<MMethod> buildGroup(MClass entity) {
		Group<MMethod> methods = new Group<>();
		
		try {
			for(IMethod method: entity.getUnderlyingObject().getMethods()) {
				methods.add(Factory.getInstance().createMMethod(method));
			}
		} catch (JavaModelException e) {
			e.printStackTrace();
		}
		
		return methods;
	}

}