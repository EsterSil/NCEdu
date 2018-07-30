package servise;

import dao.OrderDAO;
import dao.TourDAO;
import model.Order;
import model.Tour;
import org.hibernate.Session;

/**
 * Service intend to interact with database EMPLOYEES
 */
import java.util.List;
import java.util.Set;

public class TourService  implements TableService<Tour>{

    private final Session session;

    public TourService(Session session)  {
        this.session = session;
    }

    public int persist(Tour entity){
        session.beginTransaction();
        TourDAO tourDAO = new TourDAO(session);
        int result = tourDAO.persist(entity);
        session.getTransaction().commit();
        return result;
    }

    public void update(Tour entity){
        session.beginTransaction();
        TourDAO tourDAO = new TourDAO(session);
        tourDAO.update(entity);
        session.getTransaction().commit();
    }


    public Tour findById(int id){
        session.beginTransaction();
        TourDAO tourDAO = new TourDAO(session);
        Tour result = tourDAO.getEntityByID(id);
        session.getTransaction().commit();
        return result;
    }


    public void delete(Tour entity){
        session.beginTransaction();
        TourDAO tourDAO = new TourDAO(session);
        OrderDAO orderDAO = new OrderDAO(session);
        Order order  = entity.getRelatedOrder();
        if(order!= null){
            orderDAO.delete(order);
        }
        tourDAO.delete(entity);
        session.getTransaction().commit();
    }

    public List<Tour> getAll(){
        session.beginTransaction();
        TourDAO tourDAO = new TourDAO(session);
        List<Tour> result = tourDAO.getAll();
        session.getTransaction().commit();
        return result;
    }

}
