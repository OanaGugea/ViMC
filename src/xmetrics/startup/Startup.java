package xmetrics.startup;

import org.eclipse.jdt.core.IMethod;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.ui.IStartup;

import com.salexandru.xcore.utils.interfaces.XEntity;

import vimc.metamodel.factory.Factory;
import ro.lrg.insider.view.ToolRegistration;

public class Startup implements IStartup {

	@Override
	public void earlyStartup() {
		ToolRegistration.getInstance().registerXEntityConverter(
			new ToolRegistration.XEntityConverter(){

				@Override
				public XEntity convert(Object element) {
					if(element instanceof IMethod) {
						return Factory.getInstance().createMMethod((IMethod)element);
					}
					if(element instanceof IType) {
						try {
							if(((IType) element).isInterface())
								return Factory.getInstance().createMInterface((IType)element);
							
							if(((IType) element).isClass())
								return Factory.getInstance().createMClass((IType)element);
							
						} catch (JavaModelException e) {
							e.printStackTrace();
						}	
					}
					
					if(element instanceof IPackageFragment){
						return Factory.getInstance().createMPackage((IPackageFragment) element);
					}
					return null;
				}
				
			});

	}

}
