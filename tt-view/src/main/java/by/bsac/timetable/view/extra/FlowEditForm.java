package by.bsac.timetable.view.extra;

import by.bsac.timetable.command.exception.CommandException;
import by.bsac.timetable.command.util.CommandFacade;
import by.bsac.timetable.entity.Flow;
import by.bsac.timetable.entity.builder.FlowBuilder;
import by.bsac.timetable.view.component.OneColumnTable;
import by.bsac.timetable.view.util.FormInitializer;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JButton;
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

public class FlowEditForm extends JDialog {

  private static final long serialVersionUID = 1L;

  private static final Logger LOGGER = LoggerFactory.getLogger(FlowEditForm.class.getName());
  private static final String FONT_NAME = "Tahoma";

  private JTable table;
  private Flow flow;
  private JTextField textField;
  private JTextArea groupInFlowTextArea;

  public FlowEditForm() {

    setBounds(100, 100, 590, 380);
    setModal(true);
    setResizable(false);
    setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
    getContentPane().setLayout(new BorderLayout());

    JPanel contentPanel = new JPanel();
    contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
    getContentPane().add(contentPanel, BorderLayout.CENTER);
    contentPanel.setLayout(null);
    addWindowListener(new WindowAdapter() {
      @Override
      public void windowOpened(WindowEvent evt) {
        try {
          FormInitializer.initFlowTable(table);
        } catch (CommandException ex) {
          LOGGER.error(ex.getCause().getMessage(), ex);
          JOptionPane.showMessageDialog(getContentPane(), ex.getCause().getMessage());
        }
      }
    });

    JLabel label = new JLabel("Редактирование/Добавление");
    label.setFont(new Font(FONT_NAME, Font.BOLD, 14));
    label.setBounds(323, 56, 220, 18);
    contentPanel.add(label);

    JButton editButton = new JButton("Изменить");
    editButton.setFont(new Font(FONT_NAME, Font.PLAIN, 14));

    JButton deleteButton = new JButton("Удалить");
    deleteButton.setFont(new Font(FONT_NAME, Font.PLAIN, 14));

    editButton.setVisible(false);
    editButton.setBounds(323, 126, 100, 26);
    contentPanel.add(editButton);

    editButton.addActionListener(e -> {

      String nameFlow = textField.getText();
      boolean isTableHasNot = isTableHasNotAlikeValue(table, nameFlow);

      if (isTableHasNot) {

        try {
          editButton.setEnabled(false);

          Flow editingFlow = new FlowBuilder()
              .buildId(flow.getIdFlow())
              .buildName(nameFlow)
              .build();
          CommandFacade.updateFlow(editingFlow);
          FormInitializer.initFlowTable(table);

        } catch (CommandException ex) {
          LOGGER.error(ex.getCause().getMessage(), ex);
          JOptionPane.showMessageDialog(getContentPane(), ex.getCause().getMessage());
        } finally {
          editButton.setEnabled(true);
          resetComponents(editButton, deleteButton, textField, groupInFlowTextArea);
        }
      }
    });

    deleteButton.setBounds(388, 163, 89, 23);
    contentPanel.add(deleteButton);
    deleteButton.setVisible(false);
    deleteButton.addActionListener(e -> {
      int result = JOptionPane.showConfirmDialog(getContentPane(),
          "Перед удалением потока, убедитесь, что он не используется в текущем расписании",
          "Внимание!", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
      if (result == JOptionPane.YES_OPTION) {
        try {
          deleteButton.setEnabled(false);

          CommandFacade.deleteFlow(flow);
          FormInitializer.initFlowTable(table);

        } catch (CommandException ex) {
          LOGGER.error(ex.getCause().getMessage(), ex);
          JOptionPane.showMessageDialog(getContentPane(), ex.getCause().getMessage());

        } finally {
          deleteButton.setEnabled(true);
          resetComponents(editButton, deleteButton, textField, groupInFlowTextArea);
        }
      }
    });

    JButton addButton = new JButton("Добавить");
    addButton.setFont(new Font(FONT_NAME, Font.PLAIN, 14));

    addButton.setBounds(438, 126, 105, 26);
    contentPanel.add(addButton);

    addButton.addActionListener(e -> {
      String nameFlow = textField.getText();
      boolean isTableHasNot = isTableHasNotAlikeValue(table, nameFlow);

      if (isTableHasNot) {
        try {
          addButton.setEnabled(false);

          flow = new FlowBuilder().buildName(nameFlow).build();

          CommandFacade.addFlow(flow);
          FormInitializer.initFlowTable(table);

        } catch (CommandException ex) {
          LOGGER.error(ex.getCause().getMessage(), ex);
          JOptionPane.showMessageDialog(getContentPane(), ex.getCause().getMessage());
        } finally {
          addButton.setEnabled(true);
          resetComponents(editButton, deleteButton, textField, groupInFlowTextArea);
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

    JScrollPane scrollPane = new JScrollPane();
    scrollPane.setBounds(323, 222, 220, 67);
    contentPanel.add(scrollPane);

    groupInFlowTextArea = new JTextArea();
    groupInFlowTextArea.setColumns(1);
    groupInFlowTextArea.setVisible(false);
    groupInFlowTextArea.setEditable(false);
    scrollPane.setViewportView(groupInFlowTextArea);

    textField = new JTextField();
    textField.setFont(new Font(FONT_NAME, Font.PLAIN, 12));
    textField.setBounds(323, 95, 220, 20);
    contentPanel.add(textField);
    textField.setColumns(10);

    JLabel groupListLabel = new JLabel("Список групп данного потока");
    groupListLabel.setFont(new Font(FONT_NAME, Font.BOLD, 14));
    groupListLabel.setBounds(323, 197, 229, 17);
    contentPanel.add(groupListLabel);

    table.addMouseListener(new java.awt.event.MouseAdapter() {
      @Override
      public void mouseClicked(java.awt.event.MouseEvent evt) {
        int columnIndex = table.getSelectedColumn();
        int rowIndex = table.getSelectedRow();
        if (rowIndex >= 0) {
          flow = (Flow) table.getModel().getValueAt(rowIndex, columnIndex);
          LOGGER.debug("selected flow:" + flow);

          resetComponents(editButton, deleteButton, textField, groupInFlowTextArea);

          try {
            FormInitializer.initFlowGroupTextArea(groupInFlowTextArea, flow);

          } catch (CommandException ex) {
            LOGGER.error(ex.getCause().getMessage(), ex);
            JOptionPane.showMessageDialog(getContentPane(), ex.getCause().getMessage());
          } finally {
            textField.setText(flow.getName());
            editButton.setVisible(true);
            deleteButton.setVisible(true);
          }
        }
      }
    });

    JPanel buttonPane = new JPanel();
    buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
    getContentPane().add(buttonPane, BorderLayout.SOUTH);

    JButton okButton = new JButton("OK");
    okButton.setFont(new Font(FONT_NAME, Font.PLAIN, 16));
    okButton.setActionCommand("OK");
    buttonPane.add(okButton);
    getRootPane().setDefaultButton(okButton);

    okButton.addActionListener(e -> dispose());
  }

  private void resetComponents(JButton editButton, JButton deleteButton, JTextField textField,
      JTextArea groupInFlowTextArea) {
    editButton.setVisible(false);
    deleteButton.setVisible(false);
    textField.setText("");
    groupInFlowTextArea.setVisible(false);
    groupInFlowTextArea.setText("");
  }

  private boolean isTableHasNotAlikeValue(JTable table, String nameFlow) {
    int colCount = table.getColumnCount();
    int rowCount = table.getRowCount();
    for (int column = 0; column < colCount; column++) {
      for (int row = 0; row < rowCount; row++) {
        Flow value = (Flow) table.getValueAt(row, column);
        if (value.getName().equals(nameFlow)) {
          LOGGER.warn("try to duplicate nameFlow: {}", nameFlow);
          JOptionPane.showMessageDialog(getContentPane(), "Поток с таким именем уже есть");
          return false;
        }
      }
    }
    return true;
  }
}
