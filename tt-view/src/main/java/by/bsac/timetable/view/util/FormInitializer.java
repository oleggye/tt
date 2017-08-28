package by.bsac.timetable.view.util;

import java.awt.Color;
import java.awt.GridLayout;
import java.util.Date;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.border.MatteBorder;
import javax.swing.table.DefaultTableModel;

import by.bsac.timetable.command.exception.CommandException;
import by.bsac.timetable.command.util.CommandFacade;
import by.bsac.timetable.entity.Chair;
import by.bsac.timetable.entity.Classroom;
import by.bsac.timetable.entity.Faculty;
import by.bsac.timetable.entity.Flow;
import by.bsac.timetable.entity.Group;
import by.bsac.timetable.entity.IName;
import by.bsac.timetable.entity.Lecturer;
import by.bsac.timetable.entity.Record;
import by.bsac.timetable.entity.Subject;
import by.bsac.timetable.service.exception.ServiceException;
import by.bsac.timetable.service.exception.ServiceValidationException;
import by.bsac.timetable.util.SupportClass;
import by.bsac.timetable.util.VerticalLabelUI;
import components.MyComboBoxModel;
import components.OneColumnTableModel;
import tableClasses.TablesArray;

public final class FormInitializer {

  private FormInitializer() {}

  public static void initMainForm(JComboBox<Faculty> facultyComboBox,
      JComboBox<Group> groupComboBox, JLabel progressBarLbl, JProgressBar progressBar,
      byte educationLevel) throws CommandException {

    progressBarLbl.setText("Инициализация факультетов");
    progressBar.setValue(30);
    try {
      initFacultyComboBox(facultyComboBox);

      progressBar.setValue(60);

      progressBarLbl.setText("Инициализация групп");
      initGroupComboBox(facultyComboBox, groupComboBox, educationLevel);
      progressBar.setValue(100);
      progressBarLbl.setText("Завершено");

      progressBar.setVisible(false);
      progressBarLbl.setVisible(false);

    } catch (CommandException e) {
      progressBar.setValue(0);
      progressBarLbl.setText("Ошибка при инициализации");
      StringBuilder builder = new StringBuilder();
      builder.append("Ошибка при инициализации: ");
      builder.append("Приложение завершилось с ошибкой");
      builder.append(e.getMessage());
      throw new CommandException(builder.toString(), e);
    }

  }

  /**
   * Инициализация ComboBox строками с названиями факультетов
   *
   * @exception при ошибке SQL-запроса
   * @throws ServiceException
   */
  public static void initFacultyComboBox(JComboBox<Faculty> facultyComboBox)
      throws CommandException {

    //// HibernateUtil.getSession();
    try {
      List<Faculty> facultyList = CommandFacade.getFacultyList();

      if (!facultyList.isEmpty()) {
        DefaultComboBoxModel<Faculty> model = new MyComboBoxModel<>(facultyList);
        facultyComboBox.setModel(model);
      }
    } finally {
      //// HibernateUtil.closeSession();
    }
  }

  /**
   * Инициализация ComboBox строками с названиями кафедр
   *
   * @exception при ошибке SQL-запроса
   * @throws ServiceException
   */
  public static void initChairComboBox(JComboBox<Chair> chairComboBox) throws CommandException {

    //// HibernateUtil.getSession();
    try {
      List<Chair> chairList = CommandFacade.getChairList();

      if (!chairList.isEmpty()) {
        DefaultComboBoxModel<Chair> model = new MyComboBoxModel<>(chairList);
        chairComboBox.setModel(model);
      }
    } finally {
      //// HibernateUtil.closeSession();
    }
  }

  /**
   * Method gets the list of {@link Subject} instances and inits JComboBox param
   * 
   * @param subjectComboBox
   * @param chairComboBox
   * @throws ServiceException
   * @throws ServiceValidationException
   */
  public static void initSubjectComboBox(JComboBox<Subject> subjectComboBox,
      JComboBox<Chair> chairComboBox, byte educationLevel) throws CommandException {

    //// HibernateUtil.getSession();
    Chair selectedChair = (Chair) chairComboBox.getSelectedItem();
    try {
      List<Subject> subjectList =
          CommandFacade.getSubjectListByChairAndEduLevel(selectedChair, educationLevel);

      subjectComboBox.removeAllItems();

      if (!subjectList.isEmpty()) {
        DefaultComboBoxModel<Subject> model = new MyComboBoxModel<>(subjectList);
        subjectComboBox.setModel(model);
      }
    } finally {
      //// HibernateUtil.closeSession();
    }
  }

