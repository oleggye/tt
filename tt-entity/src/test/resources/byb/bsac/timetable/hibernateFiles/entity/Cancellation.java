package by.bsac.timetable.hibernateFiles.entity;


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

@Entity
@Table(name = "cancellation")
public class Cancellation implements java.io.Serializable {

  private static final long serialVersionUID = 1L;

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
  @Column(name = "id_cancellation")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  public Integer getIdCancellation() {
    return this.idCancellation;
  }

  public void setIdCancellation(Integer idCancellation) {
    this.idCancellation = idCancellation;
  }

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "id_chair", nullable = false)
  public Record getRecord() {
    return this.record;
  }

  public void setRecord(Record record) {
    this.record = record;
  }
  
  @Column(name = "date_to", length = 10, nullable = false)
  public Date getDateTo() {
    return this.dateTo;
  }

  public void setDateTo(Date dateTo) {
    this.dateTo = dateTo;
  }

  @Column(name = "date_from", length = 10, nullable = false)
  public Date getDateFrom() {
    return this.dateFrom;
  }

  public void setDateFrom(Date dateFrom) {
    this.dateFrom = dateFrom;
  }
}
