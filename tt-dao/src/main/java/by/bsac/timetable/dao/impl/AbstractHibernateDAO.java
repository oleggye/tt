package by.bsac.timetable.dao.impl;

import by.bsac.timetable.dao.IGenericDAO;
import java.io.Serializable;
import java.util.Iterator;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Transactional
public abstract class AbstractHibernateDAO<E, P extends Serializable> implements IGenericDAO<E, P> {
  private static final Logger LOGGER = LoggerFactory.getLogger(AbstractHibernateDAO.class.getName());

  @PersistenceContext
  protected EntityManager manager;

  private final Class<E> clazz;

  protected AbstractHibernateDAO(Class<E> typeParameterClass) {
    this.clazz = typeParameterClass;
  }


  @Override
  public void add(E object) {
    LOGGER.debug("add: " + object);
    if (manager.contains(object)) {
      manager.merge(object);
    } else
      manager.persist(object);
    manager.flush();
  }

  @Override
  public void addAll(List<E> listObject) {
    LOGGER.debug("addAll: " + listObject);
    Iterator<E> iter = listObject.listIterator();
    while (iter.hasNext()) {
      manager.persist(iter.next());
    }
  }

  @Override
  public E getById(P id) {
    LOGGER.debug("getById: " + id);
    return manager.find(clazz, id);
  }

  @Override
  public List<E> getAll() {
    LOGGER.debug("getAll");
    TypedQuery<E> query =
        manager.createQuery("select E from " + clazz.getSimpleName() + " E", clazz);
    return query.getResultList();
  }

  @Override
  public void update(E object) {
    LOGGER.debug("update: " + object);
    manager.merge(object);
    manager.flush();
  }

  @Override
  public void updateAll(List<E> listObject) {
    LOGGER.debug("updateAll");
    Iterator<E> iter = listObject.listIterator();
    while (iter.hasNext()) {
      manager.merge(iter.next());
    }
  }

  @Override
  public void delete(E object) {
    LOGGER.debug("delete: " + object);
    manager.remove(manager.contains(object) ? object : manager.merge(object));
    manager.flush();
  }

  @Override
  public void deleteAll(List<E> listObject) {
    LOGGER.debug("deleteAll");
    Iterator<E> iter = listObject.listIterator();
    while (iter.hasNext()) {
      E object = iter.next();
      manager.remove(manager.contains(object) ? object : manager.merge(object));
    }
    manager.flush();
  }
}
