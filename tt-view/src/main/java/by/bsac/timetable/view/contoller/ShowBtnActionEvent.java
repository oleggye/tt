package by.bsac.timetable.view.contoller;

import by.bsac.timetable.command.exception.CommandException;
import by.bsac.timetable.command.util.CommandFacade;
import by.bsac.timetable.entity.Group;
import by.bsac.timetable.entity.Record;
import by.bsac.timetable.util.DateUtil;
import by.bsac.timetable.util.SupportClass;
import by.bsac.timetable.view.MainForm;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jdatepicker.impl.JDatePickerImpl;
import tableClasses.TablesArray;

public class ShowBtnActionEvent implements ActionListener {

  private static final Logger LOGGER = LogManager.getLogger(MainForm.class.getName());

  private MainForm mainForm;
  private TablesArray tableArray;
  private JDatePickerImpl datePicker;
  private JComboBox<Group> groupComboBox;

  public ShowBtnActionEvent(MainForm mainForm, TablesArray tableArray, JDatePickerImpl datePicker,
      JComboBox<Group> groupComboBox) {
    this.tableArray = tableArray;
    this.datePicker = datePicker;
    this.groupComboBox = groupComboBox;
    this.mainForm = mainForm;
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    JButton showRecordsButton = (JButton) e.getSource();
    showRecordsButton.setEnabled(false);
    tableArray.resetAllTablesInArray();
    Group group = (Group) groupComboBox.getSelectedItem();
    try {
      Date referenceDate = (Date) datePicker.getModel().getValue();

      List<Record> recordList = CommandFacade.getTimetableForGroup(group, referenceDate);

      if (!recordList.isEmpty()) {
        SupportClass.setModelsForTables(recordList, tableArray);
      }
      selectTableByReferenceDate(referenceDate);
      mainForm.setIsGroupSelected(true);

    } catch (CommandException ex) {
      LOGGER.error(ex.getCause().getMessage(), ex);
      JOptionPane.showMessageDialog(mainForm.getMainFrame().getContentPane(),
          ex.getCause().getMessage());
      mainForm.setIsGroupSelected(false);
    } finally {
      showRecordsButton.setEnabled(true);
    }
  }

  private void selectTableByReferenceDate(Date referenceDate) {
    int weekDay = DateUtil.getWeekDay(referenceDate);
    int weekNumber = DateUtil.getWeekNumber(referenceDate);
    JTable table = tableArray.getElementAt(weekDay - 1, weekNumber - 1);
    table.setGridColor(Color.red);
  }
}
