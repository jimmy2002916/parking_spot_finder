import com.alibaba.fastjson.JSON;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

public class KafkaProducerGenerator {

    public static KafkaProducer<String, String> createProducer(String bootstrap_servers, String zookeeper_connect) {

        Properties props = new Properties();
        //EC2(Kafka producer IP here)
        props.put("bootstrap.servers", bootstrap_servers);
        props.put("zookeeper.connect", zookeeper_connect);
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer"); //Key serialization
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer"); //value serialization
        props.put("group.id", "metric-group");
        props.put("buffer.memory", 67108864);
        props.put("batch.size", 131072);
        props.put("linger.ms", 100);
        props.put("max.request.size", 10485760);
        props.put("acks", "1");
        props.put("retries", 10);
        props.put("retry.backoff.ms", 500);

        return new KafkaProducer<String, String>(props);

    }


}
