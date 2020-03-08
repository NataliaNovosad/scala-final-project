package kafka

class User(num: Int) extends Thread {
  override def run() {
    val producer = new Producer()

    producer.writeCommentsFromFile("reddit", file = "C:/Users/stell/IdeaProjects/scala-final-project/reddit/src/main/resource/reddit_comments_data" + num + ".csv")
  }
}

object SimulateUsers extends App {
  //  println(getClass.getResource("/reddit_comments_data0.csv").getPath)

    for (i <- 0 to 4) {
      val thread = new User(i)
      thread.start()
    }
}

