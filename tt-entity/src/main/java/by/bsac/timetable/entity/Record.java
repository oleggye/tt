package by.bsac.timetable.entity;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

@Entity
@Table(name = "record", catalog = "timetable")
public class Record implements java.io.Serializable, Cloneable, IName {

  private static final long serialVersionUID = -6066965208149766799L;
  private static final String FORMAT_CONST = "%s(%s)";


  private Integer idRecord;
  private Classroom classroom;
  private Group group;
  private Lecturer lecturer;
  private Subject subject;
  private SubjectFor subjectFor;
  private SubjectType subjectType;
  private byte weekNumber;
  private byte weekDay;
  private byte subjOrdinalNumber;
  private Date dateFrom;
  private Date dateTo;
  private Set<Cancellation> cancellations = new HashSet<>(0);

  public Record() {}


  public Record(Classroom classroom, Group group, Lecturer lecturer, Subject subject,
      SubjectFor subjectFor, SubjectType subjectType, byte weekNumber, byte weekDay,
      byte subjOrdinalNumber, Date dateFrom, Date dateTo) {
    this.classroom = classroom;
    this.group = group;
    this.lecturer = lecturer;
    this.subject = subject;
    this.subjectFor = subjectFor;
    this.subjectType = subjectType;
    this.weekNumber = weekNumber;
    this.weekDay = weekDay;
    this.subjOrdinalNumber = subjOrdinalNumber;
    this.dateFrom = dateFrom;
    this.dateTo = dateTo;
  }

  public Record(Classroom classroom, Group group, Lecturer lecturer, Subject subject,
      SubjectFor subjectFor, SubjectType subjectType, byte weekNumber, byte weekDay,
      byte subjOrdinalNumber, Date dateFrom, Date dateTo, Set<Cancellation> cancellations) {
    this.classroom = classroom;
    this.group = group;
    this.lecturer = lecturer;
    this.subject = subject;
    this.subjectFor = subjectFor;
    this.subjectType = subjectType;
    this.weekNumber = weekNumber;
    this.weekDay = weekDay;
    this.subjOrdinalNumber = subjOrdinalNumber;
    this.dateFrom = dateFrom;
    this.dateTo = dateTo;
    this.cancellations = cancellations;
  }

  @Id
  /*@GeneratedValue(strategy = IDENTITY)*/
  @Column(name = "id_record", unique = true, nullable = false)
  public Integer getIdRecord() {
    return this.idRecord;
  }

  public void setIdRecord(Integer idRecord) {
    this.idRecord = idRecord;
  }

  @ManyToOne(fetch = FetchType.LAZY)
  /*@JoinColumn(name = "id_classroom", nullable = false)*/
  public Classroom getClassroom() {
    return this.classroom;
  }

  public void setClassroom(Classroom classroom) {
    this.classroom = classroom;
  }

  @ManyToOne(fetch = FetchType.LAZY)
 /* @JoinColumn(name = "id_group", nullable = false)*/
  public Group getGroup() {
    return this.group;
  }

  public void setGroup(Group group) {
    this.group = group;
  }

  @ManyToOne(fetch = FetchType.LAZY)
  /*@JoinColumn(name = "id_lecturer", nullable = false)*/
  public Lecturer getLecturer() {
    return this.lecturer;
  }

  public void setLecturer(Lecturer lecturer) {
    this.lecturer = lecturer;
  }

  @ManyToOne(fetch = FetchType.LAZY)
 /* @JoinColumn(name = "id_subject", nullable = false)*/
  public Subject getSubject() {
    return this.subject;
  }

  public void setSubject(Subject subject) {
    this.subject = subject;
  }

  @ManyToOne(fetch = FetchType.LAZY)
  /*@JoinColumn(name = "id_subject_for", nullable = false)*/
  public SubjectFor getSubjectFor() {
    return this.subjectFor;
  }

  public void setSubjectFor(SubjectFor subjectFor) {
    this.subjectFor = subjectFor;
  }

  @ManyToOne(fetch = FetchType.LAZY)
 /* @JoinColumn(name = "id_subject_type", nullable = false)*/
  public SubjectType getSubjectType() {
    return this.subjectType;
  }

  public void setSubjectType(SubjectType subjectType) {
    this.subjectType = subjectType;
  }


  @Column(name = "week_number", nullable = false)
  public byte getWeekNumber() {
    return this.weekNumber;
  }

  public void setWeekNumber(byte weekNumber) {
    this.weekNumber = weekNumber;
  }


  @Column(name = "week_day", nullable = false)
  public byte getWeekDay() {
    return this.weekDay;
  }

  public void setWeekDay(byte weekDay) {
    this.weekDay = weekDay;
  }


  @Column(name = "subj_ordinal_number", nullable = false)
  public byte getSubjOrdinalNumber() {
    return this.subjOrdinalNumber;
  }

  public void setSubjOrdinalNumber(byte subjOrdinalNumber) {
    this.subjOrdinalNumber = subjOrdinalNumber;
  }

  @Temporal(TemporalType.DATE)
  @Column(name = "date_from", nullable = false, length = 10)
  public Date getDateFrom() {
    return this.dateFrom;
  }

  public void setDateFrom(Date dateFrom) {
    this.dateFrom = dateFrom;
  }

  @Temporal(TemporalType.DATE)
  @Column(name = "date_to", nullable = false, length = 10)
  public Date getDateTo() {
    return this.dateTo;
  }

  public void setDateTo(Date dateTo) {
    this.dateTo = dateTo;
  }

  @OneToMany(fetch = FetchType.LAZY, mappedBy = "record")
  public Set<Cancellation> getCancellations() {
    return this.cancellations;
  }

