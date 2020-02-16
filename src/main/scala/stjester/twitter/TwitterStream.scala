package stjester.twitter

import stjester.twitter.TwitterStream.Util
import twitter4j.conf.Configuration
import twitter4j._

object TweetStatistics{
  var count_of_tweets = 0;
}
object TwitterStream {
  def main(args: Array[String]) {
    val twitterStream = new TwitterStreamFactory(Util.config).getInstance
    twitterStream.addListener(Util.simpleStatusListener)
    twitterStream.sample()
    Thread.sleep(5000)
    twitterStream.cleanUp()
    twitterStream.shutdown()
  }

  object Util {
    val config: Configuration = new twitter4j.conf.ConfigurationBuilder()
      .setOAuthConsumerKey(AccessTokens.APIkey)
      .setOAuthConsumerSecret(AccessTokens.APIkeysecret)
      .setOAuthAccessToken(AccessTokens.accesstoken)
      .setOAuthAccessTokenSecret(AccessTokens.accesstokensecret)
      .build

    def simpleStatusListener: StatusListener = new StatusListener() {
      def onStatus(status: Status) {
        TweetStatistics.count_of_tweets += 1
        println("user:", "name: "+status.getUser.getName,"ScreenName: "+status.getUser.getScreenName,"Location: "+status.getUser.getLocation, "FollowersCount: "+status.getUser.getFollowersCount, "\n" )
        println("places:", "country: "+status.getPlace.getCountry, "StreetAddress:" + status.getPlace.getStreetAddress, "PlaceType: " + status.getPlace.getPlaceType, "\n")
        println("general:", status.getCreatedAt,status.getText,  status.getText.length, status.getSource, "\n" )
        println("hashtags")
        status.getHashtagEntities.foreach(f=>print(f.getText))
        println("===================================================================================================================================")
      }

      def onDeletionNotice(statusDeletionNotice: StatusDeletionNotice) {}

      def onTrackLimitationNotice(numberOfLimitedStatuses: Int) {}

      def onException(ex: Exception) {
        ex.printStackTrace()
      }

      def onScrubGeo(arg0: Long, arg1: Long) {}

      def onStallWarning(warning: StallWarning) {}
    }
  }


}

object AustinStreamer {
  def main(args: Array[String]) {
    val twitterStream = new TwitterStreamFactory(Util.config).getInstance
    twitterStream.addListener(Util.simpleStatusListener)
    //    val lat: Double = 41.0136
    //    val longitude: Double = 28.9744
    //    val lat1: Double = lat - .50
    //    val longitude1: Double = longitude - .50
    //    val lat2: Double = lat + .50
    //    val longitude2: Double = longitude + .50
    //
    //    val bb: Array[Double] = new Array((lat1, longitude1), (lat2, longitude2))

    val austinBox = Array(Array(-97.8, 30.25), Array(-97.65, 30.35))

    //    val boundingBoxOfUS = Array(Array(-124.848974, 24.396308), Array(-66.885444, 49.384358))
    twitterStream.filter(new FilterQuery().locations(austinBox))
    Thread.sleep(20000)
    twitterStream.cleanUp()
    twitterStream.shutdown()
    println(TweetStatistics.count_of_tweets)
  }


}

object UkraineStreamer {
  def main(args: Array[String]) {
    val twitterStream = new TwitterStreamFactory(Util.config).getInstance
    twitterStream.addListener(Util.simpleStatusListener)
    //    val lat: Double = 41.0136
    //    val longitude: Double = 28.9744
    val lat1: Double = 44.41886
    val longitude1: Double = 22.20555
    val lat2: Double = 52.18903
    val longitude2: Double = 40.13222

    //    val bb: Array[Double] = new Array((lat1, longitude1), (lat2, longitude2))

    val ukraineBox = Array(Array(longitude1, lat1 ), Array(longitude2, lat2))

    //    val boundingBoxOfUS = Array(Array(-124.848974, 24.396308), Array(-66.885444, 49.384358))
    twitterStream.filter(new FilterQuery().locations(ukraineBox))
    Thread.sleep(20000)
    twitterStream.cleanUp()
    twitterStream.shutdown()
  }
}