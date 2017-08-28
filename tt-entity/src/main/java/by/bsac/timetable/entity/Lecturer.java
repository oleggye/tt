package by.bsac.timetable.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;


@Entity
@Table(name = "lecturer")
public class Lecturer implements java.io.Serializable, Cloneable, IName {

  private static final long serialVersionUID = -1097849435762885350L;

  private Short idLecturer;
  private Chair chair;
  private String nameLecturer;
  private Set<Record> records = new HashSet<>(0);

  public Lecturer() {}


  public Lecturer(Chair chair, String nameLecturer) {
    this.chair = chair;
    this.nameLecturer = nameLecturer;
  }

  public Lecturer(Chair chair, String nameLecturer, Set<Record> records) {
    this.chair = chair;
    this.nameLecturer = nameLecturer;
    this.records = records;
  }

  @Id
  /*@GeneratedValue(strategy = IDENTITY)*/
  @Column(name = "id_lecturer", unique = true, nullable = false)
  public Short getIdLecturer() {
    return this.idLecturer;
  }

  public void setIdLecturer(Short idLecturer) {
    this.idLecturer = idLecturer;
  }

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "id_chair", nullable = false)
  public Chair getChair() {
    return this.chair;
  }

  public void setChair(Chair chair) {
    this.chair = chair;
  }


  @Column(name = "name_lecturer", nullable = false, length = 45)
  public String getNameLecturer() {
    return this.nameLecturer;
  }

  public void setNameLecturer(String nameLecturer) {
    this.nameLecturer = nameLecturer;
  }

  @OneToMany(fetch = FetchType.LAZY, mappedBy = "lecturer")
  public Set<Record> getRecords() {
    return this.records;
  }

  public void setRecords(Set<Record> records) {
    this.records = records;
  }

  @Override

  @Transient
  public String getName() {
    return this.getNameLecturer();
  }

  @Override
  public String toString() {
    return "Lecturer [idLecturer=" + idLecturer + ", chair=" + chair + ", nameLecturer="
        + nameLecturer + "]";
  }

  @Override
  protected Object clone() throws CloneNotSupportedException {
    Lecturer result = (Lecturer) super.clone();
    result.chair = (Chair) chair.clone();
    return result;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((chair == null) ? 0 : chair.hashCode());
    result = prime * result + ((idLecturer == null) ? 0 : idLecturer.hashCode());
    result = prime * result + ((nameLecturer == null) ? 0 : nameLecturer.hashCode());
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
    Lecturer other = (Lecturer) obj;
    if (idLecturer == null) {
      if (other.idLecturer != null)
        return false;
    } else if (!idLecturer.equals(other.idLecturer))
      return false;
    return true;
  }
}
