import com.alibaba.fastjson.JSON;
import org.apache.flink.api.common.serialization.SimpleStringSchema;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

import java.util.Date;
import java.text.SimpleDateFormat;
import java.sql.Timestamp;


import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer011;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.Properties;
import java.util.UUID;

public class Main extends Thread {

    //    public static final String topic = "parking_lot";
    // ip is EC2 ip, aka kafka server ip
    public static String bootstrap_servers = "xxxx:9092";
    public static String zookeeper_connect = "xxxx:2181";
    public static final String topic = "web_access";

    public static int msg_sent_count = 0;

    public static void main(String[] args) throws Exception {

//        KafkaProducer producer = KafkaProducerGenerator.createProducer(bootstrap_servers, zookeeper_connect);
//        // Data generated from main thread
//        while(true){
//            ProducerRecord record = KafkaProduceData.createData(topic);
//            producer.send(record);
//            msg_sent_count = msg_sent_count + 1;
//            System.out.println("msg_sent_count: " + msg_sent_count);
//        }


        FlinkApplication.flink_to_mysql(bootstrap_servers, zookeeper_connect, topic);
        // Data generated from additional thread
//        Main HbaseFlink = new Main();
//        HbaseFlink.start();

    }

        @Override
        public void run() {
            try {
                flinkToHbase();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        public static void flinkToHbase() throws Exception {

            while (true) {
                    FlinkApplication.flink_to_hbase(bootstrap_servers, zookeeper_connect, topic);
                    Thread.sleep(1); // if this setup as 1, broker will be broken, setup as 500 to ensure it can works
            }
        }

}
