package kafka

import java.util.Properties
import org.apache.kafka.clients.producer._

class Producer[A] {
  def writeToKafka(topic: String, key: String, value: A): Unit = {
    val props = new Properties()
    props.put("bootstrap.servers", "localhost:9093")
    props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer")
    props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer")
    val producer = new KafkaProducer[String, String](props)
    val record = new ProducerRecord[String, String](topic, key, value)
    producer.send(record)
    producer.close()
  }

  def writeCommentsFromFile(file: String): Unit = ???
}

object send extends App{
  val producer = new Producer[String]
  producer.writeToKafka("my-kafka-topic", "1", "hello")
}
