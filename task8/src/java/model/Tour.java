package model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;


/**
 * Class representing JPA entity for TOURS table
 * This table has one one-to-one relationship with ORDERS table. Object may be undefined
 * Table may be created independently.
 */
@Entity
@Table(name = "TOURS")
public class Tour implements Serializable {

    private int tourID;

    private String country;

    private String town;

    private String serviceType;

    private Date beginning;

    private int duration;

    private int price;

    private Order relatedOrder;


    private boolean visa = false;

    public Tour() {
    }

    public Tour(String country, int price) {
        this.country = country;
        this.price = price;
    }

    @Id
    @Column(name = "TOUR_ID", nullable = false, unique = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getTourID() {
        return tourID;
    }

    public void setTourID(int tourID) {
        this.tourID = tourID;
    }

    @OneToOne(mappedBy = "tour", cascade = CascadeType.ALL,
            fetch = FetchType.LAZY)//, optional = false)
    public Order getRelatedOrder() {
        return relatedOrder;
    }

    public void setRelatedOrder(Order relatedOrder) {
        this.relatedOrder = relatedOrder;
    }

    @Column
    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    @Column
    public String getTown() {
        return town;
    }

    public void setTown(String town) {
        this.town = town;
    }

    @Column
    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    @Column
    public Date getBeginning() {
        return beginning;
    }

    public void setBeginning(Date beginning) {
        this.beginning = beginning;
    }

    @Column
    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    @Column
    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    @Column
    public boolean isVisa() {
        return visa;
    }

    public void setVisa(boolean visa) {
        this.visa = visa;
    }


    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();

        builder.append("Tour to ");
        builder.append(this.country);
        builder.append(", price ");
        builder.append(this.price);
        return builder.toString();
    }

    public boolean equals(Object o) {
        if (o instanceof Tour) {
            Tour other = (Tour) o;
            return this.country.equals(other.getCountry())
                    && this.price == other.getPrice();
        }
        return false;
    }
}
