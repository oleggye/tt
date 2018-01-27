package by.bsac.timetable.entity;

import static javax.persistence.GenerationType.IDENTITY;

import com.fasterxml.jackson.annotation.JsonIgnore;
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

  private static final long serialVersionUID = 2602140866098102543L;

  private Short idFlow;
  private String name;
  @JsonIgnore
  private Set<Group> groups = new HashSet<>(0);

  public Flow() {
  }

  public Flow(String name) {
    this.name = name;
  }

  public Flow(String name, Set<Group> groups) {
    this.name = name;
    this.groups = groups;
  }

  @Id
  @GeneratedValue(strategy = IDENTITY)
  @Column(name = "id_flow", unique = true, nullable = false)
  public Short getIdFlow() {
    return this.idFlow;
  }

  public void setIdFlow(Short idFlow) {
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
    result = prime * result + ((name == null) ? 0 : name.hashCode());
    ;
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

    Flow flow = (Flow) o;

    if (idFlow != null ? !idFlow.equals(flow.idFlow) : flow.idFlow != null) {
      return false;
    }
    return name != null ? name.equals(flow.name) : flow.name == null;
  }
}
