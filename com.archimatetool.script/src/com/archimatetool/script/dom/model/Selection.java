/**
 * This program and the accompanying materials
 * are made available under the terms of the License
 * which accompanies this distribution in the file LICENSE.txt
 */
package com.archimatetool.script.dom.model;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.PlatformUI;

import com.archimatetool.editor.views.tree.ITreeModelView;
import com.archimatetool.script.dom.IArchiScriptDOMFactory;

/**
 * Selection dom object
 * 
 * Represents a collection of currently selected EObjects in the UI (models tree)
 * If Archi is not running an empty collection is returned
 * 
 * @author Phillip Beauvoir
 */
public class Selection implements IArchiScriptDOMFactory {

    public Object getDOMroot() {
        ExtendedCollection list = new ExtendedCollection();
        
        if(PlatformUI.isWorkbenchRunning()) {
            // For now just work with selections in the Model Tree
            ISelection selection = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getSelectionService().getSelection(ITreeModelView.ID);
            
            if(selection instanceof IStructuredSelection) {
                for(Object o : ((IStructuredSelection)selection).toArray()) {
                    
                    if(o instanceof IAdaptable) {
                        o = ((IAdaptable)o).getAdapter(EObject.class);
                    }
                    
                    if(o instanceof EObject) {
                        list.add(new EObjectProxy((EObject)o));
                    }
                }
            }
        }
        
        return list;
    }

}