	package by.bsac.timetable.view.contoller;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JLabel;

/*FIXME: it isn't used*/

public class MainFormWindowListener extends WindowAdapter {
	private JFrame mainFrame;
	private JLabel progressBarLbl;

	public MainFormWindowListener(JFrame mainFrame, JLabel progressBarLbl) {
		this.mainFrame = mainFrame;
		this.progressBarLbl = progressBarLbl;
	}

	@Override
	public void windowClosing(WindowEvent e) {
		//HibernateUtil.closeSession();
		super.windowClosing(e);
	}

	// @Override
	// public void windowOpened(java.awt.event.WindowEvent evt) {
	// try {
	// //showWindow(true);
	// } catch (ServiceException e) {
	// Logger.getLogger(MainFormTest.class.getName()).log(Level.SEVERE, null,
	// e);
	// JOptionPane.showMessageDialog(mainFrame.getContentPane(), e);
	// progressBarLbl.setText(e.toString());
	// } finally {
	// mainFrame.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
	// }
	// }

}
