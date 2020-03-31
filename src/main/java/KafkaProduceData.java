import com.alibaba.fastjson.JSON;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

public class KafkaProduceData {

    public static int userId = 0;

    public static ProducerRecord createData(String topic) {

        Entity entity = new Entity();
        //phone brand
        String phoneArray[] = {"iPhone", "htc", "google", "xiaomi", "huawei"};
        //os
        String onlineArray[] = {"y", "n"};
        //city
        String cityArray[] = {"Taipei","Hong Kong","London","Paris","Tokyo","New York","Singapore","Rome"};
        //Generate Brand dandomly
        int k = (int) (Math.random() * 5);
        String phoneName = phoneArray[k];
        //Generate os randomly
        int m = (int) (Math.random() * 2);
        String online = onlineArray[m];
        //Generate City randomly
        int n = (int) (Math.random() * 8);
        String city = cityArray[n];
        //Event Time Stamp
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.sss");
        Date date = new Date();
        String loginTime = sf.format(new Timestamp(date.getTime()));
//        String partitionValue = new SimpleDateFormat("yyyyMMdd").format(new Date(loginTime));

        //Loading Data into Entity
        entity.setCity(city);
        entity.setLoginTime(loginTime);
        entity.setOnline(online);
        entity.setPhoneName(phoneName);
        userId = userId + 1;
        entity.setuserId(Integer.toString(userId));
        System.out.println(Integer.toString(userId));

        ProducerRecord record = new ProducerRecord<String, String>(topic, JSON.toJSONString(entity));
//            producer.send(record);
        System.out.println("message going to send："+ JSON.toJSONString(entity));
        return record;
    }
}
