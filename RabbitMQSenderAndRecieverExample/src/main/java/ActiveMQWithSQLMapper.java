import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.core.RowMapper;
import com.rabbitmq.client.*;

public class Receiver {
    private final static String QUEUE_NAME = "hello";

    public static void main(String[] args) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost"); // Replace with RabbitMQ server host if it's not localhost
        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {
            channel.queueDeclare(QUEUE_NAME, false, false, false, null);
            System.out.println("Waiting for messages. To exit press CTRL+C");

            // Create DataSource for JdbcTemplate
            DriverManagerDataSource dataSource = new DriverManagerDataSource();
            dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
            dataSource.setUrl("jdbc:mysql://localhost:3306/demos");
            dataSource.setUsername("your_username");
            dataSource.setPassword("your_password");

            // Create JdbcTemplate with the DataSource
            JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

            // Define RowMapper for mapping query results to Java objects
            RowMapper<Student> rowMapper = (rs, rowNum) -> {
                Student student = new Student();
                student.setId(rs.getLong("id"));
                student.setName(rs.getString("name"));
                // Map other fields if needed
                return student;
            };

            DeliverCallback deliverCallback = (consumerTag, delivery) -> {
                String message = new String(delivery.getBody(), "UTF-8");
                System.out.println("Received: '" + message + "'");
                
                // Perform database operation using JdbcTemplate
                jdbcTemplate.update("INSERT INTO student (name) VALUES (?)", message);
                System.out.println("Inserted into database.");

                // Or you can perform more complex operations like fetching data from the database
                // List<Student> students = jdbcTemplate.query("SELECT * FROM student", rowMapper);
            };

            // Consume messages from RabbitMQ queue
            channel.basicConsume(QUEUE_NAME, true, deliverCallback, consumerTag -> {
            });
        }
    }
}
