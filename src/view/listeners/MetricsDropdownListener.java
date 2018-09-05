package view.listeners;

import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Combo;

public class MetricsDropdownListener implements SelectionListener {
	
	String selectedMetricName = null;

	@Override
	public void widgetSelected(SelectionEvent e) {
		this.selectedMetricName = ((Combo)e.getSource()).getText();
	}

	@Override
	public void widgetDefaultSelected(SelectionEvent e) { 
	}
	
	
	public String getSelectedMetricName() {
		return selectedMetricName;
	}
	
	public void setSelectedMetricName(String name) {
		this.selectedMetricName = name;
	}

}
