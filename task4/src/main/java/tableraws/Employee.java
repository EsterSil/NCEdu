package tableraws;

import com.fasterxml.jackson.annotation.JsonGetter;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(namespace = "tables")
public class Employee implements TableRaw {


    private int employeeID;
    private String name;
    private String lastName;
    private String post;
    private String contacts;

    public Employee() {
    }

    public Employee(int employeeID, String name, String lastName, String post) {
        this.employeeID = employeeID;
        this.name = name;
        this.lastName = lastName;
        this.post = post;
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
    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }

    @JsonGetter("id")
    @XmlAttribute(name = "id")
    public int getEmployeeID() {
        return employeeID;
    }

    public void setEmployeeID(int employeeID) {
        this.employeeID = employeeID;
    }

    @JsonGetter
    @XmlElement
    public String getContacts() {
        return contacts;
    }

    public void setContacts(String contacts) {
        this.contacts = contacts;
    }

}
