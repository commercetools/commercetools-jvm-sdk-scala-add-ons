import sbt._
import Keys._
import sbt.Extracted
import sbtrelease.ReleasePlugin._
import sbtrelease._
import ReleaseStateTransformations._
import sbtrelease.ReleasePlugin.ReleaseKeys._

object Release {
  private val pathToPgpPassphrase = System.getProperty("user.home") + "/.sbt/gpg/passphrase"

  private def task2ReleaseStep(task: sbt.TaskKey[scala.Unit]) = {
    val action = { st: State =>
      val extracted: Extracted = Project.extract(st)
      val ref = extracted.get(thisProjectRef)
      extracted.runAggregated(task in Global in ref, st)
    }
    ReleaseStep(action = action, enableCrossBuild = true)
  }

  lazy val publishSignedArtifactsStep = ReleaseStep(action = publishSignedArtifactsAction, enableCrossBuild = true)
  lazy val publishSignedArtifactsAction = { st: State =>
    val extracted: Extracted = Project.extract(st)
    val ref = extracted.get(thisProjectRef)
    extracted.runAggregated(com.typesafe.sbt.pgp.PgpKeys.publishSigned in Global in ref, st)
  }


  lazy val publishSettings = Seq(
    publishTo in ThisBuild <<= version { (v: String) =>
      val nexus = "https://oss.sonatype.org/"
      if (v.trim.endsWith("SNAPSHOT"))
        Some("snapshots" at nexus + "content/repositories/snapshots")
      else
        Some("releases"  at nexus + "service/local/staging/deploy/maven2")
    },
    com.typesafe.sbt.pgp.PgpKeys.pgpPassphrase in Global := {
      val pgpPassphraseFile = file(pathToPgpPassphrase)
      if(pgpPassphraseFile.exists && pgpPassphraseFile.canRead) {
        Option(IO.read(pgpPassphraseFile).toCharArray)
      } else
        None
    },
    publishMavenStyle in ThisBuild := true,
    publishArtifact in Test in ThisBuild := false,
    pomExtra in ThisBuild := (
      <scm>
        <url>git@github.com:commercetools/sphere-play-sdk.git</url>
        <connection>scm:git:git@github.com:commercetools/sphere-play-sdk.git</connection>
      </scm>
        <developers>
          <developer>
            <id>michael</id>
            <name>Michael Schleichardt</name>
            <url>https://github.com/schleichardt</url>
          </developer>
          <developer>
            <id>laura</id>
            <name>Laura Luiz</name>
            <url>https://github.com/lauraluiz</url>
          </developer>
        </developers>
      ),
    ReleaseKeys.releaseProcess := Seq[ReleaseStep](
      checkSnapshotDependencies,
      inquireVersions,
      runTest,
      setReleaseVersion,
      commitReleaseVersion,
      tagRelease,
      task2ReleaseStep(com.typesafe.sbt.pgp.PgpKeys.publishSigned),
      setNextVersion,
      commitNextVersion,
      pushChanges
    )
  )

}