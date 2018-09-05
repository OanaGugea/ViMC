package xmetrics.classes.metrics;

import java.util.Collections;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.search.IJavaSearchConstants;
import org.eclipse.jdt.core.search.IJavaSearchScope;
import org.eclipse.jdt.core.search.SearchEngine;
import org.eclipse.jdt.core.search.SearchMatch;
import org.eclipse.jdt.core.search.SearchParticipant;
import org.eclipse.jdt.core.search.SearchPattern;
import org.eclipse.jdt.core.search.SearchRequestor;

import com.salexandru.xcore.utils.interfaces.IPropertyComputer;
import com.salexandru.xcore.utils.metaAnnotation.PropertyComputer;

import vimc.metamodel.entity.MClass;
import xmetrics.methods.metrics.XNoOfCallsToAMethodInWorkspace;

/**
 * @author Oana Gugea
 *
 */

@PropertyComputer
public class XNoOfReferencesToClassInPackage implements IPropertyComputer<Double, MClass>{

	private static int noOfClassReferences = 0;

	public static void setNoOfClassReferences() {
		noOfClassReferences++;
	}
	
	@Override
	public Double compute(MClass mClass) {
		noOfClassReferences = 0;
		IType iClass = mClass.getUnderlyingObject();
		// the package in which the current class is located
		IPackageFragment iPackageFragment = mClass.getUnderlyingObject().getPackageFragment();
		IJavaElement[] elements = (IJavaElement[]) Collections.singletonList(iPackageFragment).toArray();
		IJavaSearchScope scope = SearchEngine.createJavaSearchScope(elements, true);
		SearchPattern searchPattern = SearchPattern.createPattern(iClass, IJavaSearchConstants.REFERENCES);
		SearchRequestor requestor = new SearchRequestor() {
			@Override
			public void acceptSearchMatch(SearchMatch match) {
				System.out.println(match.getElement());
				XNoOfCallsToAMethodInWorkspace.setNoOfMethodCalls();
			}
		};
		SearchEngine searchEngine = new SearchEngine();
		try {
			searchEngine.search(searchPattern, new SearchParticipant[] { SearchEngine.getDefaultSearchParticipant() },
					scope, requestor, new NullProgressMonitor());
		} catch (CoreException e) {
			e.printStackTrace();
		}
		return (double) noOfClassReferences;
	}
	
	

}
