package code.interview

import com.amazonaws.auth.profile.ProfileCredentialsProvider
import com.amazonaws.auth.{AWSCredentialsProvider, AWSCredentialsProviderChain, EnvironmentVariableCredentialsProvider}
import org.apache.spark.rdd.RDD
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions.col
import org.rogach.scallop.ScallopConf


object S3SparkApp {

  class Conf(arguments: Seq[String]) extends ScallopConf(arguments) {
    val input = opt[String](required = true, descr = "s3 path ex: s3a://xxxx/*")
    val output = opt[String](required = true, descr = "s3 path ex: s3a://xxxx/*")
    val awsprofile = opt[String](required = true, descr = "profile present in ~/.aws/credentials ")
    verify()
  }

  def main(args: Array[String]): Unit = {
    val conf = new Conf(args)

    val spark = SparkSession.builder()
      .appName("S3 Spark App")
      .master("local[*]")
      .getOrCreate()

    val awsCredentials = new AWSCredentialsProviderChain(new
        EnvironmentVariableCredentialsProvider(), new
        ProfileCredentialsProvider(conf.awsprofile()))

    val awsProfileAccess = awsCredentials.getCredentials
    spark.sparkContext.hadoopConfiguration.set("fs.s3a.access.key", awsProfileAccess.getAWSAccessKeyId )
    spark.sparkContext.hadoopConfiguration.set("fs.s3a.secret.key", awsProfileAccess.getAWSSecretKey)

    import spark.implicits._


    val rdd = spark.sparkContext.wholeTextFiles( conf.input())
    val result = rdd.map( ContentTransformer.transform).map(_.groupBy(_._1).mapValues(_.map(_._2))
      .toList).map( _.map(ServiceOddValues.filteringOddValues) ).reduce { ServiceOddValues.reduceKeepOnlyOddValues}

    result.toDF().write.format("csv").option("delimiter", "\t").option("header", "false").mode("Overwrite")
      .save(conf.output())

    spark.stop()
  }
}

