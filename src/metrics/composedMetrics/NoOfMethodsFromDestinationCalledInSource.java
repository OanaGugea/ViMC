package metrics.composedMetrics;

import java.util.ArrayList;
import java.util.Collections;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IMethod;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.search.IJavaSearchConstants;
import org.eclipse.jdt.core.search.IJavaSearchScope;
import org.eclipse.jdt.core.search.SearchEngine;
import org.eclipse.jdt.core.search.SearchMatch;
import org.eclipse.jdt.core.search.SearchParticipant;
import org.eclipse.jdt.core.search.SearchPattern;
import org.eclipse.jdt.core.search.SearchRequestor;

import vimc.metamodel.entity.MClass;
import metrics.vimcInterface.MetricInterface;
import xmetrics.methods.metrics.XNoOfCallsToAMethodInWorkspace;

public class NoOfMethodsFromDestinationCalledInSource extends MetricInterface {

	private static int noOfMethodCalls = 0;

	public static void setNoOfMethodCalls() {
		noOfMethodCalls++;
	}

	@Override
	public double calculateMetric(ArrayList<Object> source, ArrayList<Object> destination) {
		// source: list of classes
		// destination: list of classes
		// number of methods from destination classes called in source classes

		MClass destinationClass = null;
		MClass sourceClass = null;

		try {
			// for each class in the destination list
			for (int i = 0; i < destination.size(); i++) {
				destinationClass = (MClass) destination.get(i);
				// get all declared methods
				IMethod[] declaredMethods = destinationClass.getUnderlyingObject().getMethods();
				// for each declared method, search for calls in all classes in source list
				for (IMethod iMethod : declaredMethods) {
					for (int j = 0; j < source.size(); j++) {
						sourceClass = (MClass) source.get(i);
						// create a search pattern for the class from source to look for method calls
						IJavaElement[] elements = (IJavaElement[]) Collections.singletonList(sourceClass).toArray();
						IJavaSearchScope scope = SearchEngine.createJavaSearchScope(elements, true);
						SearchPattern searchPattern = SearchPattern.createPattern(iMethod,
								IJavaSearchConstants.REFERENCES);
						SearchRequestor requestor = new SearchRequestor() {
							@Override
							public void acceptSearchMatch(SearchMatch match) {
								System.out.println(match.getElement());
								XNoOfCallsToAMethodInWorkspace.setNoOfMethodCalls();
							}
						};
						SearchEngine searchEngine = new SearchEngine();
						searchEngine.search(searchPattern,
								new SearchParticipant[] { SearchEngine.getDefaultSearchParticipant() }, scope,
								requestor, new NullProgressMonitor());
					}
				}
			}

		} catch (JavaModelException e) {
			e.printStackTrace();
		} catch (CoreException e) {
			e.printStackTrace();
		}
		return noOfMethodCalls;
	}

}
