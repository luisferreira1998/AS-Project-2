package UC1;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
//        PProducer producer = new PProducer();
//        producer.start();
        PConsumerTest consumer = new PConsumerTest();
        consumer.start();
//        PSource source = new PSource();

    }
}
