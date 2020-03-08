package kafka

import java.io.{FileNotFoundException, IOException}
import java.util.Properties

import org.apache.kafka.clients.producer._

import scala.io.Source

class Producer {
  def writeToKafka(topic: String, key: String, value: String): Unit = {
    val props = new Properties()
    props.put("bootstrap.servers", "localhost:9093")
    props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer")
    props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer")
    val producer = new KafkaProducer[String, String](props)
    val record = new ProducerRecord[String, String](topic, key, value)
    producer.send(record)
    producer.close()
  }

  def writeCommentsFromFile(topic: String, file: String): Unit = {
    try {

      val bufferedsource = Source.fromFile(file)
      for (line <- bufferedsource.getLines) {
        val splited = line.split("&")
        splited.length match {
          case 4 => {
            val comment = "{'subreddit':'" + splited(1) + "','author':'" + splited(2) + "','comment':'" + splited(3) + "','resource':'" + file.slice(file.length - 9, file.length) + "'}"
            writeToKafka(topic, splited(0), comment)
            Thread.sleep(100)
          }
          case _ => {}
        }

      }
      bufferedsource.close()
    } catch {
      case e: FileNotFoundException => println("Couldn't find that file.")
      case e: IOException => println("Got an IOException!")
    }
  }
}
