package UC4.PConsumer;

import UC4.GUI.UpdateGUI;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.TopicPartition;

import java.io.IOException;
import java.util.List;
import java.util.Properties;

public class TConsumer extends Thread{
    private Properties properties;
    private KafkaConsumer<String, String> consumer;

    private List<TopicPartition> topicPartitions;

    private String topicName;

    private UpdateGUI consumergui;

    public TConsumer(Properties properties/*, String newTopic*/, List <TopicPartition> topicPartitions){
        this.properties = properties;
        this.consumer = new KafkaConsumer<>(this.properties);
        //this.topicName = newTopic;
        this.topicPartitions = topicPartitions;

    }

    @Override
    public void run() {

        try {
            consumergui = new UpdateGUI("C");
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        //consumer.subscribe(Arrays.asList(topicName));
        consumer.assign(topicPartitions);

        System.out.println("consumer begins !");
        while(true){
            ConsumerRecords<String,String> records = consumer.poll(100);
            for (ConsumerRecord<String, String> record : records){
                System.out.println("Receive message : " + record.value());
                consumergui.sendInfo(record.value());
            }
        }
    }
}
