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
        config.set("hbase.zookeeper.quorum", "ip-X-X-X-X.emdeon.net"); //should be same as the emr you use, should be change in hbase-site.xml
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
