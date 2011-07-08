package org.globaltester.testspecification.ui.navigator;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.swt.graphics.Image;
import org.globaltester.testspecification.GtTestSpecNature;

public class LabelProvider implements ILabelProvider {

	@Override
	public void addListener(ILabelProviderListener listener) {
		// no listeners handled yet, as no events are communicated to them
	}

	@Override
	public void dispose() {
		// nothing to be done here
	}

	@Override
	public boolean isLabelProperty(Object element, String property) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void removeListener(ILabelProviderListener listener) {
		// no listeners handled yet, as no events are communicated to them
	}

	@Override
	public Image getImage(Object element) {
		// no images provided yet
		return null;
	}

	@Override
	public String getText(Object element) {
		// append
		if (IProject.class.isInstance(element)) {
			IProject proj = (IProject) element;
			try {
				if (proj.hasNature(GtTestSpecNature.NATURE_ID)) {
					return proj.getName() + " (GT TestSpec)";
				}
			} catch (CoreException e) {
				// ignore to label this project
			}
		}
		return null;
	}

}
