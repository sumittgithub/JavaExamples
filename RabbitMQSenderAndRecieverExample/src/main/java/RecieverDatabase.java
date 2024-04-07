import com.rabbitmq.client.*;
import com.rabbitmq.client.Connection;

import java.sql.*;

public class RecieverDatabase {
    private final static String QUEUE_NAME = "hello";

    public static void main(String[] args) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost"); // Replace with RabbitMQ server host if it's not localhost
        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {
            channel.queueDeclare(QUEUE_NAME, false, false, false, null);
            System.out.println("Waiting for messages. To exit press CTRL+C");

            // JDBC connection setup
            String url = "jdbc:mysql://localhost:3306/demos";
            String username = "root";
            String password = "root";
            try (java.sql.Connection dbConnection = DriverManager.getConnection(url, username, password);
                 Statement statement = dbConnection.createStatement()) {
                DeliverCallback deliverCallback = (consumerTag, delivery) -> {
                    String message = new String(delivery.getBody(), "UTF-8");
                    System.out.println("Received: '" + message + "'");
                    // Do something with the received message, such as updating the database
                };

                channel.basicConsume(QUEUE_NAME, true, deliverCallback, consumerTag -> {
                });
            }
        }
    }
}
