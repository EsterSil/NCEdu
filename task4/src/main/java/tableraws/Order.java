package tableraws;

import com.fasterxml.jackson.annotation.JsonGetter;

import java.util.Date;
import java.util.Set;
import javax.xml.bind.annotation.*;

@XmlRootElement(name = "order")

public class Order implements TableRaw {

    private int orderID;
    private boolean isPaid = false;
    private Date orderDate;
    private Tour tour;
    private Set<Client> client;
    private Employee employee;

    public Order() {
    }

    @JsonGetter("id")
    @XmlAttribute(name = "id")
    public int getOrderID() {
        return orderID;
    }

    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }

    @JsonGetter("tour")
    @XmlElement(name = "tour")
    public Tour getTour() {
        return tour;
    }

    public void setTour(Tour tour) {
        this.tour = tour;
    }

    @JsonGetter("clients")
    @XmlElementWrapper(name = "clients")
    @XmlElement(name = "client")
    public Set<Client> getClient() {
        return client;
    }

    public void setClient(Set<Client> client) {
        this.client = client;
    }

    @JsonGetter("employee")
    @XmlElement(name = "employee")
    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public Order(int orderID, Date orderDate, Tour tour, Set<Client> client, Employee employee) {
        this.orderID = orderID;
        this.orderDate = orderDate;
        this.tour = tour;
        this.client = client;
        this.employee = employee;
    }

    @JsonGetter("is_paid")
    @XmlElement(name = "is_paid")
    public boolean isPaid() {
        return isPaid;
    }

    public void setPaid(boolean paid) {
        isPaid = paid;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    @JsonGetter("date")
    @XmlElement(name = "date")
    public Date getOrderDate() {
        return orderDate;
    }

    @Override
    public String toString() {
        String result = "Order:\n" + "id: " + getOrderID() + ";\n" +
                "manager: " + employee.getName() + " " + employee.getLastName() + ";\n" +
                "tour: to " + tour.getCountry() + " costs: " + tour.getPrice() + ";\n";
        return result;
    }
}
