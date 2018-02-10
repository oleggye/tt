package by.bsac.timetable.view.extra;

import by.bsac.timetable.command.exception.CommandException;
import by.bsac.timetable.command.util.CommandFacade;
import by.bsac.timetable.entity.Chair;
import by.bsac.timetable.entity.Subject;
import by.bsac.timetable.entity.builder.SubjectBuilder;
import by.bsac.timetable.view.component.MyComboBox;
import by.bsac.timetable.view.component.OneColumnTable;
import by.bsac.timetable.view.extra.controller.CoincidenceTextFieldController;
import by.bsac.timetable.view.util.FormInitializer;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SubjectEditForm extends JDialog {

  private static final long serialVersionUID = 1L;

  private static final Logger LOGGER = LoggerFactory.getLogger(GroupEditForm.class.getName());
  private static final String FONT_NAME = "Tahoma";
  private JTable table;
  private Subject subject;

  private boolean isNeedUpdate;
  private JTextField abbrTextField;

  public SubjectEditForm(byte educationLevel) {
    isNeedUpdate = false;

    setBounds(100, 100, 590, 380);
    setModal(true);
    setResizable(false);
    getContentPane().setLayout(new BorderLayout());
    setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

    JComboBox<Chair> chairComboBox = new MyComboBox<>();
    chairComboBox.setFont(new Font(FONT_NAME, Font.PLAIN, 14));

    JButton addButton = new JButton("Добавить");
    addButton.setFont(new Font(FONT_NAME, Font.PLAIN, 12));

    JButton editButton = new JButton("Изменить");
    editButton.setFont(new Font(FONT_NAME, Font.PLAIN, 12));

    JButton deleteButton = new JButton("Удалить");
    deleteButton.setFont(new Font(FONT_NAME, Font.PLAIN, 12));

    JTextField subjectNameTextField = new JTextField();
    subjectNameTextField.setFont(new Font(FONT_NAME, Font.PLAIN, 14));

    JLabel coincidenceLabel = new JLabel("Совпадения");
    coincidenceLabel.setFont(new Font(FONT_NAME, Font.PLAIN, 14));

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
          JOptionPane.showMessageDialog(getContentPane(), ex.getCause().getMessage());
        }
      }
    });

    JLabel lblNewLabel = new JLabel("Кафедра");
    lblNewLabel.setFont(new Font(FONT_NAME, Font.BOLD, 14));

    lblNewLabel.setBounds(334, 11, 78, 18);
    contentPanel.add(lblNewLabel);

    JLabel label = new JLabel("Редактирование/Добавление");
    label.setFont(new Font(FONT_NAME, Font.BOLD, 14));
    label.setBounds(334, 74, 234, 18);
    contentPanel.add(label);

    chairComboBox.setBounds(334, 40, 234, 23);
    contentPanel.add(chairComboBox);
    chairComboBox.setToolTipText("");

    chairComboBox.addItemListener(evt -> {
      if (evt.getStateChange() == ItemEvent.SELECTED) {
        try {
          Chair chair = (Chair) chairComboBox.getSelectedItem();

          FormInitializer.initSubjectTable(table, chair, educationLevel);

        } catch (CommandException ex) {
          LOGGER.error(ex.getCause().getMessage(), ex);
          JOptionPane.showMessageDialog(getContentPane(), ex.getCause().getMessage());
        } finally {
          refreshFormField(editButton, deleteButton, subjectNameTextField, abbrTextField);
        }
      }
    });

    ImageIcon icon = new ImageIcon("C:\\Users\\hello\\Desktop\\add-icon.png");
    Image img = icon.getImage();
    Image addIcon = img.getScaledInstance(37, 23, java.awt.Image.SCALE_SMOOTH);
    icon = new ImageIcon(addIcon);

    table = new OneColumnTable();
    table.setCellSelectionEnabled(true);
    table.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
    table.setFont(new Font("Verdana", Font.BOLD, 14));

    JScrollPane scrollPane = new JScrollPane(table);

    scrollPane.setBounds(25, 40, 259, 245);
    contentPanel.add(scrollPane);

    JPanel panel = new JPanel();
    panel.setBounds(334, 98, 234, 124);
    contentPanel.add(panel);
    panel.setLayout(null);

    subjectNameTextField.setBounds(20, 25, 200, 23);
    panel.add(subjectNameTextField);
    subjectNameTextField.setColumns(10);

    editButton.setBounds(20, 101, 100, 23);
    panel.add(editButton);

    addButton.setBounds(130, 67, 95, 23);
    addButton.setIcon(icon);
    panel.add(addButton);

    deleteButton.setBounds(130, 101, 95, 23);
    deleteButton.setVisible(false);
    panel.add(deleteButton);

    JScrollPane coincidenceScrollPane = new JScrollPane();
    coincidenceScrollPane.setBounds(334, 251, 234, 54);
    coincidenceScrollPane.setVisible(false);
    contentPanel.add(coincidenceScrollPane);

    coincidenceScrollPane.setViewportView(coincidenceTextArea);
    subjectNameTextField.addKeyListener(new CoincidenceTextFieldController(this, Subject.class,
        coincidenceTextArea, subjectNameTextField, coincidenceScrollPane, coincidenceLabel));

    abbrTextField = new JTextField();
    abbrTextField.setBounds(20, 70, 86, 20);
    panel.add(abbrTextField);
    abbrTextField.setColumns(10);

    JLabel abbrLabel = new JLabel("Аббревиатура");
    abbrLabel.setFont(new Font(FONT_NAME, Font.BOLD | Font.ITALIC, 12));
    abbrLabel.setBounds(20, 50, 100, 14);
    panel.add(abbrLabel);

    JLabel titleLabel = new JLabel("Название");
    titleLabel.setFont(new Font(FONT_NAME, Font.BOLD | Font.ITALIC, 12));
    titleLabel.setBounds(20, 5, 100, 14);
    panel.add(titleLabel);

    coincidenceLabel.setForeground(Color.RED);
    coincidenceLabel.setBounds(334, 226, 91, 14);
    coincidenceLabel.setVisible(false);
    contentPanel.add(coincidenceLabel);

    addButton.addActionListener(e -> {
      String subjectName = subjectNameTextField.getText();
      String abbrName = abbrTextField.getText();
      boolean isTableHasNot = isTableHasNotAlikeValue(table, subjectName, false);

      if (isTableHasNot) {
        try {
          addButton.setEnabled(false);

          Chair chair = (Chair) chairComboBox.getSelectedItem();
          Subject newSubject = new SubjectBuilder().chair(chair).abName(abbrName)
              .eduLevel(educationLevel).name(subjectName).build();

          CommandFacade.addSubject(newSubject);
          FormInitializer.initSubjectTable(table, chair, educationLevel);

        } catch (CommandException ex) {
          LOGGER.error(ex.getCause().getMessage(), ex);
          JOptionPane.showMessageDialog(getContentPane(), ex.getCause().getMessage());
        } finally {
          addButton.setEnabled(true);
          refreshFormField(editButton, deleteButton, subjectNameTextField, abbrTextField);
        }
      }
    });

    editButton.setVisible(false);
    editButton.addActionListener(e -> {
      String subjectName = subjectNameTextField.getText();
      String abbrName = abbrTextField.getText();
      final boolean isTableHasNot = isTableHasNotAlikeValue(table, subjectName, true);

      if (isTableHasNot) {
        try {
          editButton.setEnabled(false);

          Chair chair = (Chair) chairComboBox.getSelectedItem();
          Subject newSubject = new SubjectBuilder()
              .id(subject.getIdSubject())
              .chair(subject.getChair())
              .abName(abbrName)
              .eduLevel(subject.getEduLevel())
              .name(subjectName)
              .build();

          CommandFacade.updateSubject(newSubject);
          FormInitializer.initSubjectTable(table, chair, educationLevel);

        } catch (CommandException ex) {
          LOGGER.error(ex.getCause().getMessage(), ex);
          JOptionPane.showMessageDialog(getContentPane(), ex.getCause().getMessage());
        } finally {
          editButton.setEnabled(true);
          refreshFormField(editButton, deleteButton, subjectNameTextField, abbrTextField);
        }
      }
    });

    deleteButton.addActionListener(e -> {

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
          JOptionPane.showMessageDialog(getContentPane(), ex.getCause().getMessage());
        } finally {
          deleteButton.setEnabled(true);
          refreshFormField(editButton, deleteButton, subjectNameTextField, abbrTextField);
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

          refreshFormField(editButton, deleteButton, subjectNameTextField, abbrTextField);

          subjectNameTextField.setText(subject.getName());
          abbrTextField.setText(subject.getAbnameSubject());

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
        okButton.setFont(new Font(FONT_NAME, Font.PLAIN, 14));
        okButton.setActionCommand("OK");
        buttonPane.add(okButton);
        getRootPane().setDefaultButton(okButton);

        okButton.addActionListener(e -> dispose());
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
      JTextField groupNameTextField, JTextField abbrTextField) {
    editButton.setVisible(false);
    deleteButton.setVisible(false);
    groupNameTextField.setText("");
    abbrTextField.setText("");
  }

  private boolean isTableHasNotAlikeValue(JTable table, String nameSubject, boolean isUpdate) {
    if (isUpdate) {
      return true;
    }

    int colCount = table.getColumnCount();
    int rowCount = table.getRowCount();
    for (int column = 0; column < colCount; column++) {
      for (int row = 0; row < rowCount; row++) {
        Subject value = (Subject) table.getValueAt(row, column);
        if (value.getNameSubject().equals(nameSubject)) {
          LOGGER.warn("try to duplicate nameSubject: {}", nameSubject);
          JOptionPane.showMessageDialog(getContentPane(), "Дисциплина с таким именем уже есть");
          return false;
        }
      }
    }
    return true;
  }
}