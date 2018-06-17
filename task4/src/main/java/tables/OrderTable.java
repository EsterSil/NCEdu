package tables;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonRootName;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.HashSet;
import java.util.Set;

@JsonRootName(value = "table")
@XmlRootElement(namespace = "tables")
public class OrderTable implements Table {

    private Set<Order> orders = new HashSet<Order>();

    @JsonGetter("orders")
    @XmlElementWrapper(name = "orders")
    @XmlElement(name = "order")
    public Set<Order> getOrders() {
        return orders;
    }

    public void setOrders(Set<Order> orders) {
        this.orders = orders;
    }
}
