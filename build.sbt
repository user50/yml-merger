name := "yml-merge"

version := "1.0-SNAPSHOT"

libraryDependencies ++= Seq(
  javaJdbc,
  javaEbean,
  cache,
  "org.seleniumhq.selenium" % "selenium-java" % "2.45.0",
  "org.apache.httpcomponents" % "httpclient" % "4.3.6",
  "com.fasterxml.jackson.core" % "jackson-core" % "2.5.0",
  "com.fasterxml.jackson.core" % "jackson-databind" % "2.5.0"
)     

play.Project.playJavaSettings
