package dao;

import java.util.List;

/**
 * general interface for Data Access Object's components for tables
 * provides all necessary methods for DB interactions via Hibernate
 *
 * @param <T> one of tables classes (Client, Employee, Order, Tour)
 */
public interface DAO<T> {
    /**
     * method adds new entity to an appropriate table
     *
     * @param entity object to insert to
     * @return primary key if success
     */
    int persist(T entity);

    /**
     * method deletes given entity from the table if the table
     *
     * @param entity
     */
    void delete(T entity);

    /**
     * method update entity in the table
     *
     * @param entity
     */
    void update(T entity);

    /**
     * method returns entity from the table with given id
     *
     * @param id
     * @return entity
     */
    T getEntityByID(int id);

    /**
     * method return all entities in the table
     *
     * @return list of entities
     */
    List<T> getAll();
}
