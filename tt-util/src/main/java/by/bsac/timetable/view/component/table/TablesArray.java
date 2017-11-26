package by.bsac.timetable.view.component.table;

import java.awt.Color;
import java.awt.event.MouseListener;
import javax.swing.JPanel;

public class TablesArray {

  private MyMultiSpanCellTable[][] array = null;
  private int width = 0;
  private int height = 0;

  public TablesArray() {
    this.array = new MyMultiSpanCellTable[0][0];
    this.width = 0;
    this.height = 0;
  }

  public TablesArray(int width, int height) {
    this.array = new MyMultiSpanCellTable[width][height];
    this.width = width;
    this.height = height;
  }

  /**
   * задаем дефолтную модель для таблицы Method adds new instances of
   * {@link MyMultiSpanCellTable} to {@link #array}, invokes
   * {@link MyMultiSpanCellTable#setDefaultAttributiveCellTableModel()} for
   * each and adds it to {@link JPanel}
   */
  public void initArray(JPanel centralPanel) {
    for (MyMultiSpanCellTable[] col : array) {
      for (int i = 0; i < col.length; i++) {
        col[i] = new MyMultiSpanCellTable();
        col[i].setDefaultAttributiveCellTableModel();
        centralPanel.add(col[i]);
      }
    }
  }

  /**
   * задаем дефолтную модель для таблицы Method adds new instances of
   * {@link MyMultiSpanCellTable} to {@link #array}, invokes
   * {@link MyMultiSpanCellTable#setDefaultAttributiveCellTableModel()}, adds
   * {@link MouseListener} for each and adds it to {@link JPanel}
   */
  public void initArray(JPanel centralPanel, java.awt.event.MouseListener listener) {
    for (MyMultiSpanCellTable[] col : array) {
      for (int i = 0; i < col.length; i++) {
        col[i] = new MyMultiSpanCellTable();
        col[i].setDefaultAttributiveCellTableModel();
        col[i].addMouseListener(listener);
        centralPanel.add(col[i]);
      }
    }
  }

  /**
   * @return width of the array
   */
  public int getWidth() {
    return this.width;
  }

  /**
   * @return height of the array
   */
  public int getHeight() {
    return this.height;
  }

  /**
   * Method gets {@link ArrayPosition} object with coordinates in the array of
   * the table or return null
   */
  public ArrayPosition getElementPosition(MyMultiSpanCellTable table) {

    ArrayPosition result = null;
    for (int i = 0; i < this.width; i++) {
      for (int j = 0; j < this.height; j++) {

        if (this.getElementAt(i, j).equals(table)) {
          return new ArrayPosition(i, j);
        }
      }
    }
    return result;
  }

  public MyMultiSpanCellTable getElementAt(int x, int y) {
    return this.array[x][y];
  }

  public void setElementAt(int x, int y, MyMultiSpanCellTable table) {
    this.array[x][y] = table;
  }

  /**
   * Method cleans all data from tables in the array
   */
  public void resetAllTablesInArray() {
    for (MyMultiSpanCellTable[] e : this.array) {
      for (MyMultiSpanCellTable ee : e) {
        ee.setDefaultAttributiveCellTableModel();
        ee.clearSelection();
        ee.setGridColor(new Color(122, 138, 153));
      }
    }
  }
}