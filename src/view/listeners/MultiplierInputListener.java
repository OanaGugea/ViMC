package view.listeners;

import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Text;

public class MultiplierInputListener implements Listener {
	
	private Text multiplierInput;
	private Double multiplierValue;
	
	public MultiplierInputListener(Text input) {
		this.multiplierInput = input;
	}
	
	@Override
	public void handleEvent(Event event) {

		try {
			multiplierValue = new Double(multiplierInput.getText());
		}
		catch(Exception e)
		{
			multiplierValue = null;
			System.out.println("[ViMC] Warning: Multiplier cannot be converted to a double value!");
		}
	
	}
	
	public Double getMultiplierValue() {
		return this.multiplierValue;
	}
	
	public void setMultiplierValue(Double value) {
		this.multiplierValue = value;
	}
}
