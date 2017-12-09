package by.bsac.timetable.view.util;

import by.bsac.timetable.command.exception.CommandException;
import by.bsac.timetable.command.util.CommandFacade;
import by.bsac.timetable.entity.Faculty;
import by.bsac.timetable.view.component.MyComboBoxModel;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;

public class ServiceEditFormInitializer {

  /**
   * Инициализация ComboBox строками с названиями факультетов
   *
   * @throws CommandException при ошибке SQL-запроса
   */
  public void initFacultyComboBox(JComboBox<Faculty> facultyComboBox) throws CommandException {
    List<Faculty> facultyList = CommandFacade.getFacultyList();

    if (!facultyList.isEmpty()) {
      DefaultComboBoxModel<Faculty> model = new MyComboBoxModel<>(facultyList);
      facultyComboBox.setModel(model);
    }
  }
}
