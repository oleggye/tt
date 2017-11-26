/*
 * To change this license header, choose License Headers in Project Properties. To change this
 * template file, choose Tools | Templates and open the template in the editor.
 */
package by.bsac.timetable.view.component.table;

/**
 * Class represents position of a table in the array
 * 
 * @author hello
 */
public class ArrayPosition {

  private int colIndex;
  private int rowIndex;

  public ArrayPosition() {}

  public ArrayPosition(int colIndex, int rowIndex) {
    this.colIndex = colIndex;
    this.rowIndex = rowIndex;
  }

  /**
   * @return the colIndex
   */
  public int getColIndex() {
    return colIndex;
  }

  /**
   * @return the rowIndex
   */
  public int getRowIndex() {
    return rowIndex;
  }

  @Override
  public String toString() {
    return "colIndex: " + colIndex + ", rowIndex: " + rowIndex;
  }
}
