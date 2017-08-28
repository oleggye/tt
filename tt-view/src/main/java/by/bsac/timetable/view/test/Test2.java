package by.bsac.timetable.view.test;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.ComboBoxModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import java.awt.event.InputMethodListener;
import java.awt.event.InputMethodEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JComboBox;
import javax.swing.event.AncestorListener;

import components.MyComboBoxModel;
import tableClasses.MyMultiSpanCellTable;

import javax.swing.event.AncestorEvent;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
import java.beans.PropertyChangeListener;
import java.util.Arrays;
import java.beans.PropertyChangeEvent;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import by.bsac.component.multiSpanCellTable.AttributiveCellTableModel;
import by.bsac.timetable.entity.Record;
import by.bsac.timetable.entity.Subject;
import by.bsac.timetable.entity.SubjectFor;
import by.bsac.timetable.entity.SubjectType;
import by.bsac.timetable.entity.builder.RecordBuilder;
import by.bsac.timetable.entity.builder.SubjectBuilder;
import by.bsac.timetable.entity.builder.SubjectForBuilder;
import by.bsac.timetable.entity.builder.SubjectTypeBuilder;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Test2 extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField textField;
	private MyMultiSpanCellTable table;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			Test2 dialog = new Test2();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public Test2() {
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		{
			textField = new JTextField();
			textField.setBounds(190, 10, 86, 20);
			textField.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					System.out.println("actionPerformed");
				}
			});
			textField.addInputMethodListener(new InputMethodListener() {
				public void caretPositionChanged(InputMethodEvent event) {
					System.out.println("dsadsad");
				}
				public void inputMethodTextChanged(InputMethodEvent event) {
					System.out.println("dsadsad");
				}
			});
			contentPanel.setLayout(null);
			{
				JComboBox<String> comboBox = new JComboBox<String>();
				comboBox.addPropertyChangeListener(new PropertyChangeListener() {
					public void propertyChange(PropertyChangeEvent evt) {
						System.err.println("propertyChange!");
					}
				});
				comboBox.addItemListener(new ItemListener() {
					public void itemStateChanged(ItemEvent e) {
						System.err.println("itemStateChanged!");
					}
				});
				comboBox.addAncestorListener(new AncestorListener() {
					public void ancestorAdded(AncestorEvent event) {
						System.err.println("ancestorAdded!");
					}
					public void ancestorMoved(AncestorEvent event) {
						System.err.println("ancestorMoved!");
					}
					public void ancestorRemoved(AncestorEvent event) {
						System.err.println("ancestorRemoved!");
					}
				});
				comboBox.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						System.err.println("actionPerformed!");
					}
				});
				comboBox.setBounds(45, 10, 140, 20);
				comboBox.setModel(new MyComboBoxModel<String>(Arrays.asList("1","2")
						));
				
				contentPanel.add(comboBox);
			}
			contentPanel.add(textField);
			textField.setColumns(10);
		}
		
		table = new MyMultiSpanCellTable();
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int row =table.rowAtPoint(e.getPoint());
				System.err.println("row:" +row);
				int column = table.columnAtPoint(e.getPoint());
				System.err.println("column:" +column);
				
				System.out.println(table.getValueAt(row, column));
			}
		});
		int[] a ={1,2,2,3};
		table.setModel(new AttributiveCellTableModel(
			new Object[][] {
				{new Integer(3123), new Integer(123), "sdsd", "sd"},
				{new RecordBuilder().build(), new Integer(1), "23", "asd"},
				{new Integer(123), new Integer(23), a, "1"},
			},
			new String[] {
				"New column", "New column", "New column", "New column"
			}
		)
		
		{
			Class[] columnTypes = new Class[] {
				Integer.class, Integer.class, String.class, Object.class
			};
			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}
		});
		SubjectFor subjectFor = new SubjectForBuilder().buildId((byte)1).build();
		Subject subject = new SubjectBuilder().buildId((short)1).buildNameSubject("123").buildAbname("Hello").build();
		SubjectType subjectType = new SubjectTypeBuilder().buildId((byte)1).build();
		
		Record record = new RecordBuilder().
				buildSubjectFor(subjectFor)
				.buildSubjOrdinalNumber((byte) 1)
				.buildSubject(subject)
				.buildSubjectType(subjectType)
				.build();
		table.changeTableModel(record);
		
		table.getColumnModel().getColumn(1).setPreferredWidth(76);
		table.setBounds(84, 80, 192, 48);
		contentPanel.add(table);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}
}
