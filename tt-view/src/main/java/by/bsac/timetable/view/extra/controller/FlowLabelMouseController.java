package by.bsac.timetable.view.extra.controller;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JLabel;

import by.bsac.timetable.view.extra.FlowChooseDialog;
import by.bsac.timetable.view.extra.GroupEditForm;

public class FlowLabelMouseController extends MouseAdapter {
	private GroupEditForm frame;
	private JLabel flowLabel;

	public FlowLabelMouseController(GroupEditForm frame, JLabel flowLabel) {
		this.flowLabel = flowLabel;
		this.frame = frame;
	}

	@Override
	public void mouseEntered(MouseEvent e) {

		flowLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		if (frame.getFlow() == null) {
			flowLabel.setForeground(new Color(0, 110, 0));
			flowLabel.setText("<html><u>задать поток</u></html>");
		} else {
			flowLabel.setForeground(new Color(110, 0, 0));
			flowLabel.setText("<html><u>изменить поток</u></html>");
		}
	}

	@Override
	public void mouseExited(MouseEvent e) {
		flowLabel.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		if (frame.getFlow() == null) {
			flowLabel.setForeground(new Color(0, 0, 255));
			flowLabel.setText("Не задан");
		} else {
			flowLabel.setForeground(new Color(0, 0, 0));
			flowLabel.setText(frame.getFlow().getName());
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {

		FlowChooseDialog dialog = new FlowChooseDialog(frame);
		dialog.setLocationRelativeTo(frame);
		dialog.setVisible(true);
		if (frame.isNeedUpdate()) {

			if (frame.getFlow() == null) {
				flowLabel.setText("Не задан");
			} else {
				flowLabel.setText(frame.getFlow().getName());
			}
		}
	}
}