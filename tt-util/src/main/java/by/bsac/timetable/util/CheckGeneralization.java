package by.bsac.timetable.util;

import by.bsac.timetable.annotations.Legacy;
import java.util.List;
import javax.swing.JOptionPane;

@Legacy
public class CheckGeneralization<T> {

  public boolean checkRecordBeforeAdd(List<T> collection, T object) {
    for (T elem : collection) {
      if (elem.toString().equals(object.toString())) {
        JOptionPane.showMessageDialog(null, "Данная запись конфликтует с существующей");
        return false;
      }
    }
    return true;
  }
}
