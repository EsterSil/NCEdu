package dao;

import model.Employee;
import org.hibernate.Session;

import java.util.List;

public class EmployeeDAO implements DAO<Employee> {

    private Session currentSession;

    public EmployeeDAO(Session currentSession) {
        this.currentSession = currentSession;
    }

    public int persist(Employee entity) {
        if (currentSession.isOpen()) {
            return (Integer) currentSession.save(entity);
        }
        return 0;
    }

    public void delete(Employee entity) {
        if (currentSession.isOpen()) {
            currentSession.delete(entity);
        }
    }

    public void update(Employee entity) {
        if (currentSession.isOpen()) {
            currentSession.update(entity);
        }
    }

    public Employee getEntityByID(int id) {
        if (currentSession.isOpen()) {
            return currentSession.get(Employee.class, id);
        }
        return null;
    }

    public List<Employee> getAll() {
        List<Employee> result = null;
        String hql = "from Employee";
        if (currentSession.isOpen()) {
            result = (List<Employee>) currentSession.createQuery(hql).list();
        }
        return result;
    }
}
