package tableraws;

import com.fasterxml.jackson.annotation.JsonGetter;

import java.util.Date;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(namespace = "tables")
public class Tour implements TableRaw {
    @XmlAttribute(name = "id")
    private int tourID;

    private String country;

    private String town;

    private String serviceType;

    private Date beginning;

    private int duration;

    private int price;

    private boolean visa = false;

    public Tour() {
    }

    public Tour(int tour_id, String country, Date beginning, int duration, int price) {
        this.tourID = tour_id;
        this.country = country;
        this.beginning = beginning;
        this.duration = duration;
        this.price = price;
    }

    @JsonGetter
    @XmlElement
    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    @JsonGetter
    @XmlElement
    public String getTown() {
        return town;
    }

    public void setTown(String town) {
        this.town = town;
    }

    @JsonGetter
    @XmlElement
    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    @JsonGetter
    @XmlElement
    public Date getBeginning() {
        return beginning;
    }

    public void setBeginning(Date beginning) {
        this.beginning = beginning;
    }

    @JsonGetter
    @XmlElement
    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    @JsonGetter
    @XmlElement
    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    @JsonGetter
    @XmlElement
    public boolean isVisa() {
        return visa;
    }

    public void setVisa(boolean visa) {
        this.visa = visa;
    }

}
