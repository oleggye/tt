package by.bsac.timetable.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
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
@Table(name = "classroom",
    uniqueConstraints = @UniqueConstraint(columnNames = {"name", "building"}))
public class Classroom implements java.io.Serializable, Cloneable, IName {

  private static final long serialVersionUID = 7602775957279064254L;

  @JsonProperty(value="id")
  private Short idClassroom;
  private String name;
  private byte building;
  @JsonIgnore
  private Set<Record> records = new HashSet<>(0);
  
  private boolean isReserved;

  public Classroom() {}


  public Classroom(String name, byte building) {
    this.name = name;
    this.building = building;
  }

  public Classroom(String name, byte building, Set<Record> records) {
    this.name = name;
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


  @Column(name = "name", nullable = false, length = 30)
  public String getName() {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
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
  @JsonIgnore
  public String getFullClassroomName() {
    String format = "%s (к.%s)";
    return String.format(format, this.getName(), this.getBuilding());
  }

  @Transient
  @JsonIgnore
  public boolean isReserved() {
    return isReserved;
  }

  public void setReserved(boolean isReserved) {
    this.isReserved = isReserved;
  }

  @Override
  public String toString() {
    return "Classroom [idClassroom=" + idClassroom + ", number=" + name + ", building=" + building
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
    result = prime * result + ((name == null)? 0 : name.hashCode());
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


