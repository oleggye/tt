package components;

import java.awt.event.MouseEvent;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;

import by.bsac.timetable.entity.IName;

public class OneColumnTable extends JTable {

  private static final long serialVersionUID = 1L;

  private DefaultTableCellRenderer renderer;

  public OneColumnTable() {
    renderer = new OneColumnTableCellRenderer();
    super.setDefaultRenderer(Object.class, renderer);
  }

  @Override
  public TableCellRenderer getCellRenderer(int row, int column) {
    return renderer;
  }

  public String getToolTipText(MouseEvent e) {
    String tip = null;
    java.awt.Point p = e.getPoint();
    int rowIndex = rowAtPoint(p);
    int colIndex = columnAtPoint(p);
    int realColumnIndex = convertColumnIndexToModel(colIndex);

    if (realColumnIndex == 0) {
      Object value = getValueAt(rowIndex, colIndex);
      if (value instanceof IName) {
        tip = ((IName) value).getName();
      } else {
        value.toString();
      }
    }
    return tip;
  }
}
