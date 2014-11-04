sphere-jvm-sdk-scala-add-ons
============================

![SPHERE.IO icon](https://admin.sphere.io/assets/images/sphere_logo_rgb_long.png)

[![Build Status](https://travis-ci.org/sphereio/sphere-jvm-sdk-scala-add-ons.png?branch=master)](https://travis-ci.org/sphereio/sphere-jvm-sdk-scala-add-ons)

## Usage for Play 2.3

in build.sbt:

```
libraryDependencies ++= Seq(
  "io.sphere.sdk.jvm" % "models" % "1.0.0-M7",
  "io.sphere.sdk.jvm" % "play-2_3-java-client_2.10" % "1.0.0-M7""
)
```

See classes `io.sphere.sdk.client.PlayJavaClient` and `io.sphere.sdk.client.PlayJavaClientImpl`.
