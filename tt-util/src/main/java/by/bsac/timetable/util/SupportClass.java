package by.bsac.timetable.util;

import by.bsac.timetable.entity.Group;
import by.bsac.timetable.entity.Lecturer;
import by.bsac.timetable.entity.Record;
import by.bsac.timetable.entity.Subject;
import by.bsac.timetable.entity.SubjectFor;
import by.bsac.timetable.view.component.table.ArrayPosition;
import by.bsac.timetable.view.component.table.MyMultiSpanCellTable;
import by.bsac.timetable.view.component.table.TablesArray;
import java.awt.Color;
import java.awt.Window;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SupportClass {

  private SupportClass() {}

  private static final Logger LOGGER = LoggerFactory.getLogger(SupportClass.class);

  private static final String RECORD_CONFLICT_MESSAGE = "Данная запись конфликтует с существующей";
  private static final String RECORD_TIP_CONSTANT = "%s(%s), %s [%s]";

  private static final int LENGTHFORWORD = 10;
  private static final int LENGTHOFI = 1;
  private static final Color DEFAULT_GRID_COLOR = new Color(122, 138, 153);

  public static final void setColumnsWidth(JTable table) {

    table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
    int prefWidth;

    JTableHeader th = table.getTableHeader();
    for (int i = 0; i < table.getColumnCount(); i++) {
      TableColumn column = table.getColumnModel().getColumn(i);
      int prefWidthMax = 0;
      for (int j = 0; j < table.getRowCount(); j++) {
        try {
          String s = table.getModel().getValueAt(j, i).toString();
          prefWidth = Math.round((float) th.getFontMetrics(th.getFont())
              .getStringBounds(s, th.getGraphics()).getWidth());
          if (prefWidth > prefWidthMax) {
            prefWidthMax = prefWidth;
          }
        } catch (NullPointerException e) {
          LOGGER.warn("setColumnsWidth error", e);
          continue;
        }

      }
      column.setPreferredWidth(prefWidthMax + 10);
    }
  }

  public static final ArrayPosition findArrayPositionOfTable(JTable table, JTable[][] tablesArray) {

    ArrayPosition result = null;
    for (int i = 0; i < tablesArray.length; i++) {
      for (int j = 0; j < tablesArray[i].length; j++) {
        if (tablesArray[i][j].equals(table)) {
          result = new ArrayPosition(i, j);
          i = tablesArray.length;
          break;
        }
      }
    }
    return result;
  }

  public static final ArrayPosition findArrayPositionOfTable(JTable table,
      TablesArray tablesArray) {

    ArrayPosition result = null;
    for (int i = 0; i < tablesArray.getWidth(); i++) {
      for (int j = 0; j < tablesArray.getHeight(); j++) {
        if (tablesArray.getElementAt(i, j).equals(table)) {
          result = new ArrayPosition(i, j);
          i = tablesArray.getWidth();
          break;
        }
      }
    }
    return result;
  }

  public static void formCentering(Window frame) {
    /* получаем разрешение текущего экрана */
    java.awt.Dimension dim = frame.getToolkit().getScreenSize();
    frame.setLocation(dim.width / 2 - frame.getWidth() / 2, dim.height / 2 - frame.getHeight() / 2);
  }

  public static void relativeFormCentering(Window parent, Window frame) {
    frame.setLocationRelativeTo(parent);
  }

  public static List<Record> findMainRecordsByCriterias(List<Record> mainRecordsCollection,
      byte currentWeekDay, byte currentWeekNumber, byte currentSubjOrdinalNumber) {
    List<Record> tempArray = new LinkedList<>();

    for (Record f : mainRecordsCollection) {
      if ((f.getWeekDay() == currentWeekDay) && (f.getWeekNumber() == currentWeekNumber)
          && (f.getSubjOrdinalNumber() == currentSubjOrdinalNumber)) {
        tempArray.add(f);
      }
    }
    return tempArray;
  }

  public static void setModelsForTables(List<Record> mainRecordsCollection,
      TablesArray tablesArray) {
    for (int i = 0; i < tablesArray.getWidth(); i++) {
      for (int j = 0; j < tablesArray.getHeight(); j++) {

        ArrayPosition arrPos = new ArrayPosition(i, j);
        MyMultiSpanCellTable table = tablesArray.getElementAt(i, j);
        table.setModelForTable(mainRecordsCollection, arrPos);
        // устанавливаем цвет рамки
        table.setGridColor(DEFAULT_GRID_COLOR);

      }
    }
  }

  /**
   * <table border="1">
   * <caption>{@link SubjectFor} mapping</caption>
   * <tr>
   * <th>{@link SubjectFor#getId()}</th>
   * <th>description</th>
   * </tr>
   * <tr>
   * <td>1</td>
   * <td>FIRST_SUBGROUP</td>
   * </tr>
   * <tr>
   * <td>2</td>
   * <td>SECOND_SUBGROUP</td>
   * </tr>
   * <tr>
   * <td>3</td>
   * <td>FULL_GROUP</td>
   * </tr>
   * <tr>
   * <td>4</td>
   * <td>FULL_FLOW</td>
   * </tr>
   * </table>
   * 
   * @param subgroup
   * @return
   */
  public static int[] getColsFromSubgroup(byte subgroup) {
    // т.к. в таблице индексы с 0

    /*
     * начало костыля!!!! т.к. в БД subGroup 1 - 1-ая подгруппа, 2 - 2-ая подгруппа, 3 - Вся группа,
     * а в ComboBox item 0 - Вся группа, 1 - 1-ая подгруппа, 3 - 2-ая подгруппа, то делаем проверку
     * и замену.
     */
    /*
     * было до правки 18.10.2016 switch (subgroup) { case 1: return new int[]{1, 2}; case 2: return
     * new int[]{1}; case 3: return new int[]{2}; default: return new int[]{1}; }
     */
    switch (subgroup) {
      case 1:
        return new int[] {1};
      case 2:
        return new int[] {2};
      case 3:
        return new int[] {1, 2};
      case 4:
        return new int[] {1, 2};
      default:
        return new int[] {1};
    }
    /* конец костыля!!!! */

  }

  public static JCheckBox getCheckBoxByWeekDay(byte currentWeekNumber, JCheckBox[] checkBoxes) {
    switch (currentWeekNumber) {
      case 1:
        return checkBoxes[0];
      case 2:
        return checkBoxes[1];
      case 3:
        return checkBoxes[2];
      case 4:
        return checkBoxes[3];
      default:
        return null;

    }
  }

  public static boolean checkMainRecordBeforeAdd(List<Record> mainRecordsCollection,
      Record mainRecord) {

    byte mainRecSubGroupValue = mainRecord.getSubjectFor().getId();
    byte elRecSubGroupValue;

    for (Record element : mainRecordsCollection) {
      if ((element.getWeekDay() == mainRecord.getWeekDay())
          && (element.getWeekNumber() == mainRecord.getWeekNumber())
          && (element.getSubjOrdinalNumber() == mainRecord.getSubjOrdinalNumber())) {

        elRecSubGroupValue = element.getSubjectFor().getId();

        if (elRecSubGroupValue == mainRecSubGroupValue) {
          JOptionPane.showMessageDialog(null, RECORD_CONFLICT_MESSAGE);
          return false;
        } // т.к. имеет место костыль, то 1 изменяеться на 3 (Вся
          // группа)
        else if (mainRecSubGroupValue == 3 || elRecSubGroupValue == 3) {
          JOptionPane.showMessageDialog(null, RECORD_CONFLICT_MESSAGE);
          return false;
        }
      }
    }
    return true;
  }

  /* данный метод заменён обобщением */
  public static boolean checkSubjectsRecBeforeAdd(List<Subject> subjectsCollection, Subject subj) {
    for (Subject e : subjectsCollection) {
      if (e.getNameSubject().equals(subj.getNameSubject())) {
        JOptionPane.showMessageDialog(null, RECORD_CONFLICT_MESSAGE);
        return false;
      }
    }

    return true;
  }

  /* данный метод заменён обобщением */
  public static boolean checkGroupsRecBeforeAdd(List<Group> groupsCollection, Group group) {
    for (Group e : groupsCollection) {
      if (e.getNameGroup().equals(group.getNameGroup())) {
        JOptionPane.showMessageDialog(null, RECORD_CONFLICT_MESSAGE);
        return false;
      }
    }
    return true;
  }

  /* данный метод заменён обобщением */
  public static boolean checkLecturersRecBeforeAdd(List<Lecturer> lecturersCollection,
      Lecturer lect) {
    for (Lecturer e : lecturersCollection) {
      if (e.getNameLecturer().equals(lect.getNameLecturer())) {
        JOptionPane.showMessageDialog(null, RECORD_CONFLICT_MESSAGE);
        return false;
      }
    }
    return true;
  }

  // полиморфизм
  /*
   * public static boolean checkRecordBeforeAdd(ArrayList<MyInt> collection, MyInt element) { for
   * (MyInt e : collection) { if (e.getName().equals(element.getName())) {
   * JOptionPane.showMessageDialog(null, RECORD_CONFLICT_MESSAGE); return false; } } return true; }
   */
  public static void setHorizontalAlignmentToTable(JTable table) {
    DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
    rightRenderer.setHorizontalAlignment(JLabel.CENTER);
    table.getColumnModel().getColumn(0).setCellRenderer(rightRenderer);
  }

  public static JLabel getJLabelWithPicture(JFrame frame) throws IOException {
    return new JLabel(new ImageIcon(frame.getClass().getClassLoader().getResource("images/1.png")));
  }

  public static String makeAnAbbreviation(String name) {
    String[] wordsArr;// array for separated words from name
    String result = ""; // variable for result

    if (name != null) {
      if (name.length() <= LENGTHFORWORD) {//
        return name;
      } else {
        wordsArr = name.split(" ");
        for (String e : wordsArr) {
          if (!e.equals("")) {
            if (e.length() == LENGTHOFI) {// если это слово из 1
                                          // буквы, то добавляем
                                          // его в нижнем регистре
                                          // к аббривиатуре
              result = result.concat(String.valueOf(e.charAt(0)).toLowerCase());
            } else /*
                    * если это не 2 слова, написанные через знак "-"
                    */ if (!e.contains("-")) {
              result = result.concat(String.valueOf(e.charAt(0)).toUpperCase());
            } else {
              /*
               * если это 2 слова разделенных через "-", то необходимо взять по первой букве из
               * каждого
               */
              String[] temp = e.split("-");
              for (String ee : temp) {
                result = result.concat(String.valueOf(ee.charAt(0)).toUpperCase());
              }
            }
          }
        }
        return result;
      }
    } else {
      return result;// when name is null
    }

  }

  /*
   * // добавление аббривиатуры к названию дисциплины public static List<Subject>
   * addAbrToCollection(List<Subject> subjCol) throws SQLException {
   * 
   * final String defValue = "Unknown"; for (int i = 0; i < subjCol.size(); i++) { if
   * (subjCol.get(i).getAbnameSubject().equals(defValue)) {
   * subjCol.get(i).setAbnameSubject(SupportClass.makeAnAbbreviation(subjCol.get(i).getNameSubject()
   * )); } try { Subject subject = subjCol.get(i);
   * 
   * Request request = new Request(); ICommand command =
   * CommandProvider.getInstance().getCommand(ActionMode.Update_Subject);
   * 
   * request.putParam("subject", subject);
   * 
   * command.execute(request); } catch (CommandException e) {
   * LOGGER.error(e.getCause().getMessage(), e); } } return subjCol; }
   */

  public static String makeTipForRecord(Record record) {
    // формат: Название предмета (тип), Преподаватель, [аудитория]
    // 28.10.16 задал, чтобы отображались аббревиатуры, вместо целых
    // названий

    byte subjType = record.getSubjectType().getId();

    String subjectName = record.getSubject().getName();

    String lectName = record.getLecturer().getName();

    String classRoom = record.getClassroom().getFullClassroomName();

    switch (subjType) {
      case 1:
        return String.format(RECORD_TIP_CONSTANT, subjectName, LessonType.LECTURE.getValue(), lectName,
            classRoom);
      case 2:
        return String.format(RECORD_TIP_CONSTANT, subjectName, LessonType.LABORATORY_WORK.getValue(),
            lectName, classRoom);
      case 3:
        return String.format(RECORD_TIP_CONSTANT, subjectName, LessonType.PRACTICE_WORK.getValue(),
            lectName, classRoom);
      case 4:
        return String.format(RECORD_TIP_CONSTANT, subjectName, LessonType.CONSULTATION.getValue(),
            lectName, classRoom);
      case 5:
        return String.format(RECORD_TIP_CONSTANT, subjectName, LessonType.CREDIT.getValue(),
            lectName, classRoom);
      case 6:
        return String.format(RECORD_TIP_CONSTANT, subjectName, LessonType.EXAM.getValue(), lectName,
            classRoom);
      case 7:
        return String.format(RECORD_TIP_CONSTANT, subjectName, LessonType.TRAINING_SESSION.getValue(),
            lectName, classRoom);
      case 8:
        return String.format(RECORD_TIP_CONSTANT, subjectName, LessonType.MOVE.getValue(), lectName,
            classRoom);
      default:
        return String.format(RECORD_TIP_CONSTANT, subjectName, LessonType.LECTURE.getValue(), lectName,
            classRoom);
    }
  }
}
