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
@Table(name = "faculty",
    uniqueConstraints = @UniqueConstraint(columnNames = "name_faculty"))
public class Faculty implements java.io.Serializable, Cloneable, IName {

  private static final long serialVersionUID = -2033949070842765012L;

  @JsonProperty(value = "id")
  private Byte idFaculty;
  @JsonProperty(value = "name")
  private String nameFaculty;
  @JsonIgnore
  private Set<Group> groups = new HashSet<>(0);

  public Faculty() {
  }

  public Faculty(String nameFaculty) {
    this.nameFaculty = nameFaculty;
  }

  public Faculty(String nameFaculty, Set<Group> groups) {
    this.nameFaculty = nameFaculty;
    this.groups = groups;
  }

  @Id
  /*@GeneratedValue(strategy = IDENTITY)*/
  @Column(name = "id_faculty", unique = true, nullable = false)
  public Byte getIdFaculty() {
    return this.idFaculty;
  }

  public void setIdFaculty(Byte idFaculty) {
    this.idFaculty = idFaculty;
  }


  @Column(name = "name_faculty", unique = true, nullable = false, length = 60)
  public String getNameFaculty() {
    return this.nameFaculty;
  }

  public void setNameFaculty(String nameFaculty) {
    this.nameFaculty = nameFaculty;
  }

  @OneToMany(fetch = FetchType.LAZY, mappedBy = "faculty")
  public Set<Group> getGroups() {
    return this.groups;
  }

  public void setGroups(Set<Group> groups) {
    this.groups = groups;
  }

  @Override
  @Transient
  @JsonIgnore
  public String getName() {
    return this.getNameFaculty();
  }

  @Override
  public String toString() {
    return "Faculty [idFaculty=" + idFaculty + ", nameFaculty=" + nameFaculty + "]";
  }

  @Override
  protected Object clone() throws CloneNotSupportedException {
    return super.clone();
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + idFaculty;
    result = prime * result + ((nameFaculty == null) ? 0 : nameFaculty.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    Faculty faculty = (Faculty) o;

    if (idFaculty != null ? !idFaculty.equals(faculty.idFaculty) : faculty.idFaculty != null) {
      return false;
    }
    return nameFaculty != null ? nameFaculty.equals(faculty.nameFaculty)
        : faculty.nameFaculty == null;
  }
}
