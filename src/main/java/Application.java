import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.sink.SinkFunction;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumerBase;

public interface Application {

    void flinkToMysql(
                      StreamExecutionEnvironment env,
                      FlinkKafkaConsumerBase<String> source,
                      SinkFunction sink
                      );
                      
}
