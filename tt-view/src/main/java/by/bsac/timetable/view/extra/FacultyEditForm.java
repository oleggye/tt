package by.bsac.timetable.view.extra;

import by.bsac.timetable.command.exception.CommandException;
import by.bsac.timetable.command.util.CommandFacade;
import by.bsac.timetable.entity.Faculty;
import by.bsac.timetable.entity.builder.FacultyBuilder;
import by.bsac.timetable.view.util.FormInitializer;
import by.bsac.timetable.view.component.OneColumnTable;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class FacultyEditForm extends JDialog {
  private static final long serialVersionUID = 1L;

  private static final Logger LOGGER = LogManager.getLogger(FacultyEditForm.class.getName());

  private JTable table;
  private Faculty faculty;

  public FacultyEditForm() {

    setBounds(100, 100, 590, 380);
    setModal(true);
    setResizable(false);
    setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
    getContentPane().setLayout(new BorderLayout());

    JPanel contentPanel = new JPanel();
    contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
    getContentPane().add(contentPanel, BorderLayout.CENTER);
    contentPanel.setLayout(null);
    addWindowListener(new java.awt.event.WindowAdapter() {
      @Override
      public void windowOpened(java.awt.event.WindowEvent evt) {
        try {
          FormInitializer.initFacultyTable(table);
        } catch (CommandException ex) {
          LOGGER.error(ex.getCause().getMessage(), ex);
          JOptionPane.showMessageDialog(getContentPane(), ex.getCause().getMessage());
        }
      }
    });

    JLabel label = new JLabel("Редактирование/Добавление");
    label.setFont(new Font("Tahoma", Font.BOLD, 14));
    label.setBounds(323, 56, 220, 18);
    contentPanel.add(label);

    JButton editButton = new JButton("Изменить");
    editButton.setFont(new Font("Tahoma", Font.PLAIN, 14));

    JButton deleteButton = new JButton("Удалить");
    deleteButton.setFont(new Font("Tahoma", Font.PLAIN, 14));

    JTextArea textField = new JTextArea(1, 1);
    textField.setFont(new Font("Tahoma", Font.PLAIN, 14));

    JScrollPane scrollPane = new JScrollPane(textField);
    scrollPane.setBounds(323, 85, 220, 46);
    contentPanel.add(scrollPane);

    textField.setColumns(1);

    editButton.setVisible(false);
    editButton.setBounds(323, 142, 100, 26);
    contentPanel.add(editButton);

    editButton.addActionListener(new java.awt.event.ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        String nameFaculty = textField.getText();
        boolean isTableHasNot = isTableHasNotAlikeValue(table, nameFaculty);

        if (isTableHasNot) {
          try {
            editButton.setEnabled(false);

            Faculty editingFaculty = new FacultyBuilder()
                                .id(faculty.getIdFaculty())
                                .name(nameFaculty)
                                .build();
            CommandFacade.updateFaculty(editingFaculty);
            FormInitializer.initFacultyTable(table);

          } catch (CommandException ex) {
            LOGGER.error(ex.getCause().getMessage(), ex);
            JOptionPane.showMessageDialog(getContentPane(), ex.getCause().getMessage());
          } finally {
            editButton.setEnabled(true);
            resetComponents(editButton, deleteButton, textField);
          }
        }
      }
    });

    deleteButton.setBounds(395, 179, 89, 23);
    contentPanel.add(deleteButton);
    deleteButton.setVisible(false);
    deleteButton.addActionListener(new java.awt.event.ActionListener() {

      @Override
      public void actionPerformed(ActionEvent e) {
        int result = JOptionPane.showConfirmDialog(getContentPane(),
            "Удаление факультета повлечен за собой удаление всех связанных с ним групп, которые, в свою очередь, приведут к удалению связанных с ними занятий",
            "Внимание!", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
        if (result == JOptionPane.YES_OPTION) {
          try {
            deleteButton.setEnabled(false);

            CommandFacade.deleteFaculty(faculty);
            FormInitializer.initFacultyTable(table);

          } catch (CommandException ex) {
            LOGGER.error(ex.getCause().getMessage(), ex);
            JOptionPane.showMessageDialog(getContentPane(), ex.getCause().getMessage());
          } finally {
            deleteButton.setEnabled(true);
            resetComponents(editButton, deleteButton, textField);
          }
        }
      }
    });

    JButton addButton = new JButton("Добавить");
    addButton.setFont(new Font("Tahoma", Font.PLAIN, 14));

    addButton.setBounds(438, 142, 105, 26);
    contentPanel.add(addButton);

    addButton.addActionListener(new java.awt.event.ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        String nameFaculty = textField.getText();
        boolean isTableHasNot = isTableHasNotAlikeValue(table, nameFaculty);

        if (isTableHasNot) {
          try {
            addButton.setEnabled(false);
            faculty = new FacultyBuilder().name(nameFaculty).build();

            CommandFacade.addFaculty(faculty);
            FormInitializer.initFacultyTable(table);

          } catch (CommandException ex) {
            LOGGER.error(ex.getCause().getMessage(), ex);
            JOptionPane.showMessageDialog(getContentPane(), ex.getCause().getMessage());
          } finally {
            addButton.setEnabled(true);
            resetComponents(editButton, deleteButton, textField);
          }
        }
      }
    });

    table = new OneColumnTable();
    table.setCellSelectionEnabled(true);

    table.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
    table.setFont(new Font("Verdana", Font.BOLD, 14));

    JScrollPane tableScrollPane = new JScrollPane(table);

    tableScrollPane.setBounds(25, 40, 259, 245);
    contentPanel.add(tableScrollPane);

    table.addMouseListener(new java.awt.event.MouseAdapter() {
      @Override
      public void mouseClicked(java.awt.event.MouseEvent evt) {
        int columnIndex = table.getSelectedColumn();
        int rowIndex = table.getSelectedRow();
        if (rowIndex >= 0) {
          faculty = (Faculty) table.getModel().getValueAt(rowIndex, columnIndex);

          LOGGER.debug("selected faculty:" + faculty);
          resetComponents(editButton, deleteButton, textField);

          textField.setText(faculty.getName());
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
        okButton.setFont(new Font("Tahoma", Font.PLAIN, 16));
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

  private void resetComponents(JButton editButton, JButton deleteButton, JTextArea textField) {
    editButton.setVisible(false);
    deleteButton.setVisible(false);
    textField.setText("");
  }

  private boolean isTableHasNotAlikeValue(JTable table, String nameFaculty) {
    int colCount = table.getColumnCount();
    int rowCount = table.getRowCount();
    for (int column = 0; column < colCount; column++)
      for (int row = 0; row < rowCount; row++) {
        Faculty value = (Faculty) table.getValueAt(row, column);
        if (value.getNameFaculty().equals(nameFaculty)) {
          LOGGER.warn("try to dublicete nameFaculty:" + nameFaculty);
          JOptionPane.showMessageDialog(getContentPane(), "Факультет с таким именем уже есть");
          return false;
        }
      }
    return true;
  }
}
