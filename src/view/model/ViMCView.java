package view.model;

import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.jface.viewers.CheckboxTreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import view.constructors.ComposedMetricLabelConstructor;
import view.constructors.MetricsListConstructor;
import view.constructors.NewMetricClassGenerator;
import view.constructors.ResultCalculator;
import view.listeners.MetricsDropdownListener;
import view.listeners.MultiplierInputListener;
import view.listeners.NewMetricsNameInputListener;
import view.listeners.OperatorsDropdownListener;
import view.trees.CheckboxWorkspaceTreeContentProvider;

public class ViMCView {
	private static final String APPLY_METRIC_TO = "Apply metric to:";
	private static final String CHOOSE_METRIC = "Choose metric:";
	private static final String SET_MULTIPLIER = "Set multiplier:";
	private static final String SOURCE = "Source";
	private static final String DESTINATION = "Destination";
	private static final String CHOOSE_OPERATOR = "Choose operator:";
	private static final String UPDATE = "Update";
	private static final String SAVE_METRIC = "Save metric";
	private static final String THE_METRIC_IS_COMPOSED_HERE = "The metric is composed here...";
	private static final String COMPUTE_RESULT = "Compute result";
	private static final String RESULT = "...Result here...";
	private static final String COMPOSE_METRIC = "Compose a new metric:";
	private static final String NEW_METRIC_NAME = "Introduce the new metric's name:";
	
	private Shell shell;
	
	private Label chooseMetric;
	private Label chooseOperator;
	private Label composeANewMetric;
	private Label compositionLabel;
	private Label lowerLineSeparator1;
	private Label lowerLineSeparator2;
	private Label newMetricNameLabel;
	private Label resultLabel;
	private Label setMultiplier;
	private Label upperLineSeparator;

	private Button applyMetricToCheckbox;
	private Button computeResultButton;
	private Button destinationCheckbox;
	private Button saveMetricButton;
	private Button sourceCheckbox;
	private Button updateButton;

	private CheckboxTreeViewer destinationTreeViewer;
	private CheckboxTreeViewer sourceTreeViewer;

	private Combo metricsDropdown;
	private Combo operatorsDropdown;
	
	private Text multiplierInput;
	private Text newMetricNameInput;
	
	private MultiplierInputListener multiplierInputListener;
	private MetricsDropdownListener metricsDropdownListener;
	private OperatorsDropdownListener operatorsDropdownListener;
	private NewMetricsNameInputListener newMetricsNameInputListener;
	
	public ViMCView(Shell shell) {
		this.shell = shell;
	}
	
	public void setChooseMetricLabel() {
		chooseMetric = new Label(shell, SWT.NULL);
		chooseMetric.setText(CHOOSE_METRIC);
		GridData chooseMetricGridData = new GridData(GridData.FILL_HORIZONTAL);
		chooseMetricGridData.horizontalAlignment = GridData.BEGINNING;
		chooseMetric.setLayoutData(chooseMetricGridData);
	}
	
	public void setChooseOperatorLabel() {
		chooseOperator = new Label(shell, SWT.NULL);
		chooseOperator.setText(CHOOSE_OPERATOR);
		GridData chooseOperatorGridData = new GridData(GridData.FILL_HORIZONTAL);
		chooseOperatorGridData.horizontalAlignment = GridData.BEGINNING;
		chooseOperator.setLayoutData(chooseOperatorGridData);
	}
	
	public void setComposeANEwMetricLabel() {
		composeANewMetric = new Label(shell, SWT.NULL);
		composeANewMetric.setText(COMPOSE_METRIC);
		GridData composeANewMetricGridData = new GridData(GridData.FILL_HORIZONTAL);
		composeANewMetricGridData.horizontalSpan = 2;
		composeANewMetricGridData.horizontalAlignment = GridData.CENTER;
		composeANewMetric.setLayoutData(composeANewMetricGridData);
	}
	
	public void setCompositionLabel() {
		compositionLabel = new Label(shell, SWT.BORDER);
		compositionLabel.setText(THE_METRIC_IS_COMPOSED_HERE);
		compositionLabel.setBackground(shell.getDisplay().getSystemColor(SWT.COLOR_WHITE));
		GridData compositionLabelGridData = new GridData(GridData.FILL_HORIZONTAL);
		compositionLabelGridData.horizontalSpan = 2;
		compositionLabelGridData.horizontalAlignment = GridData.FILL;
		compositionLabel.setLayoutData(compositionLabelGridData);
	}
	
	public void setLowerLineSeparator1() {
		lowerLineSeparator1 = new Label(shell, SWT.HORIZONTAL | SWT.SEPARATOR);
		GridData lowerLineSeparator1GridData = new GridData(GridData.FILL_HORIZONTAL);
		lowerLineSeparator1GridData.horizontalSpan = 2;
		lowerLineSeparator1.setLayoutData(lowerLineSeparator1GridData);
	}
	
