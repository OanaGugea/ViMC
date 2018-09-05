package view.listeners;

import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Text;

public class NewMetricsNameInputListener implements Listener {
	
	private Text newMetricsNameInput;
	private String newMetricsName;
	
	public NewMetricsNameInputListener(Text input) {
		this.newMetricsNameInput = input;
	}
	
	@Override
	public void handleEvent(Event event) {
		newMetricsName = newMetricsNameInput.getText();
	}

	public String getNewMetricsName() {
		return newMetricsName;
	}

	public void setNewMetricsName(String newMetricsName) {
		this.newMetricsName = newMetricsName;
	}

}
