import kafka.Producer

class User(num: Int) extends Thread {
  override def run()
  {
    val producer = new Producer()
    producer.writeCommentsFromFile("my-kafka-topic", file = "/home/nata/scala_projects/scala-final-project/reddit-streaming/src/resources/reddit_comments_data"+num+".csv")
  }
}

object SimulateUsers extends App{
  for(i <- 0 to 4){
    val thread = new User(i)
    thread.start()
  }
}

