package xmetrics.packages.groups;

import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaModelException;

import com.salexandru.xcore.utils.interfaces.Group;
import com.salexandru.xcore.utils.interfaces.IRelationBuilder;
import com.salexandru.xcore.utils.metaAnnotation.RelationBuilder;

import vimc.metamodel.entity.MClass;
import vimc.metamodel.entity.MPackage;
import vimc.metamodel.factory.Factory;

@RelationBuilder
public class ClassesGroup implements IRelationBuilder<MClass, MPackage>{

	@Override
	public Group<MClass> buildGroup(MPackage mPackage) {
		Group<MClass> classesGroup = new Group<>();
		try {
			ICompilationUnit[] compilationUnits = mPackage.getUnderlyingObject().getCompilationUnits();
			for(ICompilationUnit comp : compilationUnits) {
				IType[] typeDeclared = comp.getAllTypes();
				for(IType type : typeDeclared) {
					if(type.isClass()) {
						classesGroup.add(Factory.getInstance().createMClass(type));
					}
				}
			}
		} catch (JavaModelException e) {
			e.printStackTrace();
		}
		return classesGroup;
	}

}
 