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

@Entity
@Table(name = "subject_type")
public class SubjectType implements java.io.Serializable, Cloneable {

  private static final long serialVersionUID = 1L;

  private Byte id;
  private String name;
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
  @Column(name = "id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  public Byte getId() {
    return this.id;
  }

  public void setId(Byte id) {
    this.id = id;
  }

  @Column(name = "name", length = 20, nullable = false)
  public String getName() {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
  }

  @OneToMany(mappedBy = "subjectType", fetch = FetchType.LAZY)
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
}
