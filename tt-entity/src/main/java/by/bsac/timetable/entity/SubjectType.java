package by.bsac.timetable.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "subject_type")
public class SubjectType implements java.io.Serializable, Cloneable {

  private static final long serialVersionUID = -4158964098924310875L;

  private Byte id;
  private String name;
  @JsonIgnore
  private Set<Record> records = new HashSet<>(0);

  public SubjectType() {}


  public SubjectType(String name) {
    this.name = name;
  }

  public SubjectType(String name, Set<Record> records) {
    this.name = name;
    this.records = records;
  }

  @Id
  /* @GeneratedValue(strategy = IDENTITY) */
  @Column(name = "id", unique = true, nullable = false)
  public Byte getId() {
    return this.id;
  }

  public void setId(Byte id) {
    this.id = id;
  }


  @Column(name = "name", nullable = false, length = 20)
  public String getName() {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
  }

  @OneToMany(fetch = FetchType.LAZY, mappedBy = "subjectType")
  public Set<Record> getRecords() {
    return this.records;
  }

  public void setRecords(Set<Record> records) {
    this.records = records;
  }

  @Override
  public String toString() {
    return "SubjectType [id=" + id + ", name=" + name + "]";
  }

  @Override
  protected Object clone() throws CloneNotSupportedException {
    return super.clone();
  }


  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((id == null) ? 0 : id.hashCode());
    result = prime * result + ((name == null) ? 0 : name.hashCode());
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
    SubjectType other = (SubjectType) obj;
    if (id == null) {
      if (other.id != null)
        return false;
    } else if (!id.equals(other.id))
      return false;
    if (name == null) {
      if (other.name != null)
        return false;
    } else if (!name.equals(other.name))
      return false;
    return true;
  }
}
