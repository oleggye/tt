package by.bsac.timetable.hibernateFiles.entity;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "chair")
public class Chair implements java.io.Serializable, Cloneable, IName {

  private static final long serialVersionUID = 1L;

  private Short idChair;
  private String nameChair;
  private Set<Lecturer> lecturers = new HashSet<>(0);
  private Set<Subject> subjects = new HashSet<>(0);

  public Chair() {}

  public Chair(String nameChair) {
    this.nameChair = nameChair;
  }

  public Chair(String nameChair, Set<Lecturer> lecturers, Set<Subject> subjects) {
    this.nameChair = nameChair;
    this.lecturers = lecturers;
    this.subjects = subjects;
  }

  @Id
  @Column(name = "id_chair")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  public Short getIdChair() {
    return this.idChair;
  }

  public void setIdChair(Short idChair) {
    this.idChair = idChair;
  }

  @Column(name = "name_chair", length = 70, nullable = false, unique = true)
  public String getNameChair() {
    return this.nameChair;
  }

  public void setNameChair(String nameChair) {
    this.nameChair = nameChair;
  }

  @OneToMany(mappedBy = "chair", fetch = FetchType.LAZY)
  public Set<Lecturer> getLecturers() {
    return this.lecturers;
  }

  public void setLecturers(Set<Lecturer> lecturers) {
    this.lecturers = lecturers;
  }

  @OneToMany(mappedBy = "chair", fetch = FetchType.LAZY)
  public Set<Subject> getSubjects() {
    return this.subjects;
  }

  public void setSubjects(Set<Subject> subjects) {
    this.subjects = subjects;
  }

  @Override
  @Transient
  public String getName() {
    return this.getNameChair();
  }

  @Override
  public String toString() {
    return "Chair [idChair=" + idChair + ", nameChair=" + nameChair + "]";
  }

  @Override
  protected Object clone() throws CloneNotSupportedException {
    return super.clone();
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((idChair == null) ? 0 : idChair.hashCode());
    result = prime * result + ((lecturers == null) ? 0 : lecturers.hashCode());
    result = prime * result + ((nameChair == null) ? 0 : nameChair.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (!(obj instanceof Chair))
      return false;
    Chair other = (Chair) obj;
    if (idChair == null) {
      if (other.idChair != null)
        return false;
    } else if (!idChair.equals(other.idChair))
      return false;
    if (nameChair == null) {
      if (other.nameChair != null)
        return false;
    } else if (!nameChair.equals(other.nameChair))
      return false;
    return true;
  }
}
