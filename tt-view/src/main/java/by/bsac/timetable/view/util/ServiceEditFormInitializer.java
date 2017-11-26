package by.bsac.timetable.view.util;

import by.bsac.timetable.command.exception.CommandException;
import by.bsac.timetable.command.util.CommandFacade;
import by.bsac.timetable.entity.Faculty;
import by.bsac.timetable.service.exception.ServiceException;
import by.bsac.timetable.view.component.MyComboBoxModel;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JFrame;

public class ServiceEditFormInitializer {
  private JFrame frame;

  public ServiceEditFormInitializer(JFrame frame) {
    this.frame = frame;
  }

  /**
   * Инициализация ComboBox строками с названиями факультетов
   *
   * @exception при ошибке SQL-запроса
   * @throws ServiceException
   */
  public void initFacultyComboBox(JComboBox<Faculty> facultyComboBox) throws CommandException {

    // HibernateUtil.getSession();
    try {

      List<Faculty> facultyList = CommandFacade.getFacultyList();

      if (!facultyList.isEmpty()) {
        DefaultComboBoxModel<Faculty> model = new MyComboBoxModel<>(facultyList);
        facultyComboBox.setModel(model);
      }
    } finally {
      // HibernateUtil.closeSession();
    }
  }
}
