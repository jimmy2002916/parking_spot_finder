import com.alibaba.fastjson.JSON;
import org.apache.flink.api.common.serialization.SimpleStringSchema;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer011;

import java.util.Properties;

public class FlinkApplication {

    public static void flink_to_mysql(String bootstrap_servers, String zookeeper_connect, String topic) throws Exception {

        final StreamExecutionEnvironment env1 = StreamExecutionEnvironment.getExecutionEnvironment();
        Properties props1 = new Properties();
        props1.put("bootstrap.servers", bootstrap_servers);
        props1.put("zookeeper.connect", zookeeper_connect);
        props1.put("group.id", "metric-group");
        props1.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props1.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props1.put("auto.offset.reset", "latest");

        System.out.println("Before addSource");
        env1.addSource(
                new FlinkKafkaConsumer011<>(
                        topic, new SimpleStringSchema(), props1
                )
        )
                .map(string -> JSON.parseObject(string, Entity.class))
                .addSink(new MysqlSink());

        System.out.println("before execute");
        env1.execute("Flink add sink");
        System.out.println("start to execute");

    }

    public static void flink_to_hbase(String bootstrap_servers, String zookeeper_connect, String topic) throws Exception {

        final StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        Properties props = new Properties();
        props.put("bootstrap.servers", bootstrap_servers);
        props.put("zookeeper.connect", zookeeper_connect);
        props.put("group.id", "metric-group");
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("auto.offset.reset", "latest");

        System.out.println("Before addSource");
        env.addSource(
                new FlinkKafkaConsumer011<>(
                        topic, new SimpleStringSchema(), props
                )
        )
                .map(string -> JSON.parseObject(string, Entity.class))
                .addSink(new HbaseSink("web_access", "info"));

        System.out.println("before execute");
        env.execute("Flink add sink");
        System.out.println("start to execute");
    }


}
