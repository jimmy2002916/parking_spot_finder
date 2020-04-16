import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.sink.SinkFunction;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumerBase;
import org.junit.Test;
import static org.easymock.EasyMock.*;
import static org.junit.Assert.*;
import org.junit.Test;
import org.easymock.*;

public class FlinkUnitTest {
    
    @Test
    public void unitTestFlinkApplication() throws Exception{
        
        Application flinkUnitTest = mock(FlinkApplication.class);
        flinkUnitTest.flinkToMysql(
                EasyMock.isA(StreamExecutionEnvironment.class),
                EasyMock.isA(FlinkKafkaConsumerBase.class),
                EasyMock.isA(SinkFunction.class)
        );
        expectLastCall().andVoid();
        replay(flinkUnitTest);
        
        StreamExecutionEnvironment env = mock(StreamExecutionEnvironment.class);
        FlinkKafkaConsumerBase<String> source = mock(FlinkKafkaConsumerBase.class);
        SinkFunction sink = mock(SinkFunction.class);
        flinkUnitTest.flinkToMysql(env,source, sink);
        verify(flinkUnitTest);
        
    }
}
