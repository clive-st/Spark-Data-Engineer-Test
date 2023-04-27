name := "S3SparkApp"

version := "1.0"

scalaVersion := "2.12.14"

libraryDependencies ++= Seq(
  "org.apache.spark" %% "spark-core" % "3.2.1",
  "org.apache.spark" %% "spark-sql" % "3.2.1",
  "org.apache.hadoop" % "hadoop-aws" % "3.3.1",
  "com.amazonaws" % "aws-java-sdk" % "1.11.976",
  "org.rogach" %% "scallop" % "4.1.0",
  "org.scalatest" %% "scalatest" % "3.2.10" % Test
)
