package servise;

import dao.EmployeeDAO;
import dao.OrderDAO;
import model.Employee;
import model.Order;
import org.hibernate.Session;
import java.util.List;
import java.util.Set;

/**
 * Service intend to interact with database EMPLOYEES
 */
public class EmployeeService implements TableService<Employee> {

    private final Session session;

    public EmployeeService(Session session) {
        this.session = session;
    }

    @Override
    public int persist(Employee entity) {
        session.beginTransaction();
        EmployeeDAO employeeDAO = new EmployeeDAO(session);
        int result = employeeDAO.persist(entity);
        session.getTransaction().commit();
        return result;
    }

    @Override
    public void update(Employee entity) {
        session.beginTransaction();
        EmployeeDAO employeeDAO = new EmployeeDAO(session);
        employeeDAO.update(entity);
        session.getTransaction().commit();
    }

    @Override
    public Employee findById(int id) {
        session.beginTransaction();
        EmployeeDAO employeeDAO = new EmployeeDAO(session);
        Employee result = employeeDAO.getEntityByID(id);
        session.getTransaction().commit();
        return result;
    }

    @Override
    public void delete(Employee entity) {
        session.beginTransaction();
        EmployeeDAO employeeDAO = new EmployeeDAO(session);
        OrderDAO orderDAO = new OrderDAO(session);
        Set<Order> orders  = entity.getOrders();
        if(orders!= null){
            for(Order o: orders){
                orderDAO.delete(o);
            }
        }
        employeeDAO.delete(entity);
        session.getTransaction().commit();
    }

    @Override
    public List<Employee> getAll() {
        session.beginTransaction();
        EmployeeDAO employeeDAO = new EmployeeDAO(session);
        List<Employee> result = employeeDAO.getAll();
        session.getTransaction().commit();
        return result;
    }
}