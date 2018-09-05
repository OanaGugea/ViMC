package view.plugin;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import view.constructors.ViewConstructor;
import view.model.ViMCView;

public class StartViMCCommandHandler extends AbstractHandler {
	
	private static final String WINDOW_TITLE = "Visual Metrics Composer";

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		System.out.println("[ViMC] Starting user interface...");
		
		Display display = Display.getCurrent();
		Shell shell = new Shell(display);
		
		//set the text displayed as title to the UI
		shell.setText(WINDOW_TITLE);
		
		//set the layout of the shell as a grid layout
		GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 2;
		shell.setLayout(gridLayout);
		
		ViMCView vimcView = new ViMCView(shell);
		ViewConstructor viewConstructor = new ViewConstructor();
		
		viewConstructor.constructViMCView(vimcView);
		
		shell.pack();
		shell.open();
		
		viewConstructor.setListeners(vimcView);
		
        while (!shell.isDisposed()) {
            if (!display.readAndDispatch()) {
                display.sleep();
            }
        }
		
        System.out.println("[ViMC] Closing user interface...");
		return null;
	}

}
