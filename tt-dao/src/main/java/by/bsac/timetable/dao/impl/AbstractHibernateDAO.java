package by.bsac.timetable.dao.impl;

import java.io.Serializable;
import java.util.Iterator;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.transaction.annotation.Transactional;

import by.bsac.timetable.dao.IGenericDAO;
import by.bsac.timetable.dao.exception.DAOException;

@Transactional
public abstract class AbstractHibernateDAO<E, PK extends Serializable>
    implements IGenericDAO<E, PK> {
  private static final Logger LOGGER = LogManager.getLogger(AbstractHibernateDAO.class.getName());

  @PersistenceContext
  protected EntityManager manager;

  private final Class<E> clazz;

  protected AbstractHibernateDAO(Class<E> typeParameterClass) {
    this.clazz = typeParameterClass;
  }


  @Override
  public void add(E object) throws DAOException {
    LOGGER.debug("add: " + object);
    try {
      if (manager.contains(object)) {
        manager.merge(object);
      } else
        manager.persist(object);
      manager.flush();

    } catch (RuntimeException e) {
      LOGGER.error(e.getMessage(), e);
      throw new DAOException(e.getMessage(), e);
    }
  }

  @Override
  public void addAll(List<E> listObject) throws DAOException {
    LOGGER.debug("addAll: " + listObject);
    try {
      Iterator<E> iter = listObject.listIterator();
      while (iter.hasNext()) {
        manager.persist(iter.next());
      }
    } catch (RuntimeException e) {
      LOGGER.error(e.getMessage(), e);
      throw new DAOException(e.getMessage(), e);
    }
  }

  @Override
  public E getById(PK id) throws DAOException {
    LOGGER.debug("getById: " + id);
    try {
      return manager.find(clazz, id);
    } catch (RuntimeException e) {
      LOGGER.error(e.getMessage(), e);
      throw new DAOException(e.getMessage(), e);
    }
  }

  @Override
  public List<E> getAll() throws DAOException {
    LOGGER.debug("getAll");
    try {
      /*
       * List<ITable> tableList = manager.createNativeQuery("show tables").getResultList();
       * for(ITable ob: tableList) System.out.println(ob);
       */
      TypedQuery<E> query =
          manager.createQuery("select E from " + clazz.getSimpleName() + " E", clazz);
      return query.getResultList();
    } catch (RuntimeException e) {
      LOGGER.error(e.getMessage(), e);
      throw new DAOException(e.getMessage(), e);
    }
  }

  @Override
  public void update(E object) throws DAOException {
    LOGGER.debug("update: " + object);
    try {
      manager.merge(object);
      manager.flush();
    } catch (RuntimeException e) {
      LOGGER.error(e.getMessage(), e);
      throw new DAOException(e.getMessage(), e);
    }
  }

  @Override
  public void updateAll(List<E> listObject) throws DAOException {
    LOGGER.debug("updateAll");
    try {
      Iterator<E> iter = listObject.listIterator();
      while (iter.hasNext()) {
        manager.merge(iter.next());
      }
    } catch (RuntimeException e) {
      LOGGER.error(e.getMessage(), e);
      throw new DAOException(e.getMessage(), e);
    }
  }

  @Override
  public void delete(E object) throws DAOException {
    LOGGER.debug("delete: " + object);
    try {
      manager.remove(manager.contains(object) ? object : manager.merge(object));
      manager.flush();
    } catch (RuntimeException e) {
      LOGGER.error(e.getMessage(), e);
      throw new DAOException(e.getMessage(), e);
    }
  }

  @Override
  public void deleteAll(List<E> listObject) throws DAOException {
    LOGGER.debug("deleteAll");
    try {
      Iterator<E> iter = listObject.listIterator();
      while (iter.hasNext()) {
        E object = iter.next();
        manager.remove(manager.contains(object) ? object : manager.merge(object));
      }
      manager.flush();
    } catch (RuntimeException e) {
      LOGGER.error(e.getMessage(), e);
      throw new DAOException(e.getMessage(), e);
    }
  }
}
