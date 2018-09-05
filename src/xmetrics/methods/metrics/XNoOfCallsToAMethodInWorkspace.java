package xmetrics.methods.metrics;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.jdt.core.IMethod;
import org.eclipse.jdt.core.search.IJavaSearchConstants;
import org.eclipse.jdt.core.search.IJavaSearchScope;
import org.eclipse.jdt.core.search.SearchEngine;
import org.eclipse.jdt.core.search.SearchMatch;
import org.eclipse.jdt.core.search.SearchParticipant;
import org.eclipse.jdt.core.search.SearchPattern;
import org.eclipse.jdt.core.search.SearchRequestor;

import com.salexandru.xcore.utils.interfaces.IPropertyComputer;
import com.salexandru.xcore.utils.metaAnnotation.PropertyComputer;

import vimc.metamodel.entity.MMethod;

/**
 * @author Oana Gugea
 *
 */
@PropertyComputer
public class XNoOfCallsToAMethodInWorkspace implements IPropertyComputer<Double, MMethod> {

	private static int noOfMethodCalls = 0;
	
	public static void setNoOfMethodCalls() {
		noOfMethodCalls ++;
	}
	
	@Override
	public Double compute(MMethod mMethod) {
		noOfMethodCalls = 0;
		IMethod iMethod = mMethod.getUnderlyingObject();
		IJavaSearchScope scope = SearchEngine.createWorkspaceScope();
		SearchPattern searchPattern = SearchPattern.createPattern(iMethod, IJavaSearchConstants.REFERENCES);
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
		return (double) noOfMethodCalls;
	}

}
