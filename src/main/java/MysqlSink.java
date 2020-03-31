//import org.apache.flink.configuration.Configuration;
//import org.apache.flink.streaming.api.functions.sink.RichSinkFunction;
//import org.apache.flink.streaming.api.functions.sink.SinkFunction;
//
//import java.sql.Connection;
//import java.sql.DriverManager;
//import java.sql.PreparedStatement;
//
//public class MysqlSink extends RichSinkFunction<Entity> {
//
//    // Read RDS connection information from the environment
//    String driver = "com.mysql.jdbc.Driver";
//    private PreparedStatement ps=null;
//    private Connection connection=null;
//    String dbName = "testdb";
//    String userName = "xxx";
//    String password = "xxx";
//    String hostname = "xxxx";
//    String port = "3306";
//    String jdbcUrl = "jdbc:mysql://" + hostname + ":" + port + "/" + dbName + "?user=" + userName + "&password=" + password;
//
//    @Override
//    public void open(Configuration parameters) throws Exception {
//        super.open(parameters);
//        //loading jdbc driver
//        Class.forName(driver);
//        //create connection
//        connection = DriverManager.getConnection(jdbcUrl);
////        String sql = "INSERT INTO parking_lot (ID, CELLID, NAME, DAY, HOUR, PAY, PAYCASH, MEMO, ROADID, CELLSTATUS, ISNOWCASH, ParkingStatus, lat, lon) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?);";
//        String sql = "replace into parking_lot (ID, CELLID, NAME, DAY, HOUR, PAY, PAYCASH, MEMO, ROADID, CELLSTATUS, ISNOWCASH, ParkingStatus, lat, lon) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?);";
//
//        ps = connection.prepareStatement(sql);
//    }
//
//
//    @Override
//    public void invoke(Entity value, Context context) throws Exception {
//
//        ps.setString(1,value.ID);
//        ps.setString(2,value.CELLID);
//        ps.setString(3,value.NAME);
//        ps.setString(4,value.DAY);
//        ps.setString(5,value.HOUR);
//        ps.setString(6,value.PAY);
//        ps.setString(7,value.PAYCASH);
//        ps.setString(8,value.MEMO);
//        ps.setString(9,value.ROADID);
//        ps.setString(10,value.CELLSTATUS);
//        ps.setString(11,value.ISNOWCASH);
//        ps.setString(12,value.ParkingStatus);
//        ps.setString(13,value.lat);
//        ps.setString(14,value.lon);
//
////        System.out.println("insert into parking_lot (city,loginTime,os,phoneName values (" +value.ID +"," + value.CELLID+","+value.NAME+","+value.DAY+","+value.HOUR+","+value.PAY+","+value.PAYCASH +","+value.MEMO+","+value.ROADID+","+value.CELLSTATUS+","+value.ISNOWCASH+","+value.ParkingStatus+","+value.lat+","+value.lon);
////        System.out.println("replace into parking_lot (city,loginTime,os,phoneName values (" +value.ID +"," + value.CELLID+","+value.NAME+","+value.DAY+","+value.HOUR+","+value.PAY+","+value.PAYCASH +","+value.MEMO+","+value.ROADID+","+value.CELLSTATUS+","+value.ISNOWCASH+","+value.ParkingStatus+","+value.lat+","+value.lon);
//        ps.executeUpdate();
//    }
//    @Override
//    public void close() throws Exception {
//        super.close();
//        if(connection != null){
//            connection.close();
//        }
//        if (ps != null){
//            ps.close();
//        }
//    }
//
//}


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
