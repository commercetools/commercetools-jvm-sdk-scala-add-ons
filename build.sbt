autoScalaLibrary := true

organization in ThisBuild := "com.commercetools.sdk.jvm.scala-add-ons"

val jvmSdkCoreVersion = "1.0.0-RC1"
val jvmSdkCoreOrganization = "com.commercetools.sdk.jvm.core"
val jvmSdkModelsName = "commercetools-models"
val `commercetools-models` = jvmSdkCoreOrganization % jvmSdkModelsName % jvmSdkCoreVersion
val `commercetools-java-client-core` = jvmSdkCoreOrganization % "commercetools-java-client-core" % jvmSdkCoreVersion
val commercetoolsAhc18 = jvmSdkCoreOrganization % "commercetools-java-client-ahc-1_8" % jvmSdkCoreVersion
val commercetoolsAhc19 = jvmSdkCoreOrganization % "commercetools-java-client-ahc-1_9" % jvmSdkCoreVersion
val commercetoolsAhc20 = jvmSdkCoreOrganization % "commercetools-java-client-ahc-2_0" % jvmSdkCoreVersion
val scala210 = "2.10.6"
val scala211 = "2.11.7"
val scala212 = "2.12.0-M3"

val allScalaVersions = Seq(scala210, scala211, scala212)

crossPaths := true

crossScalaVersions in ThisBuild := allScalaVersions

lazy val root = (project in file(".")).configs(IntegrationTest).aggregate(`commercetools-play-2_5-java-client`, `commercetools-play-2_4-java-client`, `commercetools-play-2_3-java-client`, `commercetools-play-2_2-java-client`, `commercetools-scala-client`, `commercetools-scala-models`).settings(
  packagedArtifacts := Map.empty,
  name := "commercetools-jvm-sdk-scala-add-ons"
)

lazy val `commercetools-play-2_5-java-client` = project.configs(IntegrationTest).settings(
  libraryDependencies ++= (if(crossScalaVersions.value.contains(scalaVersion.value)) Seq("com.typesafe.play" %% "play-java" % "2.5.0", commercetoolsAhc20) else Nil),
  crossScalaVersions := Seq(scala211),
  packagedArtifacts := (if(crossScalaVersions.value.contains(scalaVersion.value)) packagedArtifacts.value else Map.empty),
  sourceDirectory := (if(crossScalaVersions.value.contains(scalaVersion.value)) sourceDirectory.value else IO.temporaryDirectory)
)

lazy val `commercetools-play-2_4-java-client` = project.configs(IntegrationTest).settings(
  libraryDependencies ++= (if(crossScalaVersions.value.contains(scalaVersion.value)) Seq("com.typesafe.play" %% "play-java" % "2.4.6", commercetoolsAhc19) else Nil),
  crossScalaVersions := Seq(scala210, scala211),
  packagedArtifacts := (if(crossScalaVersions.value.contains(scalaVersion.value)) packagedArtifacts.value else Map.empty),
  sourceDirectory := (if(crossScalaVersions.value.contains(scalaVersion.value)) sourceDirectory.value else IO.temporaryDirectory)
)

lazy val `commercetools-play-2_3-java-client` = project.configs(IntegrationTest).settings(
  libraryDependencies ++= (if(crossScalaVersions.value.contains(scalaVersion.value)) Seq("com.typesafe.play" %% "play-java" % "2.3.10", commercetoolsAhc18) else Nil),
  crossScalaVersions := Seq(scala210, scala211),
  packagedArtifacts := (if(crossScalaVersions.value.contains(scalaVersion.value)) packagedArtifacts.value else Map.empty),
  sourceDirectory := (if(crossScalaVersions.value.contains(scalaVersion.value)) sourceDirectory.value else IO.temporaryDirectory)
)

lazy val `commercetools-play-2_2-java-client` = project.configs(IntegrationTest).settings(
  libraryDependencies ++= (if(crossScalaVersions.value.contains(scalaVersion.value)) (commercetoolsAhc18 :: ("com.typesafe.play" %% "play-java" % "2.2.6" exclude("org.yaml", "snakeyaml") exclude("org.hibernate", "hibernate-validator") exclude("org.springframework", "spring-context") exclude("org.springframework", "spring-core") exclude("org.springframework", "spring-beans") exclude("javax.servlet", "javax.servlet-api") exclude("com.typesafe.play", "play-json_2.10") exclude("com.typesafe.play", "templates_2.10")) :: Nil) else Nil),
  crossScalaVersions := Seq(scala210),
  packagedArtifacts := (if(crossScalaVersions.value.contains(scalaVersion.value)) packagedArtifacts.value else Map.empty),
  sourceDirectory := (if(crossScalaVersions.value.contains(scalaVersion.value)) sourceDirectory.value else IO.temporaryDirectory)
)

lazy val `commercetools-scala-client` = project.configs(IntegrationTest).settings(
  crossScalaVersions := allScalaVersions,
  libraryDependencies += `commercetools-java-client-core`,
  libraryDependencies += commercetoolsAhc18 % "test,it"
)

lazy val `commercetools-scala-models` = project.configs(IntegrationTest).settings(
  crossScalaVersions := allScalaVersions,
  libraryDependencies += `commercetools-models`
)

resolvers in ThisBuild += "Typesafe Repo" at "http://repo.typesafe.com/typesafe/releases/"

resolvers in ThisBuild += Resolver.sonatypeRepo("snapshots")

resolvers in ThisBuild += Resolver.sonatypeRepo("releases")

libraryDependencies in ThisBuild ++=
  jvmSdkCoreOrganization % jvmSdkModelsName % jvmSdkCoreVersion % "test" ::
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

homepage in ThisBuild := Some(url("https://github.com/commercetools/commercetools-jvm-sdk-scala-add-ons"))
