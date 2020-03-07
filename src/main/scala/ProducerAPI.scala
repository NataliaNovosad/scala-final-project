import java.util.Properties

import org.apache.kafka.clients.producer._

class ProducerTest {

  def writeToKafka(topic: String): Unit = {
    val props = new Properties()
    props.put("bootstrap.servers", "localhost:9093")
    props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer")
    props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer")
    val producer = new KafkaProducer[String, String](props)
    val record = new ProducerRecord[String, String](topic, "key", "value")
    producer.send(record)
    producer.close()
  }

}

object send extends App{
  val producer = new ProducerTest()
  producer.writeToKafka("my-kafka-topic")
}


