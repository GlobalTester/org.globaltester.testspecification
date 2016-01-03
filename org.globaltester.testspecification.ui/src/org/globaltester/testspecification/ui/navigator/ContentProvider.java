package org.globaltester.testspecification.ui.navigator;

import java.util.LinkedList;
import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.ui.model.WorkbenchContentProvider;
import org.globaltester.testspecification.GtTestSpecNature;

public class ContentProvider extends WorkbenchContentProvider {

	@Override
	public Object[] getElements(Object inputElement) {
		return filterByNature(super.getElements(inputElement));
	}

	@Override
	public Object[] getChildren(Object parentElement) {
		return filterByNature(super.getChildren(parentElement));
	}

	/**
	 * Filter out all projects that don't have the GtTestSpecNature associated
	 * 
	 * @param elements
	 * @return
	 */
	private Object[] filterByNature(Object[] elements) {
		List<Object> result = new LinkedList<Object>();

		for (Object curRes : elements) {
			if (IProject.class.isInstance(curRes)) {
				try {
					if (((IProject) curRes)
							.hasNature(GtTestSpecNature.NATURE_ID)) {
						result.add(curRes);
					}
				} catch (CoreException e) {
					// ignore the project if natures can not be determined;
				}
			} else {
				result.add(curRes);
			}

		}
		return result.toArray(elements);

	}

}
