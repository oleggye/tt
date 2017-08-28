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

@Entity
@Table(name = "subject")
public class Subject implements java.io.Serializable, Cloneable, IName {

  private static final long serialVersionUID = 1L;

  private short idSubject;
  private Chair chair;
  private String nameSubject;
  private byte eduLevel;
  private String abnameSubject;

  private Set<Record> records = new HashSet<>(0);

  public Subject() {}

  public Subject(short idSubject, Chair chair, String nameSubject, byte eduLevel,
      String abnameSubject) {
    this.idSubject = idSubject;
    this.chair = chair;
    this.nameSubject = nameSubject;
    this.eduLevel = eduLevel;
    this.abnameSubject = abnameSubject;
  }

  public Subject(Chair chair, String nameSubject, byte eduLevel) {
    this.chair = chair;
    this.nameSubject = nameSubject;
    this.eduLevel = eduLevel;
  }

  public Subject(Chair chair, String nameSubject, byte eduLevel, String abnameSubject) {
    this.chair = chair;
    this.nameSubject = nameSubject;
    this.eduLevel = eduLevel;
    this.abnameSubject = abnameSubject;
  }

  public Subject(short idSubject, Chair chair, String nameSubject, byte eduLevel,
      String abnameSubject, Set<Record> records) {
    this.idSubject = idSubject;
    this.chair = chair;
    this.nameSubject = nameSubject;
    this.eduLevel = eduLevel;
    this.abnameSubject = abnameSubject;
    this.records = records;
  }

  @Id
  @Column(name = "id_subject")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  public short getIdSubject() {
    return this.idSubject;
  }

  public void setIdSubject(short idSubject) {
    this.idSubject = idSubject;
  }

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "id_chair", nullable = false)
  public Chair getChair() {
    return this.chair;
  }

  public void setChair(Chair chair) {
    this.chair = chair;
  }

  @Column(name = "name_subject", length = 80, nullable = false)
  public String getNameSubject() {
    return this.nameSubject;
  }

  public void setNameSubject(String nameSubject) {
    this.nameSubject = nameSubject;
  }

  @Column(name = "edu_level", nullable = false)
  public byte getEduLevel() {
    return this.eduLevel;
  }

  public void setEduLevel(byte eduLevel) {
    this.eduLevel = eduLevel;
  }

  @Column(name = "abname_subject", length = 20, nullable = false)
  public String getAbnameSubject() {
    return this.abnameSubject;
  }

  public void setAbnameSubject(String abnameSubject) {
    this.abnameSubject = abnameSubject;
  }

  @OneToMany(fetch = FetchType.LAZY, mappedBy = "subject")
  public Set<Record> getRecords() {
    return this.records;
  }

  public void setRecords(Set<Record> records) {
    this.records = records;
  }

  @Override
  @Transient
  public String getName() {
    return this.getNameSubject();
  }

  @Override
  public String toString() {
    return "Subject [idSubject=" + idSubject + ", chair=" + chair + ", nameSubject=" + nameSubject
        + ", eduLevel=" + eduLevel + ", abnameSubject=" + abnameSubject + "]";
  }

  @Override
  protected Object clone() throws CloneNotSupportedException {
    return super.clone();
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((abnameSubject == null) ? 0 : abnameSubject.hashCode());
    result = prime * result + ((chair == null) ? 0 : chair.hashCode());
    result = prime * result + eduLevel;
    result = prime * result + idSubject;
    result = prime * result + ((nameSubject == null) ? 0 : nameSubject.hashCode());
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
    Subject other = (Subject) obj;

    return (idSubject != other.idSubject);
  }
}
