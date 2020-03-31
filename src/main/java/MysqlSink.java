import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.functions.sink.RichSinkFunction;
import org.apache.flink.streaming.api.functions.sink.SinkFunction;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MysqlSink extends RichSinkFunction<Entity> {

    // Read RDS connection information from the environment
    String driver = "com.mysql.jdbc.Driver";
    private PreparedStatement ps = null;
    private Connection connection = null;
    String dbName = "testdb";
    String userName = "xxx";
    String password = "xxx";
    String hostname = "xxx";
    String port = "3306";
    String jdbcUrl = "jdbc:mysql://" + hostname + ":" + port + "/" + dbName + "?user=" + userName + "&password=" + password;
    static int message_from_kafka = 0;

    @Override
    public void open(Configuration parameters) throws Exception {
        super.open(parameters);
        //loading jdbc driver
        Class.forName(driver);
        //create connection
        connection = DriverManager.getConnection(jdbcUrl);
        String sql = "replace into web_access (city, loginTime, online, phoneName, userId, processTime) values (?,?,?,?,?, now(3));"; //processTime is the time when the data insert into database, not the time when flink receive the message I think?
        ps = connection.prepareStatement(sql);
    }

    @Override
    public void invoke(Entity value, Context context) throws Exception {

        ps.setString(1,value.city);
        ps.setString(2,value.loginTime);
        ps.setString(3,value.online);
        ps.setString(4,value.phoneName);
        ps.setString(5,value.userId);
        message_from_kafka = message_from_kafka + 1;
        System.out.println("message received " + message_from_kafka + "," + " insert into web_access (city,loginTime,os,phoneName values ("+value.city+","+value.loginTime+","+value.online+","+value.phoneName+","+value.userId );
        ps.executeUpdate();
    }

    @Override
    public void close() throws Exception {
        super.close();
        if(connection != null){
            connection.close();
        }
        if (ps != null){
            ps.close();
        }
    }

}
