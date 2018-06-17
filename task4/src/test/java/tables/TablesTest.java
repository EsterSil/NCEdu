package tables;

import methods.JAXBToXML;
import methods.JacksonToJSON;
import org.junit.Before;
import org.junit.Test;

import javax.xml.bind.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.util.*;

public class TablesTest {
    private static final String tablesXMLPath = "tables-jaxb.xml";
    private static final String tablesJSONPath = "tables-json.json";
    OrderTable table;

    @Before
    public void init() {
        Set<Order> orders = new HashSet<Order>();
        Set<Client> clientsToMoscow = new HashSet<Client>();
        Set<Client> clientsToSpain = new HashSet<Client>();

        Tour tour1 = new Tour(1, "Russia", new Date(), 10, 12000);
        Tour tour2 = new Tour(2, "Spain", new Date(), 5, 7000);

        Client client1 = new Client(1, "Ivan", "123456", new Date());
        clientsToMoscow.add(client1);
        Client client2 = new Client(2, "Vasily", "654321", new Date());
        Client client3 = new Client(3, "Maria", "456789", new Date());
        clientsToSpain.add(client2);
        clientsToSpain.add(client3);

        Employee employee1 = new Employee(1, "Peter", "Smith", "manager");


        Order order1 = new Order(1, new Date(), tour1, clientsToMoscow, employee1);
        Order order2 = new Order(2, new Date(), tour2, clientsToSpain, employee1);
        orders.add(order1);
        orders.add(order2);

        table = new OrderTable();
        table.setOrders(orders);

    }

    @Test
    public void serializeTableTest() {
        JAXBToXML<OrderTable> jaxb = new JAXBToXML<>();
        jaxb.serializeTable(OrderTable.class, table, tablesXMLPath);
        System.out.println();

    }

    @Test
    public void deserializeTableTest() {
        JAXBToXML<OrderTable> jaxb = new JAXBToXML<>();
        OrderTable resultTable = (OrderTable) jaxb.deserializeTable(tablesXMLPath);
        Set<Order> newOrders = resultTable.getOrders();
        newOrders.forEach((order) -> System.out.println(order.toString()));
    }

    @Test
    public void serializeToJSONTest() {
        JacksonToJSON<OrderTable> json = new JacksonToJSON<>();
        json.serializeToJSON(table, tablesJSONPath);
    }

    @Test
    public void deserializeJSONTest() {
        JacksonToJSON<OrderTable> json = new JacksonToJSON<>();
        OrderTable resultTable = (OrderTable) json.deserializeJSON(tablesJSONPath, OrderTable.class);
        Set<Order> newOrders = resultTable.getOrders();
        newOrders.forEach((order) -> System.out.println(order.toString()));
    }
}
