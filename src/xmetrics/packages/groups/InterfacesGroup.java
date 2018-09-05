package xmetrics.packages.groups;

import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaModelException;

import com.salexandru.xcore.utils.interfaces.Group;
import com.salexandru.xcore.utils.interfaces.IRelationBuilder;
import com.salexandru.xcore.utils.metaAnnotation.RelationBuilder;

import vimc.metamodel.entity.MInterface;
import vimc.metamodel.entity.MPackage;
import vimc.metamodel.factory.Factory;

@RelationBuilder
public class InterfacesGroup implements IRelationBuilder <MInterface, MPackage>{

	@Override
	public Group<MInterface> buildGroup(MPackage entity) {
		Group<MInterface> interfaces = new Group<>();
		try {
			ICompilationUnit[] compilationUnits =  entity.getUnderlyingObject().getCompilationUnits();
			for(ICompilationUnit comp : compilationUnits) {
				IType[] typeDeclared = comp.getAllTypes();
				for(IType type : typeDeclared) {
					if(type.isInterface()) {
						interfaces.add(Factory.getInstance().createMInterface(type));
					}
				}
			}
		} catch (JavaModelException e) {
			e.printStackTrace();
		}
		return interfaces;
	}

}

