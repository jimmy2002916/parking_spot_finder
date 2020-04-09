import com.alibaba.fastjson.JSON;
import org.apache.flink.api.common.serialization.SimpleStringSchema;
import org.apache.flink.runtime.testutils.MiniClusterResourceConfiguration;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

import org.apache.flink.streaming.api.functions.sink.SinkFunction;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer011;
import org.apache.flink.test.util.MiniClusterWithClientResource;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.junit.ClassRule;
import org.junit.Test;

import static org.junit.Assert.assertEquals;


public class FlinkIntergrationTest{

    public static String actualMessage = "";
    public static String expectedMessage = "";

    private static final String topic = "testTopic";
    private static final String testBS = "127.0.0.1:9092";
    private static final String testZK = "127.0.0.1:2181";

    @ClassRule
    public static MiniClusterWithClientResource flinkCluster =
            new MiniClusterWithClientResource(
                    new MiniClusterResourceConfiguration.Builder()
                            .setNumberSlotsPerTaskManager(2)
                            .setNumberTaskManagers(1)
                            .build());


    @Test
    public static void testFlinkApplication() throws Exception {

        Entity entity = new Entity();
        entity.setCity("Taipei");
        entity.setLoginTime("2020/01/01");
        entity.setOnline("Y");
        entity.setPhoneName("apple");
        entity.setuserId("1");
        expectedMessage = entity.getCity() + " " + entity.getLoginTime() + " " + entity.getOnline() + " " + entity.getPhoneName() + " " + entity.getuserId();
        System.out.println("Expected Message: " + expectedMessage);

        ProducerRecord record = new ProducerRecord<String, String>(topic, JSON.toJSONString(entity));

        KafkaProducer producer = KafkaProducerGenerator.createProducer(testBS, testZK);
        for(int i = 0; i < 1; i++){
            producer.send(record);
        }

        final StreamExecutionEnvironment env = StreamExecutionEnvironment.createRemoteEnvironment("localhost", 10000);
        env.setParallelism(2);

        // execute
        FlinkApplication.flinkToMysql(
                env,
                new FlinkKafkaConsumer011<>(topic, new SimpleStringSchema(), Main.SetupProperties(testBS, testZK)),
                new CollectSink());

        // verify your results
        assertEquals(actualMessage, expectedMessage);
    }

    private static class CollectSink implements SinkFunction<Entity> {
        @Override
        public synchronized void invoke(Entity value) throws Exception {
            System.out.println(actualMessage = value.city + value.loginTime + value.online + value.phoneName + value.userId);
            System.out.println("Actual Message " + actualMessage);
        }
    }

}