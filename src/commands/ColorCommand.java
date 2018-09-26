package commands;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import a1.Starter;

@SuppressWarnings("serial")
public class ColorCommand extends AbstractAction{
	
	private static Starter st;
	
	public ColorCommand(Starter st) {
		ColorCommand.st = st;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		st.switchColorPub();
	}
	
}
