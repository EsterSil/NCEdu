package servise;


import dao.ClientDAO;
import dao.EmployeeDAO;
import dao.OrderDAO;
import dao.TourDAO;
import model.Client;
import model.Order;
import model.Tour;
import org.hibernate.Session;

import java.util.List;
import java.util.Set;

public class OrderService implements TableService<Order> {
    private final Session session;

    public OrderService(Session session) {
        this.session = session;
    }

    @Override
    public int persist(Order entity) {
        session.beginTransaction();
        TourDAO tourDAO = new TourDAO(session);
        OrderDAO orderDAO = new OrderDAO(session);
        EmployeeDAO employeeDAO = new EmployeeDAO(session);
        ClientDAO clientDAO = new ClientDAO(session);
        if (entity.getTour() != null) {
            entity.getTour().setRelatedOrder(entity);
            tourDAO.update(entity.getTour());
        }
        if (entity.getClients() != null) {
            Set<Client> clients = entity.getClients();
            for (Client c : clients) {
                c.addOrder(entity);
                clientDAO.update(c);
            }
        }
        entity.getResponsibleEmployee().addOrder(entity);
        employeeDAO.update(entity.getResponsibleEmployee());
        int result = orderDAO.persist(entity);
        session.getTransaction().commit();
        return result;
    }

    @Override
    public void update(Order entity) {
        session.beginTransaction();
        OrderDAO orderDAO = new OrderDAO(session);
        orderDAO.update(entity);
        session.getTransaction().commit();

    }

    @Override
    public Order findById(int id) {
        session.beginTransaction();
        OrderDAO orderDAO = new OrderDAO(session);
        Order result = orderDAO.getEntityByID(id);
        session.getTransaction().commit();
        return result;
    }

    @Override
    public void delete(Order entity) {
        session.beginTransaction();
        OrderDAO orderDAO = new OrderDAO(session);
        EmployeeDAO employeeDAO = new EmployeeDAO(session);
        ClientDAO clientDAO = new ClientDAO(session);
        TourDAO tourDAO = new TourDAO(session);
        entity.getResponsibleEmployee().getOrders().remove(entity);
        employeeDAO.update(entity.getResponsibleEmployee());
        Tour resultTour = entity.getTour();
        if (resultTour != null) {
            tourDAO.delete(resultTour);
        }
        Set<Client> clients = entity.getClients();
        if (clients != null){
            for (Client c: clients){
                c.getOrders().remove(entity);
                clientDAO.update(c);
            }
        }
        orderDAO.delete(entity);
        session.getTransaction().commit();
    }

    @Override
    public List<Order> getAll() {
        session.beginTransaction();
        OrderDAO orderDAO = new OrderDAO(session);
        List<Order> result = orderDAO.getAll();
        session.getTransaction().commit();
        return result;
    }
}