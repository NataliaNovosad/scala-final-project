import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.GlobalKTable;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.KTable;

import java.util.Arrays;
import java.util.List;
import java.util.Properties;

public class DatasetEnricher {
    public static void main(String[] args) {

        Properties config = new Properties();
        config.put(StreamsConfig.APPLICATION_ID_CONFIG, "twitter-reddit-enricher");
        config.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        config.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        config.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass());
        config.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass());

        StreamsBuilder builder = new StreamsBuilder();

//        GlobalKTable<String, String> reddit_topic = builder.globalTable("reddit");

        KStream<String, String> twitter_topic = builder.stream("twitter");
//        KStream<String, String> reddit_topic = builder.stream("reddit");

        KTable<String, String> reddit_table = builder.table("reddit");

        KStream<String, String> TwitterReddit =
                twitter_topic.leftJoin(reddit_table,
                        (twitter_top, reddit_top) -> {
                            if (reddit_top != null) {
                                List<String> words1 = Arrays.asList(reddit_top.split(" "));
                                List<String> words2 = Arrays.asList(twitter_top.split(" "));
                                if (words1.containsAll(words2) && words2.containsAll(words1)) {
                                    return "Reddit=" + reddit_top + ",Twitter=[" + twitter_top + "]";
                                } else {
                                    return "Reddit=" + reddit_top + ",Twitter=null";
                                }
                            } else {
                                return "Twitter=" + twitter_top + ",Reddit=null";
                            }
                        }
//                        (key, value) -> key,
//                        (twitter_top, reddit_top) -> {
////                            System.out.println(twitter_top);
////                            System.out.println(reddit_top);
////                            System.out.println("=================");
//
//
//                            if (reddit_top != null) {
//                                List<String> words1 = Arrays.asList(reddit_top.split(" "));
//                                List<String> words2 = Arrays.asList(twitter_top.split(" "));
//                                if (words1.containsAll(words2) && words2.containsAll(words1)) {
//                                    return "Reddit=" + reddit_top + ",Twitter=[" + twitter_top + "]";
//                                } else {
//                                    return "Reddit=" + reddit_top + ",Twitter=null";
//                                }
//                            } else {
//                                return "Twitter=" + twitter_top + ",Reddit=null";
//                            }
//                        }
                );
        TwitterReddit.to("finaltopic");

        KafkaStreams streams = new KafkaStreams(builder.build(), config);
        streams.cleanUp();
        streams.start();

        // print the topology
        System.out.println(streams.toString());

        // shutdown hook to correctly close the streams application
        Runtime.getRuntime().addShutdownHook(new Thread(streams::close));

    }
}