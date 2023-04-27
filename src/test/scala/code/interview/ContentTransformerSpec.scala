package code.interview

import collection.mutable.Stack
import org.scalatest._
import flatspec._
import matchers._
import org.apache.spark.sql.SparkSession

class ContentTransformerSpec extends AnyFlatSpec with should.Matchers {
  val spark = SparkSession.builder()
    .appName("S3 Spark App")
    .master("local[*]")
    .getOrCreate()

  "transformer" should "be able to read/transform a tsv file" in {
    val rdd = spark.sparkContext.wholeTextFiles( "src/test/scala/resources/tsv/ex1.tsv")
    val data = rdd.map(ContentTransformer.transform).collect().flatten
    assert( data === List( (1,65), (2,71),(3, 69), (4,0), ( 5,67), (6, 68), (7,69),(8,70), (0,67)))
  }

  "transformer" should "be able to read/transform a csv file" in {
    val rdd = spark.sparkContext.wholeTextFiles("src/test/scala/resources/csv/ex1.csv")
    val data = rdd.map(ContentTransformer.transform).collect().flatten
    assert(data === List((0, 23), (0, 1), (3, 4), (5, 6), (8, 0), (9, 0),(0,0)))
  }
}