  /**
   * Method gets the list of {@link Lecturer} instances and inits JComboBox param
   * 
   * @param lecturerComboBox
   * @param chairComboBox
   * @throws ServiceException
   * @throws ServiceValidationException
   */
  public static void initLecturerComboBox(JComboBox<Lecturer> lecturerComboBox,
      JComboBox<Chair> chairComboBox) throws CommandException {

    //// HibernateUtil.getSession();

    Chair chair = (Chair) chairComboBox.getSelectedItem();
    try {
      List<Lecturer> lecturerList = CommandFacade.getLecturerListByChair(chair);

      lecturerComboBox.removeAllItems();
      if (!lecturerList.isEmpty()) {
        DefaultComboBoxModel<Lecturer> model = new MyComboBoxModel<>(lecturerList);
        lecturerComboBox.setModel(model);
      }
    } finally {
      //// HibernateUtil.closeSession();
    }
  }

  /**
   * Method gets the list of {@link Lecturer} instances and inits JComboBox param
   * 
   * @param lecturerComboBox
   * @param chair
   * @throws ServiceException
   * @throws ServiceValidationException
   */
  public static void initLecturerComboBox(JComboBox<Lecturer> lecturerComboBox, Chair chair)
      throws CommandException {

    //// HibernateUtil.getSession();
    try {
      List<Lecturer> lecturerList = CommandFacade.getLecturerListByChair(chair);

      lecturerComboBox.removeAllItems();
      if (!lecturerList.isEmpty()) {
        DefaultComboBoxModel<Lecturer> model = new MyComboBoxModel<>(lecturerList);
        lecturerComboBox.setModel(model);
      }
    } finally {
      //// HibernateUtil.closeSession();
    }
  }

  /**
   * Method gets the list of {@link Classroom} instances and inits JComboBox param
   * 
   * @param lecturerComboBox
   * @param chairComboBox
   * @throws ServiceException
   */
  public static void initClassroomComboBox(JComboBox<Classroom> classroomComboBox)
      throws CommandException {

    // HibernateUtil.getSession();
    try {
      List<Classroom> classroomList = CommandFacade.getClassroomList();

      DefaultComboBoxModel<Classroom> model = new MyComboBoxModel<>(classroomList);
      classroomComboBox.setModel(model);
    } finally {
      // HibernateUtil.closeSession();
      if (classroomComboBox.getItemCount() > 0) {
        classroomComboBox.setSelectedIndex(0);
      }
    }
  }

  /**
   * Инициализация ComboBox строками с названиями потоков
   *
   * @exception при ошибке SQL-запроса
   * @throws ServiceException
   */
  public static void initFlowComboBox(JComboBox<Flow> flowComboBox) throws CommandException {

    // HibernateUtil.getSession();
    try {
      List<Flow> flowList = CommandFacade.getFlowList();

      if (!flowList.isEmpty()) {
        DefaultComboBoxModel<Flow> model = new MyComboBoxModel<>(flowList);
        flowComboBox.setModel(model);
      }
    } finally {
      // HibernateUtil.closeSession();
    }
  }

  /**
   * Инициализация ComboBox строками с названиями групп, в зависимости от выбранного факультета
   *
   * @exception при ошибке SQL-запроса
   * @throws ServiceException
   * @see ServiceException
   */
  public static void initGroupComboBox(JComboBox<Faculty> facultyComboBox,
      JComboBox<Group> groupComboBox, byte educationLevel) throws CommandException {

    // HibernateUtil.getSession();
    Faculty selectedFaculty = (Faculty) facultyComboBox.getSelectedItem();

    try {
      List<Group> groupList =
          CommandFacade.getGroupListByFacultyAndEduLevel(selectedFaculty, educationLevel);
      groupComboBox.removeAllItems();

      if (!groupList.isEmpty()) {
        DefaultComboBoxModel<Group> model = new MyComboBoxModel<>(groupList);
        groupComboBox.setModel(model);
      }
    } finally {
      // HibernateUtil.closeSession();
    }
  }

