package by.bsac.timetable.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "cancellation")
public class Cancellation implements java.io.Serializable {

  private static final long serialVersionUID = -9036988939820334961L;

  private Integer idCancellation;
  private Record record;
  private Date dateTo;
  private Date dateFrom;

  public Cancellation() {}

  public Cancellation(Record record, Date dateTo, Date dateFrom) {
    this.record = record;
    this.dateTo = dateTo;
    this.dateFrom = dateFrom;
  }

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id_cancellation", unique = true, nullable = false)
  public Integer getIdCancellation() {
    return this.idCancellation;
  }

  public void setIdCancellation(Integer idCancellation) {
    this.idCancellation = idCancellation;
  }

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "id_record", nullable = false)
  public Record getRecord() {
    return this.record;
  }

  public void setRecord(Record record) {
    this.record = record;
  }

  @Temporal(TemporalType.DATE)
  @Column(name = "date_to", nullable = false, length = 10)
  public Date getDateTo() {
    return this.dateTo;
  }

  public void setDateTo(Date dateTo) {
    this.dateTo = dateTo;
  }

  @Temporal(TemporalType.DATE)
  @Column(name = "date_from", nullable = false, length = 10)
  public Date getDateFrom() {
    return this.dateFrom;
  }

  public void setDateFrom(Date dateFrom) {
    this.dateFrom = dateFrom;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((dateFrom == null) ? 0 : dateFrom.hashCode());
    result = prime * result + ((dateTo == null) ? 0 : dateTo.hashCode());
    result = prime * result + ((idCancellation == null) ? 0 : idCancellation.hashCode());
    result = prime * result + ((record == null) ? 0 : record.hashCode());
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
    Cancellation other = (Cancellation) obj;
    if (dateFrom == null) {
      if (other.dateFrom != null)
        return false;
    } else if (!dateFrom.equals(other.dateFrom))
      return false;
    if (dateTo == null) {
      if (other.dateTo != null)
        return false;
    } else if (!dateTo.equals(other.dateTo))
      return false;
    if (idCancellation == null) {
      if (other.idCancellation != null)
        return false;
    } else if (!idCancellation.equals(other.idCancellation))
      return false;
    if (record == null) {
      if (other.record != null)
        return false;
    } else if (!record.equals(other.record))
      return false;
    return true;
  }

  @Override
  public String toString() {
    return "Cancellation [idCancellation=" + idCancellation + ", record=" + record + ", dateTo="
        + dateTo + ", dateFrom=" + dateFrom + "]";
  }
}
