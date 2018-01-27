package by.bsac.timetable.hibernateFiles.entity;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "group", uniqueConstraints = @UniqueConstraint(columnNames = "name_group"))
public class Group implements java.io.Serializable, Cloneable, IName {

  private static final long serialVersionUID = 1L;

  private Short idGroup;
  private String nameGroup;
  private Flow flow;
  private Faculty faculty;
  private Byte eduLevel;
  private Set<Record> records = new HashSet<>(0);

  public Group() {}

  /*
   * public group(Faculty faculty, String nameGroup, byte eduLevel) { this.faculty = faculty;
   * this.nameGroup = nameGroup; this.eduLevel = eduLevel; }
   * 
   * public group(Faculty faculty, Flow flow, String nameGroup, byte eduLevel, Set<Record> records)
   * { this.faculty = faculty; this.flow = flow; this.nameGroup = nameGroup; this.eduLevel =
   * eduLevel; this.records = records; }
   */
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id_group", unique = true, nullable = false)
  public Short getIdGroup() {
    return this.idGroup;
  }

  public void setIdGroup(Short idGroup) {
    this.idGroup = idGroup;
  }

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "id_faculty", nullable = false)
  public Faculty getFaculty() {
    return this.faculty;
  }

  public void setFaculty(Faculty faculty) {
    this.faculty = faculty;
  }

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "id_flow")
  public Flow getFlow() {
    return this.flow;
  }

  public void setFlow(Flow flow) {
    this.flow = flow;
  }

  @Column(name = "name_group", length = 45, nullable = false, unique = true)
  public String getNameGroup() {
    return this.nameGroup;
  }

  public void setNameGroup(String nameGroup) {
    this.nameGroup = nameGroup;
  }

  @Column(name = "edu_level", nullable = false)
  public Byte getEduLevel() {
    return this.eduLevel;
  }

  public void setEduLevel(Byte eduLevel) {
    this.eduLevel = eduLevel;
  }

  @OneToMany(fetch = FetchType.LAZY, mappedBy = "group")
  public Set<Record> getRecords() {
    return this.records;
  }

  public void setRecords(Set<Record> records) {
    this.records = records;
  }

  @Override
  @Transient
  public String getName() {
    return this.getNameGroup();
  }

  @Override
  public String toString() {
    return "Group [idGroup=" + idGroup + ", faculty=" + faculty + ", flow=" + flow + ", nameGroup="
        + nameGroup + ", eduLevel=" + eduLevel + "]";
  }

  /**
   * it's almost deep copy beside record set
   */
  @Override
  protected Object clone() throws CloneNotSupportedException {
    Group result = (Group) super.clone();
    result.faculty = (Faculty) faculty.clone();
    if (flow != null) {
      result.flow = (Flow) flow.clone();
    }
    return result;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + eduLevel;
    result = prime * result + ((faculty == null) ? 0 : faculty.hashCode());
    result = prime * result + ((flow == null) ? 0 : flow.hashCode());
    result = prime * result + ((idGroup == null) ? 0 : idGroup.hashCode());
    result = prime * result + ((nameGroup == null) ? 0 : nameGroup.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    Group other = (Group) obj;
    if (eduLevel != other.eduLevel)
      return false;
    if (faculty == null) {
      if (other.faculty != null)
        return false;
    } else if (!faculty.equals(other.faculty))
      return false;
    if (flow == null) {
      if (other.flow != null)
        return false;
    } else if (!flow.equals(other.flow))
      return false;
    if (idGroup == null) {
      if (other.idGroup != null)
        return false;
    } else if (!idGroup.equals(other.idGroup))
      return false;
    if (nameGroup == null) {
      if (other.nameGroup != null)
        return false;
    } else if (!nameGroup.equals(other.nameGroup))
      return false;
    return true;
  }
}
