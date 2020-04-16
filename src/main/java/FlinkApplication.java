import com.alibaba.fastjson.JSON;
import org.apache.flink.api.common.serialization.SimpleStringSchema;
import org.apache.flink.streaming.api.environment.CheckpointConfig;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.sink.SinkFunction;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer011;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumerBase;

import java.util.Properties;

public class FlinkApplication implements Application {

    public static void flinkToMysql(
                                      StreamExecutionEnvironment env,
                                      FlinkKafkaConsumerBase<String> source,
                                      SinkFunction sink
    ) throws Exception {

        System.out.println("Before addSource");
        env.addSource(source) // In addSource, we are creating data stream.
                .rebalance() //The call to rebalance() causes data to be re-partitioned so that all machines receive messages (for example, when the number of Kafka partitions is fewer than the number of Flink parallel instances).
                .map(string -> JSON.parseObject(string, Entity.class)) //Once a DataStream is created, you can transform it as you like.
                .addSink(sink);

        env.enableCheckpointing(60_000);
        env.getCheckpointConfig().enableExternalizedCheckpoints(CheckpointConfig.ExternalizedCheckpointCleanup.DELETE_ON_CANCELLATION);

        System.out.println("before execute");
        env.execute("Flink add sink");
        System.out.println("start to execute");

    }

//    public static void flink_to_hbase(String bootstrap_servers, String zookeeper_connect, String topic) throws Exception {
//
//        final StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
//        Properties props = new Properties();
//        props.put("bootstrap.servers", bootstrap_servers);
//        props.put("zookeeper.connect", zookeeper_connect);
//        props.put("group.id", "hbase");
//        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
//        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
//        props.put("auto.offset.reset", "latest");
//
//        System.out.println("Before addSource");
//        env.addSource(
//                new FlinkKafkaConsumer011<>(
//                        topic, new SimpleStringSchema(), props
//                )
//        )
//                .map(string -> JSON.parseObject(string, Entity.class))
//                .addSink(new HbaseSink("web_access", "info"));
//        env.enableCheckpointing(60_000);
////        env.setStateBackend((StateBackend) new FsStateBackend("hdfs://tmp/flink/checkpoints"));
//        env.getCheckpointConfig().enableExternalizedCheckpoints(CheckpointConfig.ExternalizedCheckpointCleanup.DELETE_ON_CANCELLATION);
//
//        System.out.println("before execute");
//        env.execute("Flink add sink");
//        System.out.println("start to execute");
//    }


}
