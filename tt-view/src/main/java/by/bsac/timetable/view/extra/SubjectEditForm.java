package by.bsac.timetable.view.extra;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.bsac.timetable.command.exception.CommandException;
import by.bsac.timetable.command.util.CommandFacade;
import by.bsac.timetable.entity.Chair;
import by.bsac.timetable.entity.Subject;
import by.bsac.timetable.entity.builder.SubjectBuilder;
import by.bsac.timetable.view.extra.controller.CoincidenceTextFieldController;
import by.bsac.timetable.view.util.FormInitializer;
import components.MyComboBox;
import components.OneColumnTable;

public class SubjectEditForm extends JDialog {
  private static final long serialVersionUID = 1L;

  private static final Logger LOGGER = LogManager.getLogger(GroupEditForm.class.getName());

  private JTable table;
  private Subject subject;

  private boolean isNeedUpdate;

  public SubjectEditForm(byte educationLevel) {
    isNeedUpdate = false;

    setBounds(100, 100, 590, 380);
    setModal(true);
    setResizable(false);
    getContentPane().setLayout(new BorderLayout());
    setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

    JComboBox<Chair> chairComboBox = new MyComboBox<>();
    chairComboBox.setFont(new Font("Tahoma", Font.PLAIN, 14));

    JButton addButton = new JButton("Добавить");
    addButton.setFont(new Font("Tahoma", Font.PLAIN, 12));

    JButton editButton = new JButton("Изменить");
    editButton.setFont(new Font("Tahoma", Font.PLAIN, 12));

    JButton deleteButton = new JButton("Удалить");
    deleteButton.setFont(new Font("Tahoma", Font.PLAIN, 12));

    JTextField subjectNameTextField = new JTextField();
    subjectNameTextField.setFont(new Font("Tahoma", Font.PLAIN, 14));

    JLabel coincidenceLabel = new JLabel("Совпадения");
    coincidenceLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));

    JTextArea coincidenceTextArea = new JTextArea();
    coincidenceTextArea.setEditable(false);
    coincidenceTextArea.setFont(new Font("Monospaced", Font.ITALIC, 14));

    JPanel contentPanel = new JPanel();
    contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
    getContentPane().add(contentPanel, BorderLayout.CENTER);
    contentPanel.setLayout(null);
    addWindowListener(new java.awt.event.WindowAdapter() {
      @Override
      public void windowOpened(java.awt.event.WindowEvent evt) {
        try {
          FormInitializer.initChairComboBox(chairComboBox);
          Chair chair = (Chair) chairComboBox.getSelectedItem();

          FormInitializer.initSubjectTable(table, chair, educationLevel);
        } catch (CommandException ex) {
          LOGGER.error(ex.getCause().getMessage(), ex);
          JOptionPane.showMessageDialog(getContentPane(), ex);
        }
      }
    });

    JLabel lblNewLabel = new JLabel("Кафедра");
    lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 14));

    lblNewLabel.setBounds(334, 11, 78, 18);
    contentPanel.add(lblNewLabel);

    JLabel label = new JLabel("Редактирование/Добавление");
    label.setFont(new Font("Tahoma", Font.BOLD, 14));
    label.setBounds(334, 74, 234, 18);
    contentPanel.add(label);

    chairComboBox.setBounds(334, 40, 234, 23);
    contentPanel.add(chairComboBox);
    chairComboBox.setToolTipText("");

    chairComboBox.addItemListener(new java.awt.event.ItemListener() {
      @Override
      public void itemStateChanged(java.awt.event.ItemEvent evt) {
        if (evt.getStateChange() == ItemEvent.SELECTED) {
          try {
            Chair chair = (Chair) chairComboBox.getSelectedItem();

            FormInitializer.initSubjectTable(table, chair, educationLevel);

          } catch (CommandException ex) {
            LOGGER.error(ex.getCause().getMessage(), ex);
            JOptionPane.showMessageDialog(getContentPane(), ex);
          } finally {
            refreshFormField(editButton, deleteButton, subjectNameTextField);
          }
        }
      }
    });

    ImageIcon icon = new ImageIcon("C:\\Users\\hello\\Desktop\\add-icon.png");
    Image img = icon.getImage();
    Image newimg = img.getScaledInstance(37, 23, java.awt.Image.SCALE_SMOOTH);
    icon = new ImageIcon(newimg);

    table = new OneColumnTable();
    table.setCellSelectionEnabled(true);
    table.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
    table.setFont(new Font("Verdana", Font.BOLD, 14));

    JScrollPane scrollPane = new JScrollPane(table);

    scrollPane.setBounds(25, 40, 259, 245);
    contentPanel.add(scrollPane);

    JPanel panel = new JPanel();
    panel.setBounds(334, 98, 234, 117);
    contentPanel.add(panel);
    panel.setLayout(null);

    subjectNameTextField.setBounds(20, 11, 200, 23);
    panel.add(subjectNameTextField);
    subjectNameTextField.setColumns(10);

    editButton.setBounds(20, 45, 100, 23);
    panel.add(editButton);

    addButton.setBounds(130, 45, 95, 23);
    panel.add(addButton);

    deleteButton.setBounds(81, 79, 100, 23);
    deleteButton.setVisible(false);
    panel.add(deleteButton);

    JScrollPane coincidenceScrollPane = new JScrollPane();
    coincidenceScrollPane.setBounds(334, 251, 234, 54);
    coincidenceScrollPane.setVisible(false);
    contentPanel.add(coincidenceScrollPane);

    coincidenceScrollPane.setViewportView(coincidenceTextArea);
    subjectNameTextField.addKeyListener(new CoincidenceTextFieldController(this, Subject.class,
        coincidenceTextArea, subjectNameTextField, coincidenceScrollPane, coincidenceLabel));

    coincidenceLabel.setForeground(Color.RED);
    coincidenceLabel.setBounds(334, 226, 91, 14);
    coincidenceLabel.setVisible(false);
    contentPanel.add(coincidenceLabel);

    addButton.addActionListener(new java.awt.event.ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        addButton.setEnabled(false);

        try {
          String lecturerName = subjectNameTextField.getText();
          Chair chair = (Chair) chairComboBox.getSelectedItem();
          Subject subject = new SubjectBuilder().buildChair(chair).buildEduLevel(educationLevel)
              .buildNameSubject(lecturerName).build();

          CommandFacade.addSubject(subject);
          FormInitializer.initSubjectTable(table, chair, educationLevel);

        } catch (CommandException ex) {
          LOGGER.error(ex.getCause().getMessage(), ex);
          JOptionPane.showMessageDialog(getContentPane(), ex);
        } finally {
          addButton.setEnabled(true);
          refreshFormField(editButton, deleteButton, subjectNameTextField);
        }
      }
    });

    editButton.setVisible(false);
    editButton.addActionListener(new java.awt.event.ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {

        editButton.setEnabled(false);
        try {

          String subjectName = subjectNameTextField.getText();
          Chair chair = (Chair) chairComboBox.getSelectedItem();
          subject.setChair(chair);
          subject.setNameSubject(subjectName);

          CommandFacade.updateSubject(subject);
          FormInitializer.initSubjectTable(table, chair, educationLevel);

        } catch (CommandException ex) {
          LOGGER.error(ex.getCause().getMessage(), ex);
          JOptionPane.showMessageDialog(getContentPane(), ex);
        } finally {
          editButton.setEnabled(true);
          refreshFormField(editButton, deleteButton, subjectNameTextField);
        }
      }
    });

    deleteButton.addActionListener(new java.awt.event.ActionListener() {

      @Override
      public void actionPerformed(ActionEvent e) {

        int result = JOptionPane.showConfirmDialog(getContentPane(),
            "Убедитесь, что удаляемая дисциплина не связана с записями текущего расписания",
            "Внимание!", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
        if (result == JOptionPane.YES_OPTION) {
          deleteButton.setEnabled(false);
          try {
            CommandFacade.deleteSubject(subject);
            Chair chair = (Chair) chairComboBox.getSelectedItem();
            FormInitializer.initSubjectTable(table, chair, educationLevel);

          } catch (CommandException ex) {
            LOGGER.error(ex.getCause().getMessage(), ex);
            JOptionPane.showMessageDialog(getContentPane(), ex);
          } finally {
            deleteButton.setEnabled(true);
            refreshFormField(editButton, deleteButton, subjectNameTextField);
          }
        }
      }
    });

    table.addMouseListener(new java.awt.event.MouseAdapter() {
      @Override
      public void mouseClicked(java.awt.event.MouseEvent evt) {
        int columnIndex = table.getSelectedColumn();
        int rowIndex = table.getSelectedRow();
        if (rowIndex >= 0) {
          subject = (Subject) table.getModel().getValueAt(rowIndex, columnIndex);

          LOGGER.debug("selected subject:" + subject.getName());

          refreshFormField(editButton, deleteButton, subjectNameTextField);

          subjectNameTextField.setText(subject.getName());

          editButton.setVisible(true);
          deleteButton.setVisible(true);
        }
      }
    });

    {
      JPanel buttonPane = new JPanel();
      buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
      getContentPane().add(buttonPane, BorderLayout.SOUTH);
      {
        JButton okButton = new JButton("OK");
        okButton.setFont(new Font("Tahoma", Font.PLAIN, 14));
        okButton.setActionCommand("OK");
        buttonPane.add(okButton);
        getRootPane().setDefaultButton(okButton);

        okButton.addActionListener(new java.awt.event.ActionListener() {
          @Override
          public void actionPerformed(ActionEvent e) {
            dispose();
          }
        });
      }
    }
  }

  public void setNeedUpdate(boolean isNeedUpdate) {
    this.isNeedUpdate = isNeedUpdate;
  }

  public boolean isNeedUpdate() {
    return isNeedUpdate;
  }

  private void refreshFormField(JButton editButton, JButton deleteButton,
      JTextField groupNameTextField) {
    editButton.setVisible(false);
    deleteButton.setVisible(false);
    groupNameTextField.setText("");
  }
}
