package tables;

import tableraws.Client;
import tableraws.Employee;
import tableraws.Order;
import tableraws.Tour;
import methods.XMLConverter;
import methods.JSONConverter;
import org.junit.Before;
import org.junit.Test;

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
        XMLConverter<OrderTable> jaxb = new XMLConverter<>();
        jaxb.serialize(OrderTable.class, table, tablesXMLPath);
        System.out.println();

    }

    @Test
    public void deserializeTableTest() {
        XMLConverter<OrderTable> jaxb = new XMLConverter<>();
        OrderTable resultTable = jaxb.deserialize(tablesXMLPath,OrderTable.class);
        Set<Order> newOrders = resultTable.getOrders();
        newOrders.forEach((order) -> System.out.println(order.toString()));
    }

    @Test
    public void serializeToJSONTest() {
        JSONConverter<OrderTable> json = new JSONConverter<>();
        json.serialize(OrderTable.class, table, tablesJSONPath);
    }

    @Test
    public void deserializeJSONTest() {
        JSONConverter<OrderTable> json = new JSONConverter<>();
        OrderTable resultTable = (OrderTable) json.deserialize(tablesJSONPath, OrderTable.class);
        Set<Order> newOrders = resultTable.getOrders();
        newOrders.forEach((order) -> System.out.println(order.toString()));
    }
}
