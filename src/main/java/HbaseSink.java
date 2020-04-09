////import org.apache.flink.configuration.Configuration;
////import org.apache.flink.streaming.api.functions.sink.RichSinkFunction;
////import org.apache.flink.streaming.api.functions.sink.SinkFunction;
////
////import java.sql.Connection;
////import java.sql.DriverManager;
////import java.sql.PreparedStatement;
////
////public class MysqlSink extends RichSinkFunction<Entity> {
////
////    // Read RDS connection information from the environment
////    String driver = "com.mysql.jdbc.Driver";
////    private PreparedStatement ps=null;
////    private Connection connection=null;
////    String dbName = "testdb";
////    String userName = "carma1";
////    String password = "carma1234";
////    String hostname = "carma-servicelib.cluster-cxydmdndgdv7.us-east-1.rds.amazonaws.com";
//////    String hostname = "parking-lot-database.c6p9kfxblbde.us-east-1.rds.amazonaws.com";
////    String port = "3306";
////    String jdbcUrl = "jdbc:mysql://" + hostname + ":" + port + "/" + dbName + "?user=" + userName + "&password=" + password;
////
////    @Override
////    public void open(Configuration parameters) throws Exception {
////        super.open(parameters);
////        //loading jdbc driver
////        Class.forName(driver);
////        //create connection
////        connection = DriverManager.getConnection(jdbcUrl);
//////        String sql = "INSERT INTO parking_lot (ID, CELLID, NAME, DAY, HOUR, PAY, PAYCASH, MEMO, ROADID, CELLSTATUS, ISNOWCASH, ParkingStatus, lat, lon) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?);";
////        String sql = "replace into parking_lot (ID, CELLID, NAME, DAY, HOUR, PAY, PAYCASH, MEMO, ROADID, CELLSTATUS, ISNOWCASH, ParkingStatus, lat, lon) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?);";
////
////        ps = connection.prepareStatement(sql);
////    }
////
////
////    @Override
////    public void invoke(Entity value, Context context) throws Exception {
////
////        ps.setString(1,value.ID);
////        ps.setString(2,value.CELLID);
////        ps.setString(3,value.NAME);
////        ps.setString(4,value.DAY);
////        ps.setString(5,value.HOUR);
////        ps.setString(6,value.PAY);
////        ps.setString(7,value.PAYCASH);
////        ps.setString(8,value.MEMO);
////        ps.setString(9,value.ROADID);
////        ps.setString(10,value.CELLSTATUS);
////        ps.setString(11,value.ISNOWCASH);
////        ps.setString(12,value.ParkingStatus);
////        ps.setString(13,value.lat);
////        ps.setString(14,value.lon);
////
//////        System.out.println("insert into parking_lot (city,loginTime,os,phoneName values (" +value.ID +"," + value.CELLID+","+value.NAME+","+value.DAY+","+value.HOUR+","+value.PAY+","+value.PAYCASH +","+value.MEMO+","+value.ROADID+","+value.CELLSTATUS+","+value.ISNOWCASH+","+value.ParkingStatus+","+value.lat+","+value.lon);
//////        System.out.println("replace into parking_lot (city,loginTime,os,phoneName values (" +value.ID +"," + value.CELLID+","+value.NAME+","+value.DAY+","+value.HOUR+","+value.PAY+","+value.PAYCASH +","+value.MEMO+","+value.ROADID+","+value.CELLSTATUS+","+value.ISNOWCASH+","+value.ParkingStatus+","+value.lat+","+value.lon);
////        ps.executeUpdate();
////    }
////    @Override
////    public void close() throws Exception {
////        super.close();
////        if(connection != null){
////            connection.close();
////        }
////        if (ps != null){
////            ps.close();
////        }
////    }
////
////}
//
import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.functions.sink.RichSinkFunction;

import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hbase.*;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.util.Bytes;


public class HbaseSink extends RichSinkFunction<Entity> {

    private static String table_name = null;
    private static String family = "info";
    private static Connection connection = null;
    private static Admin admin = null;
    private static final byte[] TABLE_NAME = Bytes.toBytes("web_access");
    private static final byte[] COLUMN_FAMILY_NAME = Bytes.toBytes("cf1");
    private static final byte[] COLUMN_NAME = Bytes.toBytes("greeting");

    public HbaseSink(String TABLE_NAME, String FAMILY) {
        table_name = TABLE_NAME;
        family = FAMILY;
    }


    @Override
    //check if table in hbase exists
    public void open(Configuration parameters) throws Exception {

        super.open(parameters);

        org.apache.hadoop.conf.Configuration config = HBaseConfiguration.create();
//        config.set("hbase.zookeeper.quorum", "example.com");
        config.set("hbase.zookeeper.quorum", "ip-10-224-155-16.emdeon.net"); //should be same as the emr you use, should be change in hbase-site.xml
        config.set("hbase.zookeeper.property.clientPort", "2181");
        config.set("zookeeper.znode.parent", "/hbase");  //should be same as the emr you use, should be change in hbase-site.xml
        config.addResource("/usr/lib/hbase/conf/hbase-site.xml");
//        config.addResource("/etc/hadoop/conf.empty/core-site.xml");
//        config.addResource("/etc/hadoop/conf.empty/hdfs-site.xml");

        connection = ConnectionFactory.createConnection(config);
        admin = connection.getAdmin();

    }

    @Override
    public void invoke(Entity value, Context context) throws Exception {

        HTableDescriptor descriptor = new HTableDescriptor(TableName.valueOf(TABLE_NAME));
        descriptor.addFamily(new HColumnDescriptor(COLUMN_FAMILY_NAME));
//        admin.createTable(descriptor);

        Table table = connection.getTable(TableName.valueOf("web_access"));
        Put put = new Put(Bytes.toBytes(value.userId));
        put.addColumn(COLUMN_FAMILY_NAME, Bytes.toBytes("city"), Bytes.toBytes(value.city));
        put.addColumn(COLUMN_FAMILY_NAME, Bytes.toBytes("loginTime"), Bytes.toBytes(value.loginTime));
        put.addColumn(COLUMN_FAMILY_NAME, Bytes.toBytes("online"), Bytes.toBytes(value.online));
        put.addColumn(COLUMN_FAMILY_NAME, Bytes.toBytes("phoneName"), Bytes.toBytes(value.phoneName));
        table.put(put);
        table.close();

    }

    @Override
    public void close() throws Exception {
        super.close();
        if(connection != null){
            connection.close();
        }
    }


}