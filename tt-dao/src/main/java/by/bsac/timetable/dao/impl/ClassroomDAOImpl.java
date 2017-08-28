package by.bsac.timetable.dao.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;

import by.bsac.timetable.dao.IClassroomDAO;
import by.bsac.timetable.dao.exception.DAOException;
import by.bsac.timetable.entity.Classroom;

@Repository
public class ClassroomDAOImpl extends AbstractHibernateDAO<Classroom, Short>
    implements IClassroomDAO {

  private static final Logger LOGGER = LogManager.getLogger(ClassroomDAOImpl.class.getName());

  public ClassroomDAOImpl() {
    super(Classroom.class);
  }

  @Override
  public void changeClassroom(Classroom oldClassroom, Classroom newClassroom) throws DAOException {
    LOGGER.debug("changeClassroom: [oldClassroom= " + oldClassroom + ", newClassroom= "
        + newClassroom + "]");
    try {

      /*
       * SQLQuery sqlQuery = session
       * .createSQLQuery("UPDATE timetable.record as rec SET rec.id_classroom=? WHERE rec.id_classroom=?;"
       * ); sqlQuery.setShort(0, oldClassroom.getIdClassroom()); sqlQuery.setShort(1,
       * newClassroom.getIdClassroom()); sqlQuery.executeUpdate();
       */

      manager
          .createQuery(
              "UPDATE timetable.record as rec SET rec.id_classroom=?1 WHERE rec.id_classroom=?2;")
          .setParameter(1, oldClassroom.getIdClassroom())
          .setParameter(2, newClassroom.getIdClassroom()).executeUpdate();
    } catch (RuntimeException e) {
      LOGGER.error(e.getMessage(), e);
      throw new DAOException(e.getMessage(), e);
    }
  }
}
