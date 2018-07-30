package dao;

import model.Client;
import org.hibernate.Session;

import java.util.List;

public class ClientDAO implements DAO<Client> {
    private Session currentSession;

    public ClientDAO(Session currentSession) {
        this.currentSession = currentSession;
    }

    /**
     *
     * @param entity object to insert to
     * @return
     */
    @Override
    public int persist(Client entity) {
        if (currentSession.isOpen()){
            return (Integer) currentSession.save(entity);
        }
        return 0;
    }

    @Override
    public void delete(Client entity) {
        if (currentSession.isOpen()){
            currentSession.delete(entity);
        }
    }

    @Override
    public void update(Client entity) {
        if (currentSession.isOpen()){
            currentSession.update(entity);
        }
    }

    @Override
    public Client getEntityByID(int id) {
        if (currentSession.isOpen()) {
            return currentSession.get(Client.class, id);
        }
        return null;
    }

    @Override
    public List<Client> getAll() {
        List<Client> result = null;
        String hql = "from Client";
        if (currentSession.isOpen()) {
            result = (List<Client>) currentSession.createQuery(hql).list();
        }
        return  result;
    }
    public List<?> getAll_() {
        List<?> result = null;
        String hql = "from Client";
        if (currentSession.isOpen()) result = (List<?>) currentSession.createQuery(hql).list();
        return  result;
    }
}
