package by.bsac.timetable.view.extra.controller;

import by.bsac.timetable.command.exception.CommandException;
import by.bsac.timetable.entity.IName;
import by.bsac.timetable.view.util.FormInitializer;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CoincidenceTextFieldController implements KeyListener {

  private static final Logger LOGGER = LoggerFactory
      .getLogger(CoincidenceTextFieldController.class.getName());

  private JDialog frame;
  private JTextArea coincidenceTextArea;
  private JTextField textField;
  private JScrollPane jPane;
  private JLabel coincidenceLabel;
  private Class<? extends IName> clazz;

  public CoincidenceTextFieldController(JDialog frame, Class<? extends IName> clazz,
      JTextArea coincidenceTextArea, JTextField textField, JScrollPane jPane,
      JLabel coincidenceLabel) {
    this.frame = frame;
    this.clazz = clazz;
    this.coincidenceTextArea = coincidenceTextArea;
    this.textField = textField;
    this.jPane = jPane;
    this.coincidenceLabel = coincidenceLabel;
  }

  @Override
  public void keyTyped(KeyEvent e) {

  }

  @Override
  public void keyPressed(KeyEvent e) {
    final int BACKSPACE_BUTTON_CODE = 8;
    if (e.getKeyCode() == BACKSPACE_BUTTON_CODE) {
      jPane.setVisible(false);
      coincidenceLabel.setVisible(false);
      if (textField.getText().length() == 1) {
        textField.setText("");
      }
      keyReleased(e);
    }

  }

  @Override
  public void keyReleased(KeyEvent e) {
    if (!textField.getText().isEmpty()) {

      String name = textField.getText();

      final String tip = getAllComparedRecords(name);
      if (!tip.isEmpty()) {
        jPane.setVisible(true);
        coincidenceLabel.setVisible(true);
        coincidenceTextArea.setText(tip.trim());
      } else {
        jPane.setVisible(false);
        coincidenceLabel.setVisible(false);
      }
    }
  }

  private String getAllComparedRecords(String comparableString) {
    String result = "";
    try {
      result = FormInitializer.getAllComparedRecordList(clazz, comparableString);

    } catch (CommandException ex) {
      LOGGER.error(ex.getCause().getMessage(), ex);
      JOptionPane.showMessageDialog(frame.getContentPane(), ex.getCause().getMessage());
    }
    return result;
  }
}
