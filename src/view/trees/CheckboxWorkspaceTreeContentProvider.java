package view.trees;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.IPackageFragmentRoot;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jface.viewers.ITreeContentProvider;

import vimc.metamodel.entity.MClass;
import vimc.metamodel.entity.MInterface;
import vimc.metamodel.entity.MMethod;
import vimc.metamodel.entity.MPackage;
import vimc.metamodel.factory.Factory;

public class CheckboxWorkspaceTreeContentProvider implements ITreeContentProvider {
	
	private static String NAME_OF_THIS_PLUGIN = "ViMC";

	@Override
	public Object[] getElements(Object inputElement) {
		IWorkspace workspace = ResourcesPlugin.getWorkspace();
		IWorkspaceRoot thisWorkspacesRoot = workspace.getRoot();
		
		IProject[] listOfProjects = thisWorkspacesRoot.getProjects();
		ArrayList<IProject> arrayListOfProjects =
				new ArrayList<IProject>(Arrays.asList(listOfProjects));
		
		//remove the plug-in project and the RemoteSystemsTempFiles from the list
		arrayListOfProjects = removePluginAndRemoteSystemTempFilesProjects(arrayListOfProjects);
		
		//return the array of IJavaProjects
		return createIJavaProjectsArrayFrom(arrayListOfProjects);
	}
	
	@Override
	public Object[] getChildren(Object parentElement) {
		try {
			if(parentElement instanceof IJavaProject) {		
				return getChildrenOfIJavaProject((IJavaProject) parentElement);
			}
			else if(parentElement instanceof MPackage) {
				return getChildrenOfMPackage((MPackage)parentElement);
			}
			else if(parentElement instanceof MClass) {
				return getChildrenOfMClass((MClass)parentElement);
			}
		} catch (JavaModelException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Object getParent(Object element) {
		if(element instanceof MMethod) {
			MMethod mMethod = (MMethod) element;
			IType parentIType = mMethod.getUnderlyingObject().getDeclaringType();
			return Factory.getInstance().createMClass(parentIType);
		}
		else if(element instanceof MClass) {
			MClass mClass = (MClass) element;
			IPackageFragment parentIPackageFragment = 
					mClass.getUnderlyingObject().getPackageFragment();
			return Factory.getInstance().createMPackage(parentIPackageFragment);
		}
		else if(element instanceof MPackage) {
			MPackage mPackage = (MPackage) element;
			return mPackage.getUnderlyingObject().getJavaProject();
		}
		else if(element instanceof IJavaProject) {
			IProject iProject = ((IJavaProject) element).getProject();
			return iProject.getWorkspace();
		}
		return null;
	}

	@Override
	public boolean hasChildren(Object element) {
		Object[] children = getChildren(element);
		return children == null ? false : children.length > 0;
	}
	
	@SuppressWarnings("unchecked")
	private ArrayList<IProject> removePluginAndRemoteSystemTempFilesProjects(ArrayList<IProject> arrayListOfProjects) {
		ArrayList<IProject> tempList = new ArrayList<IProject>();
		tempList = (ArrayList<IProject>) arrayListOfProjects.clone();
		for(IProject project : arrayListOfProjects) {
			if(project.getName().equals(NAME_OF_THIS_PLUGIN)) {
				tempList.remove(project);
			}
			if(project.getName().equals("RemoteSystemsTempFiles")) {
				tempList.remove(project);
			}
		}
		arrayListOfProjects = tempList;
		return arrayListOfProjects;
	}
	
	private IJavaProject[] createIJavaProjectsArrayFrom(ArrayList<IProject> arrayListOfProjects) {
		ArrayList<IJavaProject> arrayListOfIJavaProjects =
				new ArrayList<IJavaProject>();
		
		for(IProject iProject : arrayListOfProjects) {
			if (!iProject.isOpen())
				try {
					iProject.open(null);
				} catch (CoreException e) {
					e.printStackTrace();
				}
			IJavaProject iJavaProject = JavaCore.create(iProject);
			arrayListOfIJavaProjects.add(iJavaProject);
		}
		
		IJavaProject[] finalArrayOfProjects = new IJavaProject[arrayListOfIJavaProjects.size()];
		finalArrayOfProjects = arrayListOfIJavaProjects.toArray(finalArrayOfProjects);
		
		return finalArrayOfProjects;
	}
	
	private MPackage[] getChildrenOfIJavaProject(IJavaProject iJavaProject) throws JavaModelException {
		
		IPackageFragment[] iPackageFragmentsArray = iJavaProject.getPackageFragments();
		ArrayList<MPackage> mPackagesArrayList = new ArrayList<MPackage>();
		
		for(IPackageFragment iPackageFragment : iPackageFragmentsArray) {
			//include only packages in the src folder (exclude imported dependencies)
			if(iPackageFragment.getKind() == IPackageFragmentRoot.K_SOURCE) {
				mPackagesArrayList.add(
						Factory.getInstance().createMPackage(iPackageFragment));
			}
		}
		
		MPackage[] mPackagesArray = new MPackage[mPackagesArrayList.size()];
		mPackagesArray = mPackagesArrayList.toArray(mPackagesArray);
		
		return mPackagesArray;
	}
	
	private Object[] getChildrenOfMPackage(MPackage mPackage) {
		List<MClass> mClassesList = mPackage.classesGroup().getElements();
		List<MInterface> mInterfacesList = mPackage.interfacesGroup().getElements();
		
		Object[] concatenatedArray = 
				new Object[mClassesList.size() + mInterfacesList.size()];
		int concatenatedArrayIndex = 0;
		
		for(MClass mclass : mClassesList) {
			concatenatedArray[concatenatedArrayIndex] = mclass;
			concatenatedArrayIndex++;
		}
		for(MInterface minterface : mInterfacesList) {
			concatenatedArray[concatenatedArrayIndex] = minterface;
			concatenatedArrayIndex++;
		}
		
		return concatenatedArray;
	}
	
	private MMethod[] getChildrenOfMClass(MClass mClass) {
		List<MMethod> mMethodsList = mClass.methodsGroup().getElements();
		MMethod[] mMethodsArray = new MMethod[mMethodsList.size()];
		mMethodsArray = mMethodsList.toArray(mMethodsArray);
		return mMethodsArray;
	}
}
