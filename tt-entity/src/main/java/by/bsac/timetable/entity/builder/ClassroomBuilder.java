package by.bsac.timetable.entity.builder;

import by.bsac.timetable.entity.Classroom;

public class ClassroomBuilder {

  private short idClassroom;
  private String name;
  private byte building;

  public ClassroomBuilder buildId(short idClassroom) {
    this.idClassroom = idClassroom;
    return this;
  }

  public ClassroomBuilder buildName(String name) {
    this.name = name;
    return this;
  }

  public ClassroomBuilder buildBuilding(byte building) {
    this.building = building;
    return this;
  }

  public Classroom build() {
    Classroom classroom = new Classroom();
    classroom.setIdClassroom(idClassroom);
    classroom.setName(name);
    classroom.setBuilding(building);
    return classroom;
  }
}
