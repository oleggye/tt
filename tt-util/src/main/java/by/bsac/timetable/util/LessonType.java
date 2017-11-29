package by.bsac.timetable.util;

import by.bsac.timetable.entity.SubjectType;

public enum LessonType {
  LECTURE("Лекция"), LABORATORY_WORK("ЛР"), PRACTICE_WORK("ПЗ"),

  CONSULTATION("Консультация"), CREDIT("Зачет"), EXAM("Экзамен"),

  TRAINING_SESSION("Учебное занятие"), MOVE("Переезд");

  private String value;

  LessonType(String value) {
    this.value = value;
  }

  /**
   * <table border="1">
   * <caption>{@link SubjectType} mapping</caption>
   * <tr>
   * <th>{@link SubjectType#getId()} value</th>
   * <th>instance of {@link LessonType}</th>
   * </tr>
   * <tr>
   * <td>1</td>
   * <td>LECTURE</td>
   * </tr>
   * <tr>
   * <td>2</td>
   * <td>LABORATORY_WORK</td>
   * </tr>
   * <tr>
   * <td>3</td>
   * <td>PRACTICE_WORK</td>
   * </tr>
   * <tr>
   * <td>4</td>
   * <td>CONSULTATION</td>
   * </tr>
   * <tr>
   * <td>5</td>
   * <td>CREDIT</td>
   * </tr>
   * <tr>
   * <td>6</td>
   * <td>EXAM</td>
   * </tr>
   * <tr>
   * <td>7</td>
   * <td>TRAINING_SESSION</td>
   * </tr>
   * <tr>
   * <td>8</td>
   * <td>MOVE</td>
   * </tr>
   * </table>
   *
   * @param subjectType instance of SubjectType
   * @return equivalent instance of LessonType for @param
   */
  public static LessonType subjectTypeToLessonType(SubjectType subjectType) {
    if (subjectType == null) {
      throw new IllegalArgumentException("subjectType is null");
    }

    switch (subjectType.getId()) {
      case 1:
        return LessonType.LECTURE;
      case 2:
        return LessonType.LABORATORY_WORK;
      case 3:
        return LessonType.PRACTICE_WORK;
      case 4:
        return LessonType.CONSULTATION;
      case 5:
        return LessonType.CREDIT;
      case 6:
        return LessonType.EXAM;
      case 7:
        return LessonType.TRAINING_SESSION;
      case 8:
        return LessonType.MOVE;

      default:
        throw new IllegalArgumentException("Illegal argument: " + subjectType);
    }
  }

  /**
   * @see #subjectTypeToLessonType(SubjectType)
   */
  public SubjectType lessonTypeToSubjectType() {
    SubjectType subjectType = new SubjectType();

    switch (this) {
      case LECTURE:
        subjectType.setId((byte) (LECTURE.ordinal() + 1));
        break;
      case LABORATORY_WORK:
        subjectType.setId((byte) (LABORATORY_WORK.ordinal() + 1));
        break;
      case PRACTICE_WORK:
        subjectType.setId((byte) (PRACTICE_WORK.ordinal() + 1));
        break;
      case CONSULTATION:
        subjectType.setId((byte) (CONSULTATION.ordinal() + 1));
        break;
      case CREDIT:
        subjectType.setId((byte) (CREDIT.ordinal() + 1));
        break;
      case EXAM:
        subjectType.setId((byte) (EXAM.ordinal() + 1));
        break;
      case TRAINING_SESSION:
        subjectType.setId((byte) (TRAINING_SESSION.ordinal() + 1));
        break;
      case MOVE:
        subjectType.setId((byte) (MOVE.ordinal() + 1));
        break;
      default: /* never throws if API wont change */
        throw new IllegalArgumentException("Wrong element:" + this);
    }
    return subjectType;
  }


  public String getValue() {
    return this.value;
  }
}
