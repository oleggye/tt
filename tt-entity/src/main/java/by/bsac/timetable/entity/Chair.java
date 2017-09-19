package by.bsac.timetable.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "chair", uniqueConstraints = @UniqueConstraint(columnNames = "name_chair"))
public class Chair implements java.io.Serializable, Cloneable, IName {

  private static final long serialVersionUID = -3503528652713972743L;

  private Byte idChair;
  private String nameChair;
  @JsonIgnore
  private Set<Lecturer> lecturers = new HashSet<>(0);
  @JsonIgnore
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
  /* @GeneratedValue(strategy = IDENTITY) */
  @Column(name = "id_chair", unique = true, nullable = false)
  public Byte getIdChair() {
    return this.idChair;
  }

  public void setIdChair(Byte idChair) {
    this.idChair = idChair;
  }


  @Column(name = "name_chair", unique = true, nullable = false, length = 70)
  public String getNameChair() {
    return this.nameChair;
  }

  public void setNameChair(String nameChair) {
    this.nameChair = nameChair;
  }

  @OneToMany(fetch = FetchType.LAZY,
      mappedBy = "chair"/*
                         * , cascade = CascadeType.ALL, orphanRemoval = true
                         */)
  public Set<Lecturer> getLecturers() {
    return this.lecturers;
  }

  public void setLecturers(Set<Lecturer> lecturers) {
    this.lecturers = lecturers;
  }

  @OneToMany(fetch = FetchType.LAZY,
      mappedBy = "chair"/*
                         * , cascade = CascadeType.ALL, orphanRemoval = true
                         */)
  public Set<Subject> getSubjects() {
    return this.subjects;
  }

  public void setSubjects(Set<Subject> subjects) {
    this.subjects = subjects;
  }

  @Override
  @Transient
  @JsonIgnore
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
