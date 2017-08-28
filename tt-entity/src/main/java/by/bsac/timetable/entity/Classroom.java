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


@Entity
@Table(name = "classroom", catalog = "timetable",
    uniqueConstraints = @UniqueConstraint(columnNames = {"number", "building"}))
public class Classroom implements java.io.Serializable, Cloneable, IName {

  private static final long serialVersionUID = 7602775957279064254L;

  private Short idClassroom;
  private short number;
  private byte building;
  private Set<Record> records = new HashSet<Record>(0);

  private boolean isReserved;

  public Classroom() {}


  public Classroom(short number, byte building) {
    this.number = number;
    this.building = building;
  }

  public Classroom(short number, byte building, Set<Record> records) {
    this.number = number;
    this.building = building;
    this.records = records;
  }

  @Id
  /*@GeneratedValue(strategy = IDENTITY)*/
  @Column(name = "id_classroom", unique = true, nullable = false)
  public Short getIdClassroom() {
    return this.idClassroom;
  }

  public void setIdClassroom(Short idClassroom) {
    this.idClassroom = idClassroom;
  }


  @Column(name = "number", nullable = false)
  public short getNumber() {
    return this.number;
  }

  public void setNumber(short number) {
    this.number = number;
  }


  @Column(name = "building", nullable = false)
  public byte getBuilding() {
    return this.building;
  }

  public void setBuilding(byte building) {
    this.building = building;
  }

  @OneToMany(fetch = FetchType.LAZY, mappedBy = "classroom")
  public Set<Record> getRecords() {
    return this.records;
  }

  public void setRecords(Set<Record> records) {
    this.records = records;
  }


  @Transient
  public String getFullClassroomName() {
    String format = "%s (%s)";
    return String.format(format, this.getNumber(), this.getBuilding());
  }

  @Override
  @Transient
  public String getName() {
    return getFullClassroomName();
  }

  @Transient
  public boolean isReserved() {
    return isReserved;
  }

  public void setReserved(boolean isReserved) {
    this.isReserved = isReserved;
  }

  @Override
  public String toString() {
    return "Classroom [idClassroom=" + idClassroom + ", number=" + number + ", building=" + building
        + ", isReserved=" + isReserved + "]";
  }

  @Override
  protected Object clone() throws CloneNotSupportedException {
    return super.clone();
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + building;
    result = prime * result + ((idClassroom == null) ? 0 : idClassroom.hashCode());
    result = prime * result + (isReserved ? 1231 : 1237);
    result = prime * result + number;
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
    Classroom other = (Classroom) obj;
    if (idClassroom == null) {
      if (other.idClassroom != null)
        return false;
    } else if (!idClassroom.equals(other.idClassroom))
      return false;
    return true;

  }

}


