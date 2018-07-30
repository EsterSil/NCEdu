package model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;


/**
 * Class representing JPA entity for EMPLOYEES table
 * This table has one one-to-many relationship with ORDERS table. Object may be undefined
 * Table may be created independently.
 */
@Entity
@Table(name = "employees")
public class Employee implements Serializable {

    private int employeeID;

    private String name;

    private String lastName;

    private String post;

    private String contacts;

    private Set<Order> orders;


    public Employee() {
    }

    public Employee(String lastName, String post) {
        this.lastName = lastName;
        this.post = post;
    }

    @OneToMany(mappedBy = "responsibleEmployee")
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

    @Column(nullable = false)
    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }

    @Id
    @Column(name = "employee_id", nullable = false, unique = true, insertable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getEmployeeID() {
        return employeeID;
    }

    public void setEmployeeID(int employeeID) {
        this.employeeID = employeeID;
    }

    @Column
    public String getContacts() {
        return contacts;
    }

    public void setContacts(String contacts) {
        this.contacts = contacts;
    }

    @Override
    public String toString() {

        StringBuilder builder = new StringBuilder();
        builder.append("Manager: id ");
        builder.append(this.employeeID);
        builder.append(", name ");
        builder.append(this.lastName);
        builder.append(", post ");
        builder.append(this.post);
        return builder.toString();

    }

    public boolean equals(Object o) {
        if (o instanceof Employee) {
            Employee other = (Employee) o;
            return this.lastName.equals(other.getLastName())
                    && this.post.equals(other.getPost());
        }
        return false;
    }

}
