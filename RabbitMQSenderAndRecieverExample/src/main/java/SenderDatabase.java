import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import java.sql.*;

public class SenderDatabase {
    private final static String QUEUE_NAME = "hello";

    public static void main(String[] args) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost"); // Replace with RabbitMQ server host if it's not localhost
        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {
            channel.queueDeclare(QUEUE_NAME, false, false, false, null);
            
            // JDBC connection setup
            String url = "jdbc:mysql://localhost:3306/demos";
            String username = "root";
            String password = "root";
            try (java.sql.Connection dbConnection = DriverManager.getConnection(url, username, password);
                 Statement statement = ((java.sql.Connection) dbConnection).createStatement();
                 ResultSet resultSet = statement.executeQuery("SELECT * FROM students")) {
                while (resultSet.next()) {
                    String message = resultSet.getString("name"); // Assuming there's a 'name' column in the 'student' table
                    channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
                    System.out.println("Sent: '" + message + "'");
                }
            }
        }
    }
}
