package model;


import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Class representing JPA entity for ORDERS table
 * This table has many-to-many relationship with CLIENTS table: every order connected at las with ine client entity.
 * Object should be defined
 * This table has many-to-one relationship with EMPLOYEES table: every order belongs to one concrete manager,
 * but the manager could have many orders. Object may not be defined
 * This table has one-to-one relationship with TOURS table: every tour associated with concrete tour.
 * Object may not be defined
 * Table CAN NOT be created independently and  required definitely related employee object and probably tour, clients
 */
@Entity
@Table(name = "ORDERS")
public class Order implements Serializable {


    private int orderID;

    private boolean isPaid = false;

    private Date orderDate;

    private Tour tour;

    private Set<Client> clients;

    private Employee responsibleEmployee;

    public Order() {
    }

    public Order(Set<Client> clients, Employee responsibleEmployee) {
        this.clients = clients;
        this.responsibleEmployee = responsibleEmployee;
    }

    public Order(Tour tour, Employee employee, Set<Client> clients) {
        this.tour = tour;
        this.responsibleEmployee = employee;
        this.clients = clients;
    }

    public Order(Employee responsibleEmployee) {
        this.responsibleEmployee = responsibleEmployee;
    }

    public Order(Tour tour, Employee responsibleEmployee) {
        this.tour = tour;
        this.responsibleEmployee = responsibleEmployee;
    }

    @Id
    @Column(name = "ORDER_ID", nullable = false, unique = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getOrderID() {
        return orderID;
    }

    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "tour_id", nullable = true)
    public Tour getTour() {
        return tour;
    }

    public void setTour(Tour tour) {
        this.tour = tour;
    }

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "order_clients", joinColumns = {
            @JoinColumn(name = "ORDER_ID", nullable = false, updatable = false)},
            inverseJoinColumns = {@JoinColumn(name = "CLIENT_ID",
                    nullable = true, updatable = false)})
    public Set<Client> getClients() {
        return clients;
    }

    public void addClient(Client client) {
        if (this.clients == null) {
            this.clients = new HashSet<>();
        }
        this.clients.add(client);
    }

    public void setClients(Set<Client> client) {
        this.clients = client;
    }

    @ManyToOne(fetch = FetchType.LAZY)//, cascade = CascadeType.ALL)
    @JoinColumn(name = "employee_id", nullable = false)//, updatable = false, insertable = false)
    public Employee getResponsibleEmployee() {
        return responsibleEmployee;
    }

    public void setResponsibleEmployee(Employee responsibleEmployee) {
        this.responsibleEmployee = responsibleEmployee;
    }


    @Column
    public boolean isPaid() {
        return isPaid;
    }

    public void setPaid(boolean paid) {
        isPaid = paid;
    }

    @Column
    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    @Override
    public String toString() {
        String result = "Order:\n" + "id: " + getOrderID() + ";\n" +
                "manager: " + responsibleEmployee.getName() + " " + responsibleEmployee.getLastName() + ";\n" +
                "tour: to " + tour.getCountry() + " costs: " + tour.getPrice() + ";\n";
        return result;
    }

    public boolean equals(Object o) {
        if (o instanceof Order) {
            Order other = (Order) o;
            if (this.responsibleEmployee.equals(other.getResponsibleEmployee())) {
                if (this.tour == null || this.tour.equals(other.getTour())) {
                    return true;
                }
            }
        }
        return false;
    }
}
