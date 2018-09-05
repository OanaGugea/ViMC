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
public class ConstructorsGroup implements IRelationBuilder <MMethod, MClass>{
	
	/**
	 * @param entity: a MClass which is parsed to get its constructors
	 * @return Group<MMethod>: a group of all the constructors in entity
	 */
	@Override
	public Group<MMethod> buildGroup(MClass entity) {
		Group<MMethod> constructors = new Group<>();
		
		try {
			for(IMethod method: entity.getUnderlyingObject().getMethods()) {
				if(method.isConstructor()) {
					constructors.add(Factory.getInstance().createMMethod(method));
				}
			}
		} catch (JavaModelException e) {
			e.printStackTrace();
		}
		
		return constructors;
	}

}
