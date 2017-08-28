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
@Table(name = "subject_for")
public class SubjectFor implements java.io.Serializable, Cloneable {

  private static final long serialVersionUID = 1L;

  private byte id;
  private String name;
  private Set<Record> records = new HashSet<>(0);

  public SubjectFor() {}

  public SubjectFor(byte id, String name) {
    this.id = id;
    this.name = name;
  }

  public SubjectFor(byte id, String name, Set<Record> records) {
    this.id = id;
    this.name = name;
    this.records = records;
  }

  @Id
  @Column(name = "id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  public byte getId() {
    return this.id;
  }

  public void setId(byte id) {
    this.id = id;
  }

  @Column(name = "name", length = 45, nullable = false)
  public String getName() {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
  }

  @OneToMany(mappedBy = "subjectFor", fetch = FetchType.LAZY)
  public Set<Record> getRecords() {
    return this.records;
  }

  public void setRecords(Set<Record> records) {
    this.records = records;
  }

  @Override
  public String toString() {
    return "SubjectFor [id=" + id + ", name=" + name + "]";
  }

  @Override
  protected Object clone() throws CloneNotSupportedException {
    return super.clone();
  }
}
