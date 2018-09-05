package xmetrics.packages.metrics;

import java.util.List;

import org.eclipse.jdt.core.Flags;
import org.eclipse.jdt.core.JavaModelException;

import com.salexandru.xcore.utils.interfaces.IPropertyComputer;
import com.salexandru.xcore.utils.metaAnnotation.PropertyComputer;

import vimc.metamodel.entity.MClass;
import vimc.metamodel.entity.MPackage;

@PropertyComputer
public class XNoOfAbstractClassesInPackage implements IPropertyComputer<Double, MPackage> {
	
	
	@Override
	public Double compute(MPackage mPackage) {
		
		List<MClass> classesInPackage = mPackage.classesGroup().getElements();
		int noOfAbstractClasses = 0;
		
		for(MClass eachClass: classesInPackage) {
			try {
				if(Flags.isAbstract(eachClass.getUnderlyingObject().getFlags()))
					noOfAbstractClasses++;
			} catch (JavaModelException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return (double) noOfAbstractClasses;
	}

}
