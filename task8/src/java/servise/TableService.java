package servise;


import org.hibernate.Session;

import java.util.List;

/**
 * general interface for services working with table
 * Highest level of DAO pattern requires opened session
 * Provides general methods for API interacting with data base
 *
 * @param <T>
 */
public interface TableService<T> {

    /**
     * upper level method
     * method adds new entity to an appropriate table if the table is independent
     *
     * @param entity entity object to insert to
     * @return primary key if success
     */
    int persist(T entity);

    /**
     * method update entity in the table and related tables if it required
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
    T findById(int id);

    /**
     * method deletes given entity from the table if the table is independent
     * and update data in related tables in cascade relationship
     *
     * @param entity
     */
    void delete(T entity);

    /**
     * method return all entities in the table
     *
     * @return list of entities
     */
    List<T> getAll();
}