  /**
   * Получаем коллекцию с расписанием для выбранной группы и заполняем записями таблицу
   * 
   * @throws ServiceValidationException
   *
   * @exception при ошибке SQL-запроса
   * @see ServiceException
   */
  public static void getGroupTimeTable(JComboBox<Group> groupComboBox, TablesArray tableArray,
      Date referenceDate) throws CommandException {
    // HibernateUtil.getSession();

    try {
      if (groupComboBox.getSelectedIndex() >= 0) {

        Group group = (Group) groupComboBox.getSelectedItem();
        List<Record> recordList = CommandFacade.getTimetableForGroup(group, referenceDate);

        if (!recordList.isEmpty()) {
          SupportClass.setModelsForTables(recordList, tableArray);
        }

        // } else if (!someFlag) {
      } else
        JOptionPane.showMessageDialog(null, "Пожалуйста, выберите группу");
    } finally {
      // HibernateUtil.closeSession();
    }

    // someFlag = true;
    // } else {
    // someFlag = false;
    // }
  }

  public static void initLeftPanel(JScrollPane scrollPane) {
    // задаем дни недели в метках
    // leftPanel.add(addDayOfWeekTitlesToPanel()
    JPanel leftPanel = new JPanel();
    leftPanel.setBorder(new MatteBorder(0, 0, 0, 1, (Color) new Color(0, 0, 0)));
    leftPanel.setLayout(new GridLayout(7, 1, 0, 0));
    JLabel[] jLbls = new JLabel[] {new JLabel("Понедельник"), new JLabel("Вторник"),
        new JLabel("Среда"), new JLabel("Четверг"), new JLabel("Пятница"), new JLabel("Суббота"),
        new JLabel("Воскресенье")};
    for (JLabel lbl : jLbls) {
      lbl.setUI(new VerticalLabelUI(false));// задаем вертикальное
      // отображение для текста в метках
      lbl.setHorizontalAlignment(SwingConstants.CENTER);// задаем
      // горизонтальное выравнивание по центру
      leftPanel.add(lbl);
    }
    scrollPane.setRowHeaderView(leftPanel);
  }

  /**
   * Получаем коллецию всех факультетов, добавляем в модель и задаем её переданной таблице
   * 
   * @param table
   * @throws ServiceException
   */
  public static void initFacultyTable(JTable table) throws CommandException {
    // HibernateUtil.getSession();
    try {
      List<Faculty> facultyList = CommandFacade.getFacultyList();

      initTable(table, facultyList, Faculty.class, "Список факультетов");
    } finally {
      // HibernateUtil.closeSession();
    }
  }

  /**
   * Получаем коллецию всех кафедр, добавляем в модель и задаем её переданной таблице
   * 
   * @param table
   * @throws ServiceException
   */
  public static void initChairTable(JTable table) throws CommandException {
    // HibernateUtil.getSession();
    try {
      List<Chair> chairList = CommandFacade.getChairList();

      initTable(table, chairList, Chair.class, "Список кафедр");
    } finally {
      // HibernateUtil.closeSession();
    }
  }

  /**
   * Получаем аудиторий всех кафедр, добавляем в модель и задаем её переданной таблице
   * 
   * @param table
   * @throws ServiceException
   */
  public static void initClassroomTable(JTable table) throws CommandException {
    // HibernateUtil.getSession();
    try {
      List<Classroom> classroomList = CommandFacade.getClassroomList();

      initTable(table, classroomList, Classroom.class, "Список аудиторий");
    } finally {
      // HibernateUtil.closeSession();
    }
  }

  /**
   * Получаем коллецию всех потоков, добавляем в модель и задаем её переданной таблице
   * 
   * @param table
   * @throws ServiceException
   */
  public static void initFlowTable(JTable table) throws CommandException {
    // HibernateUtil.getSession();
    try {
      List<Flow> flowList = CommandFacade.getFlowList();

      initTable(table, flowList, Flow.class, "Список потоков");
    } finally {
      // HibernateUtil.closeSession();
    }
  }

