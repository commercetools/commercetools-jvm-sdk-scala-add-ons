autoScalaLibrary := true

crossScalaVersions in ThisBuild := Seq("2.10.4", "2.11.0")

crossPaths := true

lazy val root = (project in file(".")).configs(IntegrationTest).aggregate(`play-2_3-java-client`, `play-2_2-java-client`, `scala-client`)

lazy val `play-2_3-java-client` = project.configs(IntegrationTest).settings(
  libraryDependencies += "com.typesafe.play" %% "play-java" % "2.3.5"
).dependsOn(`scala-client`)

lazy val `play-2_2-java-client` = project.configs(IntegrationTest).settings(
  libraryDependencies += "com.typesafe.play" %% "play-java" % "2.2.4" exclude("org.yaml", "snakeyaml") exclude("org.hibernate", "hibernate-validator") exclude("org.springframework", "spring-context") exclude("org.springframework", "spring-core") exclude("org.springframework", "spring-beans") exclude("javax.servlet", "javax.servlet-api") exclude("com.typesafe.play", "play-json_2.10") exclude("com.typesafe.play", "templates_2.10"),
  crossScalaVersions in ThisBuild := Seq("2.10.4"),
  sourceDirectory := `play-2_3-java-client`.base.getAbsoluteFile / sourceDirectory.value.getName
).dependsOn(`scala-client`)

lazy val `scala-client` = project.configs(IntegrationTest)

resolvers in ThisBuild += "Typesafe Repo" at "http://repo.typesafe.com/typesafe/releases/"

organization in ThisBuild := "io.sphere.sdk.jvm"

libraryDependencies in ThisBuild ++=
  "io.sphere.sdk.jvm" % "java-client" % "1.0.0-M6" ::
  "org.easytesting" % "fest-assert" % "1.4" % "test,it" ::
  "junit" % "junit-dep" % "4.11" % "test,it" ::
  "com.novocode" % "junit-interface" % "0.10" % "test, it" ::
  Nil


javacOptions in ThisBuild ++= Seq("-deprecation", "-Xlint:unchecked", "-source", "1.8", "-target", "1.8", "-Xlint:all", "-Xlint:-options", "-Xlint:-path", "-Werror", "-parameters")

javacOptions in (Compile, doc) in ThisBuild := Nil

scalaVersion in ThisBuild := "2.10.4"

scalacOptions in ThisBuild ++= Seq("-deprecation", "-unchecked", "-feature", "-language:implicitConversions", "-language:postfixOps")

Defaults.itSettings

releaseSettings

Release.publishSettings

licenses in ThisBuild := Seq("Apache" -> url("http://www.apache.org/licenses/LICENSE-2.0"))

homepage in ThisBuild := Some(url("https://github.com/sphereio/sphere-jvm-sdk-scala-add-ons"))