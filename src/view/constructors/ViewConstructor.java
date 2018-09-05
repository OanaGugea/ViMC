package view.constructors;

import view.model.ViMCView;

public class ViewConstructor {
	
	public void constructViMCView(ViMCView vimcView) {
		
		//Row 1: "Compose a new metric" label
		vimcView.setComposeANEwMetricLabel();
		
		//Row 2: "Choose metric" label and dropdown list
		vimcView.setChooseMetricLabel();
		vimcView.setMetricsDropdown();
						
		//Row 3: "Set multiplier" label and text input
		vimcView.setMultiplierLabel();
		vimcView.setMultiplierInputText();
		
		//Row 4: "Choose operator" label and dropdown
		vimcView.setChooseOperatorLabel();
		vimcView.setOperatorsDropdown();
		
		//Row 5: "Introduce the new metric's name: " label and input text
		vimcView.setNewMetricNameLabel();
		vimcView.setNewMetricNameInputText();
		
		//Row 6: "Update" and "Save metric" buttons
		vimcView.setUpdateButton();
		vimcView.setSaveMetricButton();
				
		//Row 7: line separator before metric composition
		vimcView.setUpperLineSeparator();

		//Row 8: "The metric is composed here..." label delimited by 2 separators
		vimcView.setCompositionLabel();
				
		//Row 9&10: line separators after metric composition
		vimcView.setLowerLineSeparator1();
		vimcView.setLowerLineSeparator2();

		//Row 11: "Apply metric to:" checkbox
		vimcView.setApplyMetricToCheckbox();
		
		//Row 12: Source Checkbox and Destination Checkbox
		vimcView.setSourceCheckbox();
		vimcView.setDestinationCheckbox();
		
		//Row 13: the 2 workspace trees
		vimcView.setSourceWorkspaceTree();
		vimcView.setDestinationWorkspaceTree();
		
		//Row 14: "Compute result" button and "...Result..." label
		vimcView.setComputeResultButton();
		vimcView.setResultLabel();		
	}

	public void setListeners(ViMCView vimcView) {
		vimcView.setMetricsDropdownListener();
		vimcView.setMultiplierInputTextListener();
		vimcView.setOperatorsDropdownListener();
		vimcView.setUpdateButtonListener();
		vimcView.setNewMetricsNameInputListener();
		vimcView.setSaveMetricButtonListener();
		vimcView.setComputeResultButtonListener();
		vimcView.setApplyMetricToCheckboxListener();
	}
}