	public void setLowerLineSeparator2() {
		lowerLineSeparator2 = new Label(shell, SWT.HORIZONTAL | SWT.SEPARATOR);
		GridData lowerLineSeparator2GridData = new GridData(GridData.FILL_HORIZONTAL);
		lowerLineSeparator2GridData.horizontalSpan = 2;
		lowerLineSeparator2.setLayoutData(lowerLineSeparator2GridData);
	}
	
	public void setResultLabel() {
		resultLabel = new Label(shell, SWT.BORDER);
		resultLabel.setText(RESULT);
		resultLabel.setBackground(shell.getDisplay().getSystemColor(SWT.COLOR_WHITE));
		GridData resultLabelGridData = new GridData(GridData.FILL_HORIZONTAL);
		resultLabelGridData.horizontalAlignment = GridData.FILL;
		resultLabel.setLayoutData(resultLabelGridData);
	}
	
	public void setMultiplierLabel() {
		setMultiplier = new Label(shell, SWT.NULL);
		setMultiplier.setText(SET_MULTIPLIER);
		GridData setMultiplierGridData = new GridData(GridData.FILL_HORIZONTAL);
		setMultiplierGridData.horizontalAlignment = GridData.BEGINNING;
		setMultiplier.setLayoutData(setMultiplierGridData);
	}
	
	public void setNewMetricNameLabel() {
		newMetricNameLabel = new Label(shell, SWT.NULL);
		newMetricNameLabel.setText(NEW_METRIC_NAME);
		GridData newMetricNameLabelGridData = new GridData(GridData.FILL_HORIZONTAL);
		newMetricNameLabelGridData.horizontalAlignment = GridData.BEGINNING;
		newMetricNameLabel.setLayoutData(newMetricNameLabelGridData);
	}
	
	public void setUpperLineSeparator() {
		upperLineSeparator = new Label(shell, SWT.HORIZONTAL | SWT.SEPARATOR);
		GridData upperLineSeparatorGridData = new GridData(GridData.FILL_HORIZONTAL);
		upperLineSeparatorGridData.horizontalSpan = 2;
		upperLineSeparator.setLayoutData(upperLineSeparatorGridData);
	}
		
