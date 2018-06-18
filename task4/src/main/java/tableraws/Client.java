package tableraws;

import com.fasterxml.jackson.annotation.JsonGetter;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Date;

@XmlRootElement(namespace = "tables")
public class Client implements TableRaw {

    private int clientID;
    private String name;
    private String lastName;
    private String passport;
    private String contacts;
    private Date dayOdBirth;

    public Client() {
    }

    public Client(int clientID, String name, String passport, Date dayOdBirth) {
        this.clientID = clientID;
        this.name = name;
        this.passport = passport;
        this.dayOdBirth = dayOdBirth;
    }

    @JsonGetter
    @XmlElement
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @JsonGetter
    @XmlElement
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @JsonGetter
    @XmlElement
    public String getContacts() {
        return contacts;
    }

    public void setContacts(String contacts) {
        this.contacts = contacts;
    }

    @JsonGetter
    @XmlElement
    public Date getDayOdBirth() {
        return dayOdBirth;
    }

    public void setDayOdBirth(Date dayOdBirth) {
        this.dayOdBirth = dayOdBirth;
    }

    @JsonGetter("id")
    @XmlAttribute(name = "id")
    public int getClientID() {
        return clientID;
    }

    public void setClientID(int clientID) {
        this.clientID = clientID;
    }

    @JsonGetter

    @XmlElement
    public String getPassport() {

        return passport;
    }

    public void setPassport(String passport) {
        this.passport = passport;
    }

}
