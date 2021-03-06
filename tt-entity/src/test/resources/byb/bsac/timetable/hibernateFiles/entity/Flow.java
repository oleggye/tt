package by.bsac.timetable.hibernateFiles.entity;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "flow", uniqueConstraints = @UniqueConstraint(columnNames = "name"))
public class Flow implements java.io.Serializable, Cloneable, IName {

  private static final long serialVersionUID = 1L;

  private short idFlow;
  private String name;
  private Set<Group> groups = new HashSet<>(0);

  public Flow() {}

  public Flow(short idFlow, String name) {
    this.idFlow = idFlow;
    this.name = name;
  }

  public Flow(short idFlow, String name, Set<Group> groups) {
    this.idFlow = idFlow;
    this.name = name;
    this.groups = groups;
  }

  @Id
  @GeneratedValue(strategy = IDENTITY)
  @Column(name = "id_flow", unique = true, nullable = false)
  public short getIdFlow() {
    return this.idFlow;
  }

  public void setIdFlow(short idFlow) {
    this.idFlow = idFlow;
  }

  @Column(name = "name", unique = true, nullable = false, length = 45)
  public String getName() {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
  }

  @OneToMany(fetch = FetchType.LAZY, mappedBy = "flow")
  public Set<Group> getGroups() {
    return this.groups;
  }

  public void setGroups(Set<Group> groups) {
    this.groups = groups;
  }

  @Override
  public String toString() {
    return "Flow [idFlow=" + idFlow + ", name=" + name + "]";
  }

  @Override
  protected Object clone() throws CloneNotSupportedException {
    return super.clone();
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + idFlow;
    result = prime * result + ((name == null) ? 0 : name.hashCode());;
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
    Flow other = (Flow) obj;
    return idFlow != other.idFlow;
  }
}