  /**
   * Получаем коллецию всех групп факультета, добавляем в модель и задаем её переданной таблице
   * 
   * @param table
   * @throws ServiceException
   */
  public static void initGroupTable(JTable table, Faculty faculty, byte educationLevel)
      throws CommandException {
    // HibernateUtil.getSession();
    try {
      List<Group> groupList =
          CommandFacade.getGroupListByFacultyAndEduLevel(faculty, educationLevel);

      DefaultTableModel tModel =
          new OneColumnTableModel<Group>(Group.class, groupList, "Список групп");
      table.setModel(tModel);
      SupportClass.setHorizontalAlignmentToTable(table);

    } finally {
      // HibernateUtil.closeSession();
    }
  }

  /**
   * Получаем коллецию всех преподавателей кафедры, добавляем в модель и задаем её переданной
   * таблице
   * 
   * @param table
   * @throws ServiceException
   * @throws ServiceValidationException
   */
  public static void initLecturerTable(JTable table, Chair chair) throws CommandException {
    // HibernateUtil.getSession();
    try {
      List<Lecturer> lecturerList = CommandFacade.getLecturerListByChair(chair);

      DefaultTableModel tModel =
          new OneColumnTableModel<Lecturer>(Lecturer.class, lecturerList, "Список преподавателей");
      table.setModel(tModel);
      SupportClass.setHorizontalAlignmentToTable(table);
    } finally {
      // HibernateUtil.closeSession();
    }
  }

  /**
   * Получаем коллецию всех преподавателей кафедры, добавляем в модель и задаем её переданной
   * таблице
   * 
   * @param table
   * @throws ServiceException
   * @throws ServiceValidationException
   */
  public static void initSubjectTable(JTable table, Chair chair, byte educationLevel)
      throws CommandException {
    // HibernateUtil.getSession();
    try {
      List<Subject> subjectList =
          CommandFacade.getSubjectListByChairAndEduLevel(chair, educationLevel);

      DefaultTableModel tModel =
          new OneColumnTableModel<Subject>(Subject.class, subjectList, "Список дисциплин");
      table.setModel(tModel);
      SupportClass.setHorizontalAlignmentToTable(table);
    } finally {
      // HibernateUtil.closeSession();
    }
  }

  /**
   * Generic-метод который задает модель составленной из списка переданной таблице
   * 
   * @param table
   * @throws ServiceException
   */
  private static <E> void initTable(JTable table, List<? extends IName> list, Class<E> clazz,
      String tableTitle) {

    if (!list.isEmpty()) {

      DefaultTableModel tModel = new OneColumnTableModel<E>(clazz, list, tableTitle);
      table.setModel(tModel);
      SupportClass.setHorizontalAlignmentToTable(table);
    }
  }

  /**
   * Инициализирует преденный компонент списком групп переданного потока
   * 
   * @param groupInFlowTextArea
   * @param flow
   * @throws ServiceException
   * @throws ServiceValidationException
   */
  public static void initFlowGroupTextArea(JTextArea groupInFlowTextArea, Flow flow)
      throws CommandException {
    // HibernateUtil.getSession();
    try {
      List<Group> groupList = CommandFacade.getGroupListByFlow(flow);

      if (!groupList.isEmpty()) {

        for (int index = 0; index < groupList.size(); index++) {
          groupInFlowTextArea.append(groupList.get(index).getName());
          if (index != groupList.size() - 1) {
            groupInFlowTextArea.append("\n");
          }
        }
        groupInFlowTextArea.setVisible(true);
      }
    } finally {
      // HibernateUtil.closeSession();
    }
  }

  public static <E> void getAllComparedRecordList(Class<?> clazz, String name, StringBuilder result)
      throws CommandException {

    List<?> resultList = null;

    if (clazz.equals(Lecturer.class)) {
      resultList = CommandFacade.getLecturerListByName(name);
    } else {
      if (clazz.equals(Subject.class)) {
        resultList = CommandFacade.getSubjectListByName(name);
      } else {
        if (clazz.equals(Group.class)) {
          resultList = CommandFacade.getGroupListByName(name);
        } else {
          if (clazz.equals(Flow.class)) {
            resultList = CommandFacade.getFlowListByName(name);
          }
        }
      }
    }
    for (Object elem : resultList) {
      String coincidenceName = ((IName) elem).getName();
      result.append(coincidenceName);
      result.append("\n");
    }
  }
}
