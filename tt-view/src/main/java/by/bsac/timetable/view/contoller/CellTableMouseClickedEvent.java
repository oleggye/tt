package by.bsac.timetable.view.contoller;

import java.awt.Cursor;
import java.awt.event.MouseEvent;
import java.util.Date;
import java.util.List;

import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jdatepicker.impl.JDatePickerImpl;

import by.bsac.timetable.command.CommandProvider;
import by.bsac.timetable.command.ICommand;
import by.bsac.timetable.command.exception.CommandException;
import by.bsac.timetable.command.util.Request;
import by.bsac.timetable.entity.Group;
import by.bsac.timetable.entity.Record;
import by.bsac.timetable.util.ActionMode;
import by.bsac.timetable.util.DateUtil;
import by.bsac.timetable.util.SupportClass;
import by.bsac.timetable.view.AddNewRecordForm;
import by.bsac.timetable.view.MainForm;
import by.bsac.timetable.view.UpdateOrCancelForm;
import tableClasses.ArrayPosition;
import tableClasses.MyMultiSpanCellTable;
import tableClasses.TablesArray;

public class CellTableMouseClickedEvent extends java.awt.event.MouseAdapter {

	private static final Logger LOGGER = LogManager.getLogger(CellTableMouseClickedEvent.class.getName());

	private MainForm mainForm;
	private TablesArray tableArray;
	private JComboBox<Group> groupComboBox;
	private JDatePickerImpl datePicker;

	public CellTableMouseClickedEvent(MainForm mainForm, TablesArray tableArray, JComboBox<Group> groupComboBox,
			JDatePickerImpl datePicker) {
		this.mainForm = mainForm;
		this.tableArray = tableArray;
		this.groupComboBox = groupComboBox;
		this.datePicker = datePicker;
	}

	@Override
	public void mouseClicked(MouseEvent event) {

		if (mainForm.getIsGroupSelected()) {
			mainForm.getMainFrame().setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

			MyMultiSpanCellTable table = (MyMultiSpanCellTable) event.getSource();

			// получаем положение (координаты) выделенной строки таблицы
			int row = table.rowAtPoint(event.getPoint());
			LOGGER.debug("selected row:" + row);
			int column = table.columnAtPoint(event.getPoint());
			LOGGER.debug("column:" + column);

			if (column == 0)
				return;

			Object value = table.getValueAt(row, column);
			LOGGER.debug("selected value:" + value);

			// Получаем положение таблицы в массиве
			ArrayPosition aPosition = tableArray.getElementPosition(table);
			LOGGER.debug("aPosition: " + aPosition);

			byte selectedWeekNumber = (byte) (aPosition.getRowIndex() + 1);
			byte selectedLessonOrdinalNumber = (byte) (table.getSelectedRow() + 1);
			byte selectedWeekDay = (byte) (aPosition.getColIndex() + 1);
			Group group = (Group) groupComboBox.getSelectedItem();

			Date referenceDate = (Date) datePicker.getModel().getValue();
			Date lessonDate = DateUtil.getDateByRefDateAndWeekNumberAndDay(referenceDate, selectedWeekNumber,
					selectedWeekDay);

			if (value != null) {
				Record record = (Record) value;

				invokeUpdateOrCancelForm(mainForm.getMainFrame(), lessonDate, group, record, selectedWeekNumber,
						selectedWeekDay, selectedLessonOrdinalNumber);
				mainForm.getMainFrame().setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			} else {
				LOGGER.debug("It isn't a record:");
				invokeAddNewRecordForm(mainForm.getMainFrame(), lessonDate, group, selectedWeekNumber, selectedWeekDay,
						selectedLessonOrdinalNumber);
				mainForm.getMainFrame().setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
			table.clearSelection(); // убираем выделение

			mainForm.getMainFrame().setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
			try {
				ICommand command = CommandProvider.getInstance().getCommand(ActionMode.Get_Timetable_For_Group);
				Request request = new Request();
				request.putParam("group", group);
				request.putParam("referenceDate", referenceDate);
				command.execute(request);

				List<Record> recordList = (List<Record>) request.getValue("recordList");

				if (!recordList.isEmpty()) {
					SupportClass.setModelsForTables(recordList, tableArray);
				}

			} catch (CommandException ex) {
				LOGGER.error(ex.getCause().getMessage(), ex);
				JOptionPane.showMessageDialog(mainForm.getMainFrame().getContentPane(), ex.getCause().getMessage());
			} finally {
				mainForm.getMainFrame().setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
		}
	}

	private void invokeUpdateOrCancelForm(JFrame parent, Date lessonDate, Group group, Record record, byte weekNumber,
			byte weekDay, byte lessonOrdinalNumber) {
		UpdateOrCancelForm updateOrCancelForm = new UpdateOrCancelForm(parent, lessonDate, group, record, weekNumber,
				weekDay, lessonOrdinalNumber);
		updateOrCancelForm.setModal(true);
		updateOrCancelForm.setVisible(true);
	}

	private void invokeAddNewRecordForm(JFrame parent, Date lessonDate, Group group, byte weekNumber, byte weekDay,
			byte lessonOrdinalNumber) {
		AddNewRecordForm addNewRecord = new AddNewRecordForm(parent, lessonDate, group, weekNumber, weekDay,
				lessonOrdinalNumber);
		addNewRecord.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		addNewRecord.setModal(true);
		addNewRecord.setVisible(true);
	}
}