package by.bsac.timetable.view.extra;

import java.awt.BorderLayout;
import java.awt.Color;
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
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.bsac.timetable.command.exception.CommandException;
import by.bsac.timetable.command.util.CommandFacade;
import by.bsac.timetable.entity.Classroom;
import by.bsac.timetable.entity.builder.ClassroomBuilder;
import by.bsac.timetable.view.util.FormInitializer;
import components.OneColumnTable;

public class ClassroomEditForm extends JDialog {
  private static final long serialVersionUID = 1L;

  private static final Logger LOGGER = LogManager.getLogger(ClassroomEditForm.class.getName());

  private JDialog frame;
  private JTable table;
  private Classroom classroom;

  public ClassroomEditForm() {
    frame = this;

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
          FormInitializer.initClassroomTable(table);
        } catch (CommandException ex) {
          LOGGER.error(ex.getCause().getMessage(), ex);
          JOptionPane.showMessageDialog(getContentPane(), ex.getCause().getMessage());
        }
      }
    });

    JLabel label = new JLabel("Редактирование/Добавление");
    label.setFont(new Font("Tahoma", Font.BOLD, 14));
    label.setBounds(319, 39, 220, 18);
    contentPanel.add(label);

    JButton editButton = new JButton("Изменить");
    editButton.setFont(new Font("Tahoma", Font.PLAIN, 14));

    JButton deleteButton = new JButton("Удалить");
    deleteButton.setFont(new Font("Tahoma", Font.PLAIN, 14));

    JTextField numberField = new JTextField();
    contentPanel.add(numberField);
    numberField.setFont(new Font("Tahoma", Font.PLAIN, 14));
    numberField.setBounds(339, 117, 94, 26);

    JLabel buildingLabel = new JLabel("<html>Номер<br>корпуса</html>");
    buildingLabel.setForeground(Color.BLUE);
    buildingLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
    buildingLabel.setBounds(470, 68, 79, 38);
    contentPanel.add(buildingLabel);

    JTextField buildingField = new JTextField();
    buildingField.setFont(new Font("Tahoma", Font.PLAIN, 14));
    buildingField.setColumns(1);
    buildingField.setBounds(470, 117, 65, 26);
    contentPanel.add(buildingField);

    JLabel label_1 = new JLabel("<html>Номер<br>аудитории</html>");
    label_1.setForeground(new Color(0, 128, 0));
    label_1.setFont(new Font("Tahoma", Font.BOLD, 14));
    label_1.setBounds(341, 68, 79, 38);
    contentPanel.add(label_1);

    numberField.setColumns(1);

    editButton.setVisible(false);
    editButton.setBounds(333, 169, 100, 26);
    contentPanel.add(editButton);

    editButton.addActionListener(new java.awt.event.ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {

        try {
          editButton.setEnabled(false);
          byte building = Byte.valueOf(buildingField.getText());
          short number = Short.valueOf(numberField.getText());

          classroom.setBuilding(building);
          classroom.setNumber(number);

          CommandFacade.updateClassroom(classroom);
          FormInitializer.initClassroomTable(table);

        } catch (CommandException ex) {
          LOGGER.error(ex.getCause().getMessage(), ex);
          JOptionPane.showMessageDialog(getContentPane(), ex.getCause().getMessage());
        } catch (NumberFormatException ex) {
          LOGGER.warn(ex.getCause().getMessage(), ex);
          JOptionPane.showMessageDialog(getContentPane(), "Введены не верные числа");
        } finally {
          editButton.setEnabled(true);
          resetComponents(editButton, deleteButton, numberField, buildingField);
        }

      }
    });

    deleteButton.setBounds(394, 206, 89, 23);
    contentPanel.add(deleteButton);
    deleteButton.setVisible(false);
    deleteButton.addActionListener(new java.awt.event.ActionListener() {

      @Override
      public void actionPerformed(ActionEvent e) {

        int result = JOptionPane.showConfirmDialog(getContentPane(),
            "Перед удалением, вам необходимо переназначить аудиторию для занятий в текущем расписании",
            "Внимание!", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
        if (result == JOptionPane.YES_OPTION) {
          deleteButton.setEnabled(false);

          ChangeClassroomDialog dialog = new ChangeClassroomDialog(classroom);
          dialog.setLocationRelativeTo(frame);
          dialog.setVisible(true);

          try {

            /*
             * ICommand command = CommandProvider.getInstance().getCommand(ActionMode.
             * Delete_Classroom); Request request = new Request(); request.putParam("classroom",
             * classroom); command.execute(request);
             */

            FormInitializer.initClassroomTable(table);

          } catch (CommandException ex) {
            LOGGER.error(ex.getCause().getMessage(), ex);
            JOptionPane.showMessageDialog(getContentPane(), ex.getCause().getMessage());
          } finally {
            deleteButton.setEnabled(true);
            resetComponents(editButton, deleteButton, numberField, buildingField);
          }
        }
      }
    });

    JButton addButton = new JButton("Добавить");
    addButton.setFont(new Font("Tahoma", Font.PLAIN, 14));

    addButton.setBounds(444, 169, 105, 26);
    contentPanel.add(addButton);

    addButton.addActionListener(new java.awt.event.ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        try {
          addButton.setEnabled(false);
          byte building = Byte.valueOf(buildingField.getText());
          short number = Short.valueOf(numberField.getText());

          classroom = new ClassroomBuilder().buildBuilding(building).buildNumber(number).build();

          CommandFacade.addClassroom(classroom);
          FormInitializer.initClassroomTable(table);

        } catch (CommandException ex) {
          LOGGER.error(ex.getCause().getMessage(), ex);
          JOptionPane.showMessageDialog(getContentPane(), ex.getCause().getMessage());
        } catch (NumberFormatException ex) {
          LOGGER.warn(ex.getCause().getMessage(), ex);
          JOptionPane.showMessageDialog(getContentPane(), "Введены не верные числа");
        } finally {
          addButton.setEnabled(true);
          resetComponents(editButton, deleteButton, numberField, buildingField);
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
          classroom = (Classroom) table.getModel().getValueAt(rowIndex, columnIndex);

          LOGGER.debug("selected classroom:" + classroom);
          resetComponents(editButton, deleteButton, numberField, buildingField);

          buildingField.setText(String.valueOf(classroom.getBuilding()));
          numberField.setText(String.valueOf(classroom.getNumber()));
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

  private void resetComponents(JButton editButton, JButton deleteButton, JTextField numberTextFiled,
      JTextField buildingTextFiled) {
    editButton.setVisible(false);
    deleteButton.setVisible(false);
    numberTextFiled.setText("");
    buildingTextFiled.setText("");
  }
}
