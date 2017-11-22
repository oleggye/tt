package tableClasses;

import by.bsac.component.multiSpanCellTable.AttributiveCellTableModel;
import by.bsac.component.multiSpanCellTable.CellSpan;
import by.bsac.component.multiSpanCellTable.MultiSpanCellTable;
import by.bsac.timetable.entity.IName;
import by.bsac.timetable.entity.Record;
import by.bsac.timetable.util.SupportClass;
import java.awt.Font;
import java.awt.event.MouseEvent;
import java.util.List;

public class MyMultiSpanCellTable extends MultiSpanCellTable {

  private static final long serialVersionUID = 1L;

  public MyMultiSpanCellTable() {
    super();
    this.setFont(new Font("Tahoma", Font.BOLD, 14));
  }

  public MyMultiSpanCellTable(AttributiveCellTableModel model) {
    super(model);
    this.setFont(new Font("Tahoma", Font.BOLD, 14));
  }

  // добавленный метод к изначальному
  public void changeTableModel(Record record) {
    int[] rows = new int[]{record.getSubjOrdinalNumber()
        - 1}; /* т.к. в таблице индексы с 0 */
    int[] cols = SupportClass.getColsFromSubgroup(record.getSubjectFor().getId());

    AttributiveCellTableModel tableModel = (AttributiveCellTableModel) this
        .getModel();/* задаем переменную с моделью текущей таблицы */
    tableModel.setValueAt(record, rows[0], cols[0]);// задаем значение

		/*
     * т.е. если нужно объединять ячейки, т.к. в качестве подгруппы
		 * "Вся группа"
		 */
    if (cols.length > 1) {

      final CellSpan cellAttribute = (CellSpan) tableModel.getCellAttribute();
      cellAttribute.combine(rows, cols);
      this.revalidate();
      this.repaint();
    }
  }

  public void setDefaultAttributiveCellTableModel() {
    AttributiveCellTableModel tableModel = new AttributiveCellTableModel(
        new Object[][]{{1, null, null},
            {2, null, null}, {3, null, null}, {4, null, null}, {5, null, null}, {6, null, null},},
        new String[]{"№", "1 подгруппа", "2 подгруппа"});
    this.setModel(tableModel);

    this.getColumnModel().getColumn(0).setMaxWidth(10);
  }

  public void setModelForTable(List<Record> mainRecordsCollection, ArrayPosition arrPosition) {

    this.setDefaultAttributiveCellTableModel();// задаем дефолтную модель
    // для таблицы
    for (Record e : mainRecordsCollection) {
      if (e.getWeekDay() == (arrPosition.getColIndex() + 1)
          && e.getWeekNumber() == (arrPosition.getRowIndex() + 1)) {

        this.changeTableModel(e);
      }
    }
  }

  @Override
  public String getToolTipText(MouseEvent event) {
    String tip;
    java.awt.Point p = event.getPoint();
    int rowIndex = rowAtPoint(p);
    int colIndex = columnAtPoint(p);
    int realColumnIndex = convertColumnIndexToModel(colIndex);

    Object value = getValueAt(rowIndex, colIndex);

    if ((realColumnIndex == 1 || realColumnIndex == 2) && value != null) {
      if (value instanceof IName) {
        if (value instanceof Record) {
          tip = SupportClass.makeTipForRecord((Record) value);
        } else {
          tip = ((IName) value).getName();
        }
      } else {
        tip = value.toString();
      }
    } else {
      tip = super.getToolTipText(event);
    }
    return tip;
  }
}