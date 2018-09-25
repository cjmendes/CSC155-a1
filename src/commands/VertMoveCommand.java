package commands;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import a1.Starter;

@SuppressWarnings("serial")
public class VertMoveCommand extends AbstractAction{
	
	private static Starter st;
	
	public VertMoveCommand(Starter st) {
		VertMoveCommand.st = st;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		st.setVerticalCheck();
	}
	
}
