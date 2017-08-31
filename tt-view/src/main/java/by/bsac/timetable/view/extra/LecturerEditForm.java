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
import by.bsac.timetable.entity.Lecturer;
import by.bsac.timetable.entity.builder.LecturerBuilder;
import by.bsac.timetable.view.extra.controller.CoincidenceTextFieldController;
import by.bsac.timetable.view.util.FormInitializer;
import components.MyComboBox;
import components.OneColumnTable;

public class LecturerEditForm extends JDialog {
  private static final long serialVersionUID = 1L;

  private static final Logger LOGGER = LogManager.getLogger(GroupEditForm.class.getName());

  private JDialog frame;
  private JTable table;
  private Lecturer lecturer;

  private boolean isNeedUpdate;

  public LecturerEditForm() {
    frame = this;
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

    JTextField lecturerNameTextField = new JTextField();
    lecturerNameTextField.setFont(new Font("Tahoma", Font.PLAIN, 14));

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

          FormInitializer.initLecturerTable(table, chair);
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

            FormInitializer.initLecturerTable(table, chair);

          } catch (CommandException ex) {
            LOGGER.error(ex.getCause().getMessage(), ex);
            JOptionPane.showMessageDialog(getContentPane(), ex);
          } finally {
            refreshFormField(editButton, deleteButton, lecturerNameTextField);
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

    lecturerNameTextField.setBounds(20, 11, 200, 23);
    panel.add(lecturerNameTextField);
    lecturerNameTextField.setColumns(10);

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
    lecturerNameTextField.addKeyListener(new CoincidenceTextFieldController(this, Lecturer.class,
        coincidenceTextArea, lecturerNameTextField, coincidenceScrollPane, coincidenceLabel));

    coincidenceLabel.setForeground(Color.RED);
    coincidenceLabel.setBounds(334, 226, 91, 14);
    coincidenceLabel.setVisible(false);
    contentPanel.add(coincidenceLabel);

    addButton.addActionListener(new java.awt.event.ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        addButton.setEnabled(false);

        try {
          String lecturerName = lecturerNameTextField.getText();
          Chair chair = (Chair) chairComboBox.getSelectedItem();
          Lecturer lecturer =
              new LecturerBuilder().buildChair(chair).buildNameLecturer(lecturerName).build();

          CommandFacade.addLecturer(lecturer);
          FormInitializer.initLecturerTable(table, chair);

        } catch (CommandException ex) {
          LOGGER.error(ex.getCause().getMessage(), ex);
          JOptionPane.showMessageDialog(getContentPane(), ex);
        } finally {
          addButton.setEnabled(true);
          refreshFormField(editButton, deleteButton, lecturerNameTextField);
        }
      }
    });

    editButton.setVisible(false);
    editButton.addActionListener(new java.awt.event.ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {

        editButton.setEnabled(false);
        try {

          String lecturerName = lecturerNameTextField.getText();
          Chair chair = (Chair) chairComboBox.getSelectedItem();
          lecturer.setChair(chair);
          lecturer.setNameLecturer(lecturerName);

          CommandFacade.updateLecturer(lecturer);
          FormInitializer.initLecturerTable(table, chair);

        } catch (CommandException ex) {
          LOGGER.error(ex.getCause().getMessage(), ex);
          JOptionPane.showMessageDialog(getContentPane(), ex);
        } finally {
          editButton.setEnabled(true);
          refreshFormField(editButton, deleteButton, lecturerNameTextField);
        }
      }
    });

    deleteButton.addActionListener(new java.awt.event.ActionListener() {

      @Override
      public void actionPerformed(ActionEvent e) {

        int result = JOptionPane.showConfirmDialog(getContentPane(),
            "Перед удалением, вам необходимо переназначить ведение занятий данного преподавателя",
            "Внимание!", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
        if (result == JOptionPane.YES_OPTION) {
          deleteButton.setEnabled(false);

          Chair chair = (Chair) chairComboBox.getSelectedItem();
          JDialog dialog = new ChangeLecturerDialog(chair, lecturer);
          dialog.setLocationRelativeTo(frame);
          dialog.setVisible(true);

          try {
            /*
             * ICommand command = CommandProvider.getInstance().getCommand(ActionMode.
             * Delete_Lecturer); Request request = new Request(); request.putParam("lecturer",
             * lecturer);
             * 
             * command.execute(request);
             */

            // Chair chair = (Chair)
            // chairComboBox.getSelectedItem();
            FormInitializer.initLecturerTable(table, chair);

          } catch (CommandException ex) {
            LOGGER.error(ex.getCause().getMessage(), ex);
            JOptionPane.showMessageDialog(getContentPane(), ex);
          } finally {
            deleteButton.setEnabled(true);
            refreshFormField(editButton, deleteButton, lecturerNameTextField);
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
          lecturer = (Lecturer) table.getModel().getValueAt(rowIndex, columnIndex);

          LOGGER.debug("selected lecturer:" + lecturer.getName());

          refreshFormField(editButton, deleteButton, lecturerNameTextField);

          lecturerNameTextField.setText(lecturer.getName());

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
