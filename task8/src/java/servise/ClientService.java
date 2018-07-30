package servise;

import dao.ClientDAO;
import dao.OrderDAO;
import model.Client;
import model.Order;
import org.hibernate.Session;

import java.util.List;
import java.util.Set;

/**
 * Service intend to interact with database CLIENTS
 */
public class ClientService implements TableService<Client> {
    private final Session session;

    public ClientService(Session session) {
        this.session = session;
    }

    @Override
    public int persist(Client entity) {
        session.beginTransaction();
        ClientDAO clientDAO = new ClientDAO(session);
        int result = clientDAO.persist(entity);
        session.getTransaction().commit();
        return result;
    }

    @Override
    public void update(Client entity) {
        session.beginTransaction();
        ClientDAO clientDAO = new ClientDAO(session);
        clientDAO.update(entity);
        session.getTransaction().commit();
    }

    @Override
    public Client findById(int id) {
        session.beginTransaction();
        ClientDAO clientDAO = new ClientDAO(session);
        Client result = clientDAO.getEntityByID(id);
        session.getTransaction().commit();
        return result;
    }

    @Override
    public void delete(Client entity) {
        session.beginTransaction();
        ClientDAO clientDAO = new ClientDAO(session);
        OrderDAO orderDAO = new OrderDAO(session);
        Set<Order> orders  = entity.getOrders();
        if(orders!= null){
            for(Order o: orders){
                o.getClients().remove(entity);
                orderDAO.update(o);
            }
        }

        clientDAO.delete(entity);
        session.getTransaction().commit();
    }

    @Override
    public List<Client> getAll() {
        session.beginTransaction();
        ClientDAO clientDAO = new ClientDAO(session);
        List<Client> result = clientDAO.getAll();
        session.getTransaction().commit();
        return result;
    }
}
