import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.component.rabbitmq.RabbitMQComponent;
import org.apache.camel.component.rabbitmq.RabbitMQConstants;
import java.sql.*;

public class SenderCamelWithActiveMQ {
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
                from("jdbc:" + url + "?user=" + username + "&password=" + password
                    + "&autoCommit=true&delay=1000")
                .to("rabbitmq:queue:hello");
            }
        });

        context.start();
        Thread.sleep(10000); // wait 10 seconds before stopping the context
        context.stop();
    }
}
