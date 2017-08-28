package by.bsac.timetable.entity;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "cancellation", catalog = "timetable")
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
  @GeneratedValue(strategy = IDENTITY)
  @Column(name = "id_cancellation", unique = true, nullable = false)
  public Integer getIdCancellation() {
    return this.idCancellation;
  }

  public void setIdCancellation(Integer idCancellation) {
    this.idCancellation = idCancellation;
  }

  @ManyToOne(fetch = FetchType.LAZY)
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



}


