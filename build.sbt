autoScalaLibrary := true

val jvmSdkVersion = "1.0.0-M25"
val `sphere-models` = "io.sphere.sdk.jvm" % "sphere-models" % jvmSdkVersion
val `sphere-java-client-core` = "io.sphere.sdk.jvm" % "sphere-java-client-core" % jvmSdkVersion
val sphereNing18 = "io.sphere.sdk.jvm" % "sphere-java-client-ahc-1_8" % jvmSdkVersion
val sphereNing19 = "io.sphere.sdk.jvm" % "sphere-java-client-ahc-1_9" % jvmSdkVersion
val scala210 = "2.10.6"
val scala211 = "2.11.7"
val scala212 = "2.12.0-M3"

val allScalaVersions = Seq(scala210, scala211, scala212)

crossPaths := true

crossScalaVersions in ThisBuild := allScalaVersions

lazy val root = (project in file(".")).configs(IntegrationTest).aggregate(`sphere-play-2_5-java-client`, `sphere-play-2_4-java-client`, `sphere-play-2_3-java-client`, `sphere-play-2_2-java-client`, `sphere-scala-client`, `sphere-scala-models`).settings(
  packagedArtifacts := Map.empty,
  name := "sphere-jvm-sdk-scala-add-ons"
)

lazy val `sphere-play-2_5-java-client` = project.configs(IntegrationTest).settings(
  libraryDependencies ++= (if(crossScalaVersions.value.contains(scalaVersion.value)) Seq("com.typesafe.play" %% "play-java" % "2.5.0-M1", sphereNing19) else Nil),
  crossScalaVersions := Seq(scala211),
  packagedArtifacts := (if(crossScalaVersions.value.contains(scalaVersion.value)) packagedArtifacts.value else Map.empty),
  sourceDirectory := (if(crossScalaVersions.value.contains(scalaVersion.value)) sourceDirectory.value else IO.temporaryDirectory)
)

lazy val `sphere-play-2_4-java-client` = project.configs(IntegrationTest).settings(
  libraryDependencies ++= (if(crossScalaVersions.value.contains(scalaVersion.value)) Seq("com.typesafe.play" %% "play-java" % "2.4.4", sphereNing19) else Nil),
  crossScalaVersions := Seq(scala210, scala211),
  packagedArtifacts := (if(crossScalaVersions.value.contains(scalaVersion.value)) packagedArtifacts.value else Map.empty),
  sourceDirectory := (if(crossScalaVersions.value.contains(scalaVersion.value)) sourceDirectory.value else IO.temporaryDirectory)
)

lazy val `sphere-play-2_3-java-client` = project.configs(IntegrationTest).settings(
  libraryDependencies ++= (if(crossScalaVersions.value.contains(scalaVersion.value)) Seq("com.typesafe.play" %% "play-java" % "2.3.10", sphereNing18) else Nil),
  crossScalaVersions := Seq(scala210, scala211),
  packagedArtifacts := (if(crossScalaVersions.value.contains(scalaVersion.value)) packagedArtifacts.value else Map.empty),
  sourceDirectory := (if(crossScalaVersions.value.contains(scalaVersion.value)) sourceDirectory.value else IO.temporaryDirectory)
)

lazy val `sphere-play-2_2-java-client` = project.configs(IntegrationTest).settings(
  libraryDependencies ++= (if(crossScalaVersions.value.contains(scalaVersion.value)) (sphereNing18 :: ("com.typesafe.play" %% "play-java" % "2.2.6" exclude("org.yaml", "snakeyaml") exclude("org.hibernate", "hibernate-validator") exclude("org.springframework", "spring-context") exclude("org.springframework", "spring-core") exclude("org.springframework", "spring-beans") exclude("javax.servlet", "javax.servlet-api") exclude("com.typesafe.play", "play-json_2.10") exclude("com.typesafe.play", "templates_2.10")) :: Nil) else Nil),
  crossScalaVersions := Seq(scala210),
  packagedArtifacts := (if(crossScalaVersions.value.contains(scalaVersion.value)) packagedArtifacts.value else Map.empty),
  sourceDirectory := (if(crossScalaVersions.value.contains(scalaVersion.value)) sourceDirectory.value else IO.temporaryDirectory)
)

lazy val `sphere-scala-client` = project.configs(IntegrationTest).settings(
  crossScalaVersions := allScalaVersions,
  libraryDependencies += `sphere-java-client-core`,
  libraryDependencies += sphereNing18 % "test,it"
)

lazy val `sphere-scala-models` = project.configs(IntegrationTest).settings(
  crossScalaVersions := allScalaVersions,
  libraryDependencies += `sphere-models`
)

resolvers in ThisBuild += "Typesafe Repo" at "http://repo.typesafe.com/typesafe/releases/"

resolvers in ThisBuild += Resolver.sonatypeRepo("snapshots")

resolvers in ThisBuild += Resolver.sonatypeRepo("releases")

organization in ThisBuild := "io.sphere.sdk.jvm"

libraryDependencies in ThisBuild ++=
  "io.sphere.sdk.jvm" % "sphere-models" % jvmSdkVersion % "test" ::
  "org.easytesting" % "fest-assert" % "1.4" % "test" ::
    "org.assertj" % "assertj-core" % "3.0.0" % "test" ::
  "junit" % "junit-dep" % "4.11" % "test" ::
  "com.novocode" % "junit-interface" % "0.10" % "test" ::
  Nil


javacOptions in ThisBuild ++= Seq("-deprecation", "-Xlint:unchecked", "-source", "1.8", "-target", "1.8", "-Xlint:all", "-Xlint:-options", "-Xlint:-path", "-parameters")

javacOptions in (Compile, doc) in ThisBuild := Nil

scalaVersion in ThisBuild := scala210

scalacOptions in ThisBuild ++= Seq("-deprecation", "-unchecked", "-feature", "-language:implicitConversions", "-language:postfixOps")

Defaults.itSettings

releaseSettings

Release.publishSettings

licenses in ThisBuild := Seq("Apache" -> url("http://www.apache.org/licenses/LICENSE-2.0"))

homepage in ThisBuild := Some(url("https://github.com/sphereio/sphere-jvm-sdk-scala-add-ons"))
