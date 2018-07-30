package dao;

import model.Order;
import org.hibernate.Session;

import java.util.List;

public class OrderDAO implements DAO<Order> {
    private Session currentSession;

    public OrderDAO(Session currentSession) {
        this.currentSession = currentSession;
    }

    @Override
    public int persist(Order entity) {
        if (currentSession.isOpen()) {
            return (Integer) currentSession.save(entity);
        }
        return 0;
    }

    @Override
    public void delete(Order entity) {
        if (currentSession.isOpen()) {
            currentSession.delete(entity);
        }
    }

    @Override
    public void update(Order entity) {
        if (currentSession.isOpen()) {
            currentSession.update(entity);
        }
    }

    @Override
    public Order getEntityByID(int id) {
        if (currentSession.isOpen()) {
            return currentSession.get(Order.class, id);
        }
        return null;
    }

    @Override
    public List<Order> getAll() {
        List<Order> result = null;
        String hql = "from Order";
        if (currentSession.isOpen()) {
            result = (List<Order>) currentSession.createQuery(hql).list();
        }
        return result;
    }
}
