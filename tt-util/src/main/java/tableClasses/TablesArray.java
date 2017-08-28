package tableClasses;

import java.util.List;

import javax.swing.JPanel;

import by.bsac.timetable.entity.Record;
import tableClasses.ArrayPosition;

public class TablesArray {

	private MyMultiSpanCellTable[][] tablesArray = null;
	private int width = 0;
	private int height = 0;

	public TablesArray() {
		this.tablesArray = new MyMultiSpanCellTable[0][0];
		this.width = 0;
		this.height = 0;
	}

	public TablesArray(int width, int height) {
		this.tablesArray = new MyMultiSpanCellTable[width][height];
		this.width = width;
		this.height = height;
	}

	/**
	 * задаем дефолтную модель для таблицы Method adds new instances of
	 * {@link MyMultiSpanCellTable} to {@link #tablesArray}, invokes
	 * {@link #MyMultiSpanCellTable.setDefaultAttributiveCellTableModel()} for
	 * each and adds it to {@link JPanel}
	 * 
	 * @param list
	 * @param centralPanel
	 */
	public void initArray(List<Record> list, JPanel centralPanel) {
		for (MyMultiSpanCellTable[] col : tablesArray) {
			for (int i = 0; i < col.length; i++) {
				col[i] = new MyMultiSpanCellTable();
				col[i].setDefaultAttributiveCellTableModel();
				centralPanel.add(col[i]);
			}
		}
	}

	/**
	 * задаем дефолтную модель для таблицы Method adds new instances of
	 * {@link MyMultiSpanCellTable} to {@link #tablesArray}, invokes
	 * {@link #MyMultiSpanCellTable.setDefaultAttributiveCellTableModel()}, adds
	 * {@link #MouseListener} for each and adds it to {@link JPanel}
	 * 
	 * @param list
	 * @param centralPanel
	 * @param listener
	 */
	public void initArray(List<Record> list, JPanel centralPanel, java.awt.event.MouseListener listener) {
		for (MyMultiSpanCellTable[] col : tablesArray) {
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
	 * 
	 * @param table
	 * @return
	 */
	public ArrayPosition getElementPosition(MyMultiSpanCellTable table) {

		ArrayPosition result = null;
		for (int i = 0; i < this.getWidth(); i++) {
			for (int j = 0; j < this.getHeight(); j++) {
				if (this.getElementAt(i, j).equals(table)) {
					result = new ArrayPosition(i, j);
					i = this.width;
					break;
				}
			}
		}
		return result;
	}

	public MyMultiSpanCellTable getElementAt(int x, int y) {
		return this.tablesArray[x][y];
	}

	public void setElementAt(int x, int y, MyMultiSpanCellTable table) {
		this.tablesArray[x][y] = table;
	}

	/**
	 * Method cleans all data from tables in the array
	 */
	public void resetAllTablesInArray() {
		for (MyMultiSpanCellTable[] e : this.tablesArray) {
			for (MyMultiSpanCellTable ee : e) {
				ee.setDefaultAttributiveCellTableModel();
			}
		}
	}
}