  public void setCancellations(Set<Cancellation> cancellations) {
    this.cancellations = cancellations;
  }

  @Transient
  public String getSubjAndSubjType(boolean isFullNames) {
    // 28.10.16 задал, чтобы отображались аббревиатуры, вместо целых
    // названий
    int idSubjectType = this.getSubjectType().getId();
    String subjectName;
    if (isFullNames) {
      subjectName = this.getSubject().getNameSubject();
    } else {
      subjectName = this.getSubject().getAbnameSubject();
    }

    switch (idSubjectType) {
      case 1:
        return String.format(FORMAT_CONST, subjectName, "Лекция");
      case 2:
        return String.format(FORMAT_CONST, subjectName, "ЛР");
      case 3:
        return String.format(FORMAT_CONST, subjectName, "ПЗ");
      case 4:
        return String.format(FORMAT_CONST, subjectName, "Консультация");
      case 5:
        return String.format(FORMAT_CONST, subjectName, "Зачет");
      case 6:
        return String.format(FORMAT_CONST, subjectName, "Экзамен");
      case 7:
        return String.format(FORMAT_CONST, subjectName, "Учебное занятие");
      case 8:
        return String.format(FORMAT_CONST, subjectName, "Переезд");
      default:
        return String.format(FORMAT_CONST, subjectName, "Лекция");
    }
  }

  // @Override
  // public String toString() {
  // return "Record [idRecord=" + idRecord + ", classroom=" + classroom + ",
  // group=" + group + ", lecturer="
  // + lecturer + ", subject=" + subject + ", subjectFor=" + subjectFor + ",
  // subjectType=" + subjectType
  // + ", weekNumber=" + weekNumber + ", weekDay=" + weekDay + ",
  // subjOrdinalNumber=" + subjOrdinalNumber
  // + ", dateFrom=" + dateFrom + ", dateTo=" + dateTo + "]\n";
  // }
  // FIXME так не стоит делать!
  @Override
  public String toString() {
    return getSubjAndSubjType(false);
  }



  @Override
  @Transient
  public String getName() {
    return getSubjAndSubjType(false);
  }

  public String printRecord() {
    return "Record [idRecord=" + idRecord + ", classroom=" + classroom + ", group=" + group
        + ", lecturer=" + lecturer + ", subject=" + subject + ", subjectFor=" + subjectFor
        + ", subjectType=" + subjectType + ", weekNumber=" + weekNumber + ", weekDay=" + weekDay
        + ", subjOrdinalNumber=" + subjOrdinalNumber + ", dateFrom=" + dateFrom + ", dateTo="
        + dateTo + "]";
  }

  @Override
  public Object clone() throws CloneNotSupportedException {
    Record result = (Record) super.clone();
    result.classroom = (Classroom) classroom.clone();
    result.group = (Group) group.clone();
    result.lecturer = (Lecturer) lecturer.clone();
    result.subject = (Subject) subject.clone();
    result.subjectFor = (SubjectFor) subjectFor.clone();
    result.subjectType = (SubjectType) subjectType.clone();
    result.dateTo = (Date) dateTo.clone();
    result.dateFrom = (Date) dateFrom.clone();
    return result;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((classroom == null) ? 0 : classroom.hashCode());
    result = prime * result + ((dateFrom == null) ? 0 : dateFrom.hashCode());
    result = prime * result + ((dateTo == null) ? 0 : dateTo.hashCode());
    result = prime * result + ((group == null) ? 0 : group.hashCode());
    result = prime * result + ((idRecord == null) ? 0 : idRecord.hashCode());
    result = prime * result + ((lecturer == null) ? 0 : lecturer.hashCode());
    result = prime * result + subjOrdinalNumber;
    result = prime * result + ((subject == null) ? 0 : subject.hashCode());
    result = prime * result + ((subjectFor == null) ? 0 : subjectFor.hashCode());
    result = prime * result + ((subjectType == null) ? 0 : subjectType.hashCode());
    result = prime * result + weekDay;
    result = prime * result + weekNumber;
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    Record other = (Record) obj;
    if (idRecord == null) {
      if (other.idRecord != null) {
        return false;
      }
    } else if (!idRecord.equals(other.idRecord)) {
      return false;
    }
    if (classroom == null) {
      if (other.classroom != null) {
        return false;
      }
    } else if (!classroom.equals(other.classroom)) {
      return false;
    }
    if (dateFrom == null) {
      if (other.dateFrom != null) {
        return false;
      }
    } else if (!dateFrom.equals(other.dateFrom)) {
      return false;
    }
    if (dateTo == null) {
      if (other.dateTo != null) {
        return false;
      }
    } else if (!dateTo.equals(other.dateTo)) {
      return false;
    }
    if (group == null) {
      if (other.group != null) {
        return false;
      }
    } else if (!group.equals(other.group)) {
      return false;
    }
    if (lecturer == null) {
      if (other.lecturer != null) {
        return false;
      }
    } else if (!lecturer.equals(other.lecturer)) {
      return false;
    }
    if (subjOrdinalNumber != other.subjOrdinalNumber) {
      return false;
    }
    if (subject == null) {
      if (other.subject != null) {
        return false;
      }
    } else if (!subject.equals(other.subject)) {
      return false;
    }
    if (subjectFor == null) {
      if (other.subjectFor != null) {
        return false;
      }
    } else if (!subjectFor.equals(other.subjectFor)) {
      return false;
    }
    if (subjectType == null) {
      if (other.subjectType != null) {
        return false;
      }
    } else if (!subjectType.equals(other.subjectType)) {
      return false;
    }
    if (weekDay != other.weekDay) {
      return false;
    }
    return weekNumber != other.weekNumber;
  }
}