	public void setApplyMetricToCheckbox() {
		applyMetricToCheckbox = new Button(shell, SWT.CHECK);
		applyMetricToCheckbox.setText(APPLY_METRIC_TO);
		GridData applyMetricToCheckboxGridData = new GridData(GridData.FILL_HORIZONTAL);
		applyMetricToCheckboxGridData.horizontalSpan = 2;
		applyMetricToCheckboxGridData.horizontalAlignment = GridData.CENTER;
		applyMetricToCheckbox.setLayoutData(applyMetricToCheckboxGridData);
	}
	public void setApplyMetricToCheckboxListener() {
		applyMetricToCheckbox.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				if(applyMetricToCheckbox.getSelection()) {
					sourceCheckbox.setEnabled(true);
					sourceCheckbox.getParent().layout();
					destinationCheckbox.setEnabled(true);
					destinationCheckbox.getParent().layout();
				}
				else {
					sourceCheckbox.setEnabled(false);
					sourceCheckbox.getParent().layout();
					destinationCheckbox.setEnabled(false);
					destinationCheckbox.getParent().layout();
				}
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
			}
			
		});
	}
	
	public void setComputeResultButton() {
		computeResultButton = new Button(shell, SWT.PUSH);
		computeResultButton.setText(COMPUTE_RESULT);
		GridData computeResultButtonGridData = new GridData(GridData.FILL_HORIZONTAL);
		computeResultButtonGridData.horizontalAlignment = GridData.FILL;
		computeResultButton.setLayoutData(computeResultButtonGridData);
	}
	
	public void setComputeResultButtonListener() {
		computeResultButton.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				if(metricsDropdownListener.getSelectedMetricName() != null) {
					String selectedMetric = metricsDropdownListener.getSelectedMetricName();
					Object[] sourceElements = sourceTreeViewer.getCheckedElements();
					Object[] destinationElements = destinationTreeViewer.getCheckedElements();
					
					ResultCalculator resultCalculator = 
							new ResultCalculator(selectedMetric);
					resultCalculator.setSourceParameters(sourceElements);
					resultCalculator.setDestinationParameters(destinationElements);
					String result = resultCalculator.computeResult();
					
					//update the result label
					resultLabel.setText(result);
					resultLabel.getParent().layout();
					
					uncheckAllCheckboxesAfterCompute(sourceElements, destinationElements);
				}
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
			}
		});
	}
	
	public void setDestinationCheckbox() {
		destinationCheckbox = new Button(shell, SWT.CHECK);
		destinationCheckbox.setText(DESTINATION);
		destinationCheckbox.setEnabled(false);
		GridData destinationGridData = new GridData(GridData.FILL_HORIZONTAL);
		destinationGridData.horizontalAlignment = GridData.CENTER;
		destinationCheckbox.setLayoutData(destinationGridData);
	}
	
	public void setSaveMetricButton() {
		saveMetricButton = new Button(shell, SWT.PUSH);
		saveMetricButton.setText(SAVE_METRIC);
		GridData saveMetricButtonGridData = new GridData(GridData.FILL_HORIZONTAL);
		saveMetricButtonGridData.horizontalAlignment = GridData.FILL;
		saveMetricButton.setLayoutData(saveMetricButtonGridData);
	}
	
	public void setSaveMetricButtonListener() {
		saveMetricButton.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				System.out.println("[ViMC] Info: Saving the new metric ...");
				if(multiplierInputListener.getMultiplierValue() != null) {
					//clear the input field in the UI & memory
					multiplierInputListener.setMultiplierValue(null);
					multiplierInput.setText("");
					multiplierInput.getParent().layout();
				}
				if(metricsDropdownListener.getSelectedMetricName() != null){
					//clear the selection in the UI & memory
					metricsDropdownListener.setSelectedMetricName(null);
					metricsDropdown.setText("");
					metricsDropdown.getParent().layout();
				}
				if(operatorsDropdownListener.getSelectedOperator() != null) {
					//clear the selection in the UI & memory
					operatorsDropdownListener.setSelectedOperator(operatorsDropdown.getItem(0));
					operatorsDropdown.setText("");
					operatorsDropdown.getParent().layout();
				}
				if(newMetricsNameInputListener.getNewMetricsName() != null) {
					//use this field
					NewMetricClassGenerator newMetricClassGenerator = 
							new NewMetricClassGenerator(
									newMetricsNameInputListener.getNewMetricsName(),
									compositionLabel.getText());
					newMetricClassGenerator.generateNewMetricClass();
					//clear the input field in the UI and in the memory
					newMetricsNameInputListener.setNewMetricsName(null);
					newMetricNameInput.setText("");
					newMetricNameInput.getParent().layout();
				}
				
				//reset the composition label with initial text
				compositionLabel.setText(THE_METRIC_IS_COMPOSED_HERE);
				compositionLabel.getParent().layout();
				
				//reload the metrics dropdown to include new metric
				String[] listOfMetrics = new MetricsListConstructor().getListOfAllMetricsImplemented();
				metricsDropdown.setItems(listOfMetrics);
				metricsDropdown.getParent().layout();
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
			}
			
		});
	}
	
	public void setSourceCheckbox() {
		sourceCheckbox = new Button(shell, SWT.CHECK);
		sourceCheckbox.setText(SOURCE);
		sourceCheckbox.setEnabled(false);
		GridData sourceGridData = new GridData(GridData.FILL_HORIZONTAL);
		sourceGridData.horizontalAlignment = GridData.CENTER;
		sourceCheckbox.setLayoutData(sourceGridData);
	}
	
	public void setUpdateButton() {
		updateButton = new Button(shell, SWT.PUSH);
		updateButton.setText(UPDATE);
		GridData updateButtonGridData = new GridData(GridData.FILL_HORIZONTAL);
		updateButtonGridData.horizontalAlignment = GridData.FILL;
		updateButton.setLayoutData(updateButtonGridData);
	}
	
	public void setUpdateButtonListener() {
		ComposedMetricLabelConstructor cmlc = new ComposedMetricLabelConstructor();
		updateButton.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				
				System.out.println("[ViMC] Info: Updating the interface ...");
				
				if(multiplierInputListener.getMultiplierValue() != null){
					cmlc.addMultiplierToFormula(multiplierInputListener.getMultiplierValue());
					
					//after getting the value, clear the input field in the UI & memory
					multiplierInputListener.setMultiplierValue(null);
					multiplierInput.setText("");
					multiplierInput.getParent().layout();
				}
				if(metricsDropdownListener.getSelectedMetricName() != null){
					cmlc.addMetricNameToFormula(metricsDropdownListener.getSelectedMetricName());
					
					//after getting the value, clear the selection in the UI & memory
					metricsDropdownListener.setSelectedMetricName(null);
					metricsDropdown.setText("");
					metricsDropdown.getParent().layout();
				}
				if(operatorsDropdownListener.getSelectedOperator() != null) {
					cmlc.addOperatorToFormula(operatorsDropdownListener.getSelectedOperator());
					
					//after getting the value, clear the selection in the UI & memory
					operatorsDropdownListener.setSelectedOperator(operatorsDropdown.getItem(0));
					operatorsDropdown.setText("");
					operatorsDropdown.getParent().layout();
				}
				
				if(cmlc.getFormula() != null){
					compositionLabel.setText(cmlc.getFormula());
					compositionLabel.getParent().layout();
				}
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
			}
			
		});
	}
	
	public void setDestinationWorkspaceTree() {
		destinationTreeViewer = new CheckboxTreeViewer(shell);
		destinationTreeViewer.getTree().setLayoutData(new GridData(GridData.FILL_BOTH));
		destinationTreeViewer.setContentProvider(new CheckboxWorkspaceTreeContentProvider());
		IWorkspace workspace = ResourcesPlugin.getWorkspace();
		destinationTreeViewer.setInput(workspace);
	}
	
	public void setSourceWorkspaceTree() {
		sourceTreeViewer = new CheckboxTreeViewer(shell);
		sourceTreeViewer.getTree().setLayoutData(new GridData(GridData.FILL_BOTH));
		sourceTreeViewer.setContentProvider(new CheckboxWorkspaceTreeContentProvider());
		IWorkspace workspace = ResourcesPlugin.getWorkspace();
		sourceTreeViewer.setInput(workspace);
	}
	
	public void setMetricsDropdown() {
		metricsDropdown = new Combo(shell, SWT.READ_ONLY);
		String[] listOfMetrics = new MetricsListConstructor().getListOfAllMetricsImplemented();
		metricsDropdown.setItems(listOfMetrics);
		GridData metricsDropdownGridData = new GridData(GridData.FILL_HORIZONTAL);
		metricsDropdownGridData.horizontalAlignment = GridData.FILL;
		metricsDropdown.setLayoutData(metricsDropdownGridData);
	}
	
	public void setMetricsDropdownListener() {
		metricsDropdownListener = new MetricsDropdownListener();
		metricsDropdown.addSelectionListener(metricsDropdownListener);
	}

	public void setOperatorsDropdown() {
		operatorsDropdown = new Combo(shell, SWT.READ_ONLY);
		operatorsDropdown.setItems(new String[] {"","+", "-", "*"});
		GridData operatorsDropdownGridData = new GridData(GridData.FILL_HORIZONTAL);
		operatorsDropdownGridData.horizontalAlignment = GridData.FILL;
		operatorsDropdown.setLayoutData(operatorsDropdownGridData);
	}
	
	public void setOperatorsDropdownListener() {
		operatorsDropdownListener = new OperatorsDropdownListener();
		operatorsDropdown.addSelectionListener(operatorsDropdownListener);
	}
	
	public void setMultiplierInputText() {
		multiplierInput = new Text(shell, SWT.SINGLE | SWT.BORDER);
		GridData multiplierInputGridData = new GridData(GridData.FILL_HORIZONTAL);
		multiplierInputGridData.horizontalAlignment = GridData.FILL;
		multiplierInput.setLayoutData(multiplierInputGridData);
	}
	
	public void setMultiplierInputTextListener() {
		multiplierInputListener = new MultiplierInputListener(multiplierInput);
		multiplierInput.addListener(SWT.Modify, multiplierInputListener);
	}
	
	public Listener getMultiplierInputTextListener( ) {
		return this.multiplierInputListener;
	}
	
	public void setNewMetricNameInputText() {
		newMetricNameInput = new Text(shell, SWT.SINGLE | SWT.BORDER);
		GridData newMetricNameInputGridData = new GridData(GridData.FILL_HORIZONTAL);
		newMetricNameInputGridData.horizontalAlignment = GridData.FILL;
		newMetricNameInput.setLayoutData(newMetricNameInputGridData);
	}
	
	public void setNewMetricsNameInputListener() {
		newMetricsNameInputListener = new NewMetricsNameInputListener(newMetricNameInput);
		newMetricNameInput.addListener(SWT.Modify, newMetricsNameInputListener);
	}
	
	private void uncheckAllCheckboxesAfterCompute(Object[] sourceElements, Object[] destinationElements) {
		//uncheck the elements in the 2 trees
		for(Object eachSourceElem : sourceElements) {
			sourceTreeViewer.setChecked(eachSourceElem, false);
		}
		for(Object eachDestinationElem : destinationElements) {
			destinationTreeViewer.setChecked(eachDestinationElem, false);
		}
		
		//uncheck the rest of the checkboxes
		applyMetricToCheckbox.setSelection(false);
		applyMetricToCheckbox.getParent().layout();
		
		sourceCheckbox.setSelection(false);
		sourceCheckbox.getParent().layout();
		
		destinationCheckbox.setSelection(false);
		destinationCheckbox.getParent().layout();

	}
}
