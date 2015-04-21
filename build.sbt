autoScalaLibrary := true

val jvmSdkVersion = "1.0.0-M13"
val sphereNing18 = "io.sphere.sdk.jvm" % "sphere-java-client-ning-1_8" % jvmSdkVersion
val sphereNing19 = "io.sphere.sdk.jvm" % "sphere-java-client-ning-1_9" % jvmSdkVersion
val scala210 = "2.10.4"

crossScalaVersions in ThisBuild := Seq(scala210, "2.11.6")

crossPaths := true

lazy val root = (project in file(".")).configs(IntegrationTest).aggregate(`sphere-play-2_4-java-client`, `sphere-play-2_3-java-client`, `sphere-play-2_2-java-client`, `sphere-scala-client`).settings(
  packagedArtifacts := Map.empty,
  name := "sphere-jvm-sdk-scala-add-ons"
)

lazy val `sphere-play-2_4-java-client` = project.configs(IntegrationTest).settings(
  libraryDependencies ++= Seq("com.typesafe.play" %% "play-java" % "2.4.0-M3", sphereNing19)
)

lazy val `sphere-play-2_3-java-client` = project.configs(IntegrationTest).settings(
  libraryDependencies ++= Seq("com.typesafe.play" %% "play-java" % "2.3.8", sphereNing18)
)

lazy val `sphere-play-2_2-java-client` = project.configs(IntegrationTest).settings(
  libraryDependencies += "com.typesafe.play" % "play-java_2.10" % "2.2.4" exclude("org.yaml", "snakeyaml") exclude("org.hibernate", "hibernate-validator") exclude("org.springframework", "spring-context") exclude("org.springframework", "spring-core") exclude("org.springframework", "spring-beans") exclude("javax.servlet", "javax.servlet-api") exclude("com.typesafe.play", "play-json_2.10") exclude("com.typesafe.play", "templates_2.10"),
  libraryDependencies += sphereNing18,
  crossScalaVersions := Seq(scala210),
  packagedArtifacts := (if(scalaVersion.value == scala210) packagedArtifacts.value else Map.empty)
)

lazy val `sphere-scala-client` = project.configs(IntegrationTest).settings(
  libraryDependencies += sphereNing18
)

resolvers in ThisBuild += "Typesafe Repo" at "http://repo.typesafe.com/typesafe/releases/"

resolvers in ThisBuild += Resolver.sonatypeRepo("snapshots")

resolvers in ThisBuild += Resolver.sonatypeRepo("releases")

organization in ThisBuild := "io.sphere.sdk.jvm"

libraryDependencies in ThisBuild ++=
  "io.sphere.sdk.jvm" % "sphere-models" % jvmSdkVersion % "test" ::
  "com.typesafe" % "config" % "1.2.1" ::
  "org.easytesting" % "fest-assert" % "1.4" % "test" ::
  "junit" % "junit-dep" % "4.11" % "test" ::
  "com.novocode" % "junit-interface" % "0.10" % "test" ::
  Nil


javacOptions in ThisBuild ++= Seq("-deprecation", "-Xlint:unchecked", "-source", "1.8", "-target", "1.8", "-Xlint:all", "-Xlint:-options", "-Xlint:-path", "-Werror", "-parameters")

javacOptions in (Compile, doc) in ThisBuild := Nil

scalaVersion in ThisBuild := scala210

scalacOptions in ThisBuild ++= Seq("-deprecation", "-unchecked", "-feature", "-language:implicitConversions", "-language:postfixOps")

Defaults.itSettings

releaseSettings

Release.publishSettings

licenses in ThisBuild := Seq("Apache" -> url("http://www.apache.org/licenses/LICENSE-2.0"))

homepage in ThisBuild := Some(url("https://github.com/sphereio/sphere-jvm-sdk-scala-add-ons"))