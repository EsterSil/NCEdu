package model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Class representing JPA entity for CLIENTS table
 * This table has one many-to-many relationship with ORDERS table. Object may be undefined
 * Table may be created independently.
 */
@Entity
@Table(name = "CLIENTS")
public class Client implements Serializable {

    private int clientID;

    private String name;

    private String lastName;

    private String passport;

    private String contacts;

    private Date dayOdBirth;

    private Set<Order> orders;



    public Client() {
    }

    public Client(String lastName) {
        this.lastName = lastName;
    }

    public Client(String lastName, String passport) {
        this.lastName = lastName;
        this.passport = passport;
    }

    public Client(String name, String passport, Date dayOdBirth) {
        this.name = name;
        this.passport = passport;
        this.dayOdBirth = dayOdBirth;
    }

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "clients")
    public Set<Order> getOrders() {
        return orders;
    }

    public void addOrder(Order order) {
        if (this.orders == null) {
            this.orders = new HashSet<>();
        }
        this.orders.add(order);
    }

    public void setOrders(Set<Order> orders) {
        this.orders = orders;
    }

    @Column
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Column
    public String getContacts() {
        return contacts;
    }

    public void setContacts(String contacts) {
        this.contacts = contacts;
    }

    @Column
    public Date getDayOdBirth() {
        return dayOdBirth;
    }

    public void setDayOdBirth(Date dayOdBirth) {
        this.dayOdBirth = dayOdBirth;
    }

    @Id
    @Column(name = "CLIENT_ID", nullable = false, unique = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getClientID() {
        return clientID;
    }

    public void setClientID(int clientID) {
        this.clientID = clientID;
    }

    @Column
    public String getPassport() {

        return passport;
    }

    public void setPassport(String passport) {
        this.passport = passport;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Client) {
            Client other = (Client) o;
            return this.lastName.equals(other.getLastName())
                    && this.passport.equals(other.getPassport());
        }
        return false;
    }
}
