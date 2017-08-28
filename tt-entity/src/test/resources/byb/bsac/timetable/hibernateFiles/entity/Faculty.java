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
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "faculty", uniqueConstraints = @UniqueConstraint(columnNames = "name_faculty"))
public class Faculty implements java.io.Serializable, Cloneable, IName {

  private static final long serialVersionUID = 1L;

  private Short idFaculty;
  private String nameFaculty;
  private Set<Group> groups = new HashSet<>(0);

  public Faculty() {}

  /*
   * public Faculty(String nameFaculty) { this.nameFaculty = nameFaculty; }
   * 
   * public Faculty(String nameFaculty, Set<Group> groups) { this.nameFaculty = nameFaculty;
   * this.groups = groups; }
   */

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id_faculty", unique = true, nullable = false)
  public Short getIdFaculty() {
    return this.idFaculty;
  }

  public void setIdFaculty(Short idFaculty) {
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
    result = prime * result + ((groups == null) ? 0 : groups.hashCode());
    result = prime * result + idFaculty;
    result = prime * result + ((nameFaculty == null) ? 0 : nameFaculty.hashCode());
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
    Faculty other = (Faculty) obj;
    if (idFaculty != other.idFaculty)
      return false;
    if (nameFaculty == null) {
      if (other.nameFaculty != null)
        return false;
    } else if (!nameFaculty.equals(other.nameFaculty))
      return false;
    return true;
  }
}
