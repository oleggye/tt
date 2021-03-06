package by.bsac.timetable.entity;

import static javax.persistence.GenerationType.IDENTITY;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "subject")
public class Subject implements java.io.Serializable, Cloneable, IName {

  private static final long serialVersionUID = -1879039518302025880L;

  @JsonProperty(value="id")
  private Short idSubject;
  @JsonIgnore
  private Chair chair;
  @JsonProperty(value="name")
  private String nameSubject;
  @JsonProperty(value="educationLevel")
  private Byte eduLevel;
  @JsonProperty(value="abbreviation")
  private String abnameSubject;
  @JsonIgnore
  private Set<Record> records = new HashSet<>(0);

  public Subject() {}


  public Subject(Chair chair, String nameSubject, Byte eduLevel, String abnameSubject) {
    this.chair = chair;
    this.nameSubject = nameSubject;
    this.eduLevel = eduLevel;
    this.abnameSubject = abnameSubject;
  }

  public Subject(Chair chair, String nameSubject, Byte eduLevel, String abnameSubject,
      Set<Record> records) {
    this.chair = chair;
    this.nameSubject = nameSubject;
    this.eduLevel = eduLevel;
    this.abnameSubject = abnameSubject;
    this.records = records;
  }

  public Subject(Chair chair, String nameSubject, byte eduLevel) {
    this.chair = chair;
    this.nameSubject = nameSubject;
    this.eduLevel = eduLevel;
  }


  @Id
  @GeneratedValue(strategy = IDENTITY)
  @Column(name = "id_subject", unique = true, nullable = false)
  public Short getIdSubject() {
    return this.idSubject;
  }

  public void setIdSubject(Short idSubject) {
    this.idSubject = idSubject;
  }

  @ManyToOne(fetch = FetchType.EAGER/*, cascade = {CascadeType.MERGE, CascadeType.REMOVE}*/)
  @JoinColumn(name = "id_chair", nullable = false)
  public Chair getChair() {
    return this.chair;
  }

  public void setChair(Chair chair) {
    this.chair = chair;
  }


  @Column(name = "name_subject", nullable = false, length = 80)
  public String getNameSubject() {
    return this.nameSubject;
  }

  public void setNameSubject(String nameSubject) {
    this.nameSubject = nameSubject;
  }


  @Column(name = "edu_level", nullable = false)
  public Byte getEduLevel() {
    return this.eduLevel;
  }

  public void setEduLevel(Byte eduLevel) {
    this.eduLevel = eduLevel;
  }


  @Column(name = "abname_subject", nullable = false, length = 20)
  public String getAbnameSubject() {
    return this.abnameSubject;
  }

  public void setAbnameSubject(String abnameSubject) {
    this.abnameSubject = abnameSubject;
  }

  @OneToMany(fetch = FetchType.LAZY, mappedBy = "subject")
  public Set<Record> getRecords() {
    return this.records;
  }

  public void setRecords(Set<Record> records) {
    this.records = records;
  }

  @Override
  @Transient
  @JsonIgnore
  public String getName() {
    return this.getNameSubject();
  }

  @Override
  public String toString() {
    return "Subject [idSubject=" + idSubject + ", chair=" + chair + ", nameSubject=" + nameSubject
        + ", eduLevel=" + eduLevel + ", abnameSubject=" + abnameSubject + "]";
  }

  @Override
  protected Object clone() throws CloneNotSupportedException {
    return super.clone();
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((abnameSubject == null) ? 0 : abnameSubject.hashCode());
    result = prime * result + ((chair == null) ? 0 : chair.hashCode());
    result = prime * result + eduLevel;
    result = prime * result + idSubject;
    result = prime * result + ((nameSubject == null) ? 0 : nameSubject.hashCode());
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
    Subject other = (Subject) obj;
    if (idSubject == null) {
      if (other.idSubject != null)
        return false;
    } else if (!idSubject.equals(other.idSubject))
      return false;
    return true;
  }
}
