//import org.apache.kafka.clients.consumer.ConsumerConfig
//import org.apache.kafka.common.serialization.Serdes
//import org.apache.kafka.streams.KafkaStreams
//import org.apache.kafka.streams.StreamsBuilder
//import org.apache.kafka.streams.StreamsConfig
//import org.apache.kafka.streams.kstream.GlobalKTable
//import org.apache.kafka.streams.kstream.KStream
//import java.util.Properties
//
//class DatasetEnricher {
//  def main(args: Array[String]): Unit = {
//    val config = new Properties();
//    config.put(StreamsConfig.APPLICATION_ID_CONFIG, "dtaset_enricher")
//    config.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092")
//    config.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest")
//    config.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String.getClass)
//    config.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String.getClass)
//    val builder = new StreamsBuilder();
//    val twitterGlobalTable: GlobalKTable[String, String] = builder.globalTable("twitter")
//    val redditPosts: KStream[String, String] = builder.stream("reddit")
//
//    val twitterreddit: KStream[String, String] = twitterGlobalTable.leftJoin(redditPosts, (key, value) => key, (userPurchase, userInfo) => {
//      import java.util
//      val words1 = util.Arrays.asList(title.split(" "))
//      val words2 = util.Arrays.asList(title2.split(" "))
//
//      return words1.containsAll(words2) && words2.containsAll(words1)
//      foo(userPurchase, userInfo)
//    })
//    twitterreddit.to("finaldataset")
//    val streams = new KafkaStreams(builder.build, config)
//    streams.cleanUp()
//    streams.start()
//    // print the topology
//    System.out.println(streams.toString)
//    // shutdown hook to correctly close the streams application
//    Runtime.getRuntime.addShutdownHook(new Thread(streams.close))
//  }
//}
