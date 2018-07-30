package dao;

import model.Tour;
import org.hibernate.Session;

import java.util.List;

public class TourDAO implements DAO<Tour> {
    Session currentSession;

    public TourDAO(Session currentSession) {
        this.currentSession = currentSession;
    }

    public int persist(Tour entity) {
        if (currentSession.isOpen()) {
            return (Integer) currentSession.save(entity);
        }
        return 0;
    }

    public void delete(Tour entity) {
        if (currentSession.isOpen()) {
            currentSession.delete(entity);
        }
    }

    public void update(Tour entity) {
        if (currentSession.isOpen()) {
            currentSession.update(entity);
        }
    }

    public Tour getEntityByID(int id) {
        if (currentSession.isOpen()) {
            return currentSession.get(Tour.class, id);
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    public List<Tour> getAll() {
        List<Tour> result = null;
        String hql = "from Tour";
        if (currentSession.isOpen()) {
            result = (List<Tour>) currentSession.createQuery(hql).list();
        }
        return result;
    }
}
