package UC2.PProducer;

import UC1.GUI.NewGui;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.Properties;

/**
 * Class in which producers are managed
 */
public class PProducer{

    /**
     * The port on which we retrieve data from PSource
     */
    public static final int PSOURCE_PORT = 13000;

    /**
     * The address on which we retrieve data from PSource
     */
    public static final String IP_ADDRESS = "127.0.0.1";

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        NewGui gui = new NewGui("Producer GUI", 6);
        gui.setVisible(true);

        TProducer producers[] = new TProducer[6];

        Properties props = new Properties();
        props.put("bootstrap.servers", "localhost:9092");
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        //some records can be lost, but minimize the possibility of losing all records of a sensor ID
        props.put("acks", "1");

        //to minimize the possibility of losing all records of a sensor ID without deteriorating the performance and minimize the latency
        props.put("retries", "1");

        /**
         * Starting 6 producers
         */
        for (int i=0; i<6; i++){
            //Socket attributes
            Socket clientSocket = new Socket(IP_ADDRESS, PSOURCE_PORT);
            ObjectInputStream in = new ObjectInputStream(clientSocket.getInputStream());

            producers[i] = (new TProducer(props, in, gui));
            producers[i].start();
        }
    }
}
