import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.component.rabbitmq.RabbitMQComponent;
import java.sql.*;

public class RecieverCamelWithActiveMQ {
    public static void main(String[] args) throws Exception {
        CamelContext context = new DefaultCamelContext();
        
        // Add RabbitMQ component
        RabbitMQComponent rabbitmq = new RabbitMQComponent();
        context.addComponent("rabbitmq", rabbitmq);

        // JDBC connection setup
        String url = "jdbc:mysql://localhost:3306/demos";
        String username = "root";
        String password = "root";

        context.addRoutes(new RouteBuilder() {
            public void configure() {
                from("rabbitmq:queue:hello")
                .process(exchange -> {
                    String message = exchange.getIn().getBody(String.class);
                    System.out.println("Received: " + message);

                    // Do something with the received message, such as updating the database
                })
                .to("jdbc:" + url + "?user=" + username + "&password=" + password);
            }
        });

        context.start();
        Thread.sleep(10000); // wait 10 seconds before stopping the context
        context.stop();
    }
}
