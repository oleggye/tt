package by.bsac.timetable.entity.builder;

import by.bsac.timetable.entity.Cancellation;
import by.bsac.timetable.entity.Record;
import java.util.Date;

public class CancellationBuilder {

  private Integer idCancellation;
  private Record record;
  private Date dateTo;
  private Date dateFrom;

  public CancellationBuilder buildId(Integer idCancellation) {
    this.idCancellation = idCancellation;
    return this;
  }

  public CancellationBuilder buildRecord(Record record) {
    this.record = record;
    return this;
  }

  public CancellationBuilder buildDateFrom(Date dateFrom) {
    this.dateFrom = dateFrom;
    return this;
  }

  public CancellationBuilder buildDateTo(Date dateTo) {
    this.dateTo = dateTo;
    return this;
  }

  public Cancellation build() {
    Cancellation cancellation = new Cancellation();
    cancellation.setIdCancellation(idCancellation);
    cancellation.setRecord(record);
    cancellation.setDateFrom(dateFrom);
    cancellation.setDateTo(dateTo);
    return cancellation;
  }
  
  public Cancellation copy(Cancellation prototype) {
    Cancellation cancellation = new Cancellation();/*
    cancellation.setIdCancellation(prototype.getIdCancellation());*/
    cancellation.setRecord(prototype.getRecord());
    cancellation.setDateFrom(prototype.getDateFrom());
    cancellation.setDateTo(prototype.getDateTo());
    return cancellation;
  }
}
