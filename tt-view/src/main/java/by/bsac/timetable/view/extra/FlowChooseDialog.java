package by.bsac.timetable.view.extra;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ItemEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.bsac.timetable.command.exception.CommandException;
import by.bsac.timetable.entity.Flow;
import by.bsac.timetable.view.util.FormInitializer;
import components.MyComboBox;

public class FlowChooseDialog extends JDialog {

	private static final Logger LOGGER = LogManager.getLogger(FlowChooseDialog.class.getName());

	private static final long serialVersionUID = 1L;

	private final JPanel contentPanel = new JPanel();

	private Flow flow;

	public FlowChooseDialog(GroupEditForm frame) {
		this.flow = frame.getFlow();

		setModal(true);
		setResizable(false);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

		JComboBox<Flow> flowComboBox = new MyComboBox<>();

		addWindowListener(new WindowAdapter() {
			@Override
			public void windowOpened(WindowEvent e) {
				try {
					FormInitializer.initFlowComboBox(flowComboBox);
					if (flow != null) {
						flowComboBox.setSelectedItem(flow);
					} else {
						flow = (Flow) flowComboBox.getSelectedItem();
					}
				} catch (CommandException ex) {
					LOGGER.error(ex.getCause().getMessage(), ex);
					JOptionPane.showMessageDialog(getContentPane(), ex.getCause().getMessage(), "Ошибка",
							JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		setResizable(false);
		setBounds(100, 100, 222, 135);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);

		flowComboBox.addItemListener((ItemEvent evt) -> {
			if (evt.getStateChange() == ItemEvent.SELECTED) {
				flow = (Flow) flowComboBox.getSelectedItem();
			}
		});

		flowComboBox.setBounds(10, 25, 185, 34);
		contentPanel.add(flowComboBox);

		JLabel label = new JLabel("Название потока");
		label.setFont(new Font("Tahoma", Font.BOLD, 14));
		label.setBounds(10, 0, 156, 24);
		contentPanel.add(label);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.setActionCommand("OK");

				okButton.addActionListener(ItemEvent -> {
					frame.setNeedUpdate(true);
					frame.setFlow(flow);
					dispose();
				});

				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}

			if (this.flow != null) {

				JButton button = new JButton("Удалить");
				button.addActionListener(ItemEvent -> {
					frame.setFlow(null);
					frame.setNeedUpdate(true);
					dispose();
				});
				buttonPane.add(button);
			}
		}
	}
}