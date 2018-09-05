package view.listeners;

import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Combo;

public class OperatorsDropdownListener implements SelectionListener {
	
	private String selectedOperator = null;
	
	@Override
	public void widgetSelected(SelectionEvent e) {
		this.selectedOperator = ((Combo) (e.getSource())).getText();
	}

	@Override
	public void widgetDefaultSelected(SelectionEvent e) {
	}
	
	public String getSelectedOperator() {
		return this.selectedOperator;
	}
	
	public void setSelectedOperator(String operator) {
		this.selectedOperator = operator;
	}
}
