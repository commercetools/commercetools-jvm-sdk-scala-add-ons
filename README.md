commercetools-jvm-sdk-scala-add-ons
============================

![SPHERE.IO icon](https://admin.sphere.io/assets/images/sphere_logo_rgb_long.png)

[![][travis img]][travis]
[![][maven img]][maven]
[![][license img]][license]

This repository is for in Scala implemented stuff to extend the https://github.com/commercetools/commercetools-jvm-sdk .

Search for your Scala version on [Maven Central](http://search.maven.org/#search%7Cga%7C1%7Cg%3A%22com.commercetools.sdk.jvm.scala-add-ons%22).
We support 2.10.x, 2.11.x and milestones of 2.12.

## Scala client

[![][maven img]][maven]


```scala
libraryDependencies += "com.commercetools.sdk.jvm.scala-add-ons" %% "commercetools-scala-client" % "version"
```

The Scala client wraps a `SphereClient` and can return Scala's `Future`s:

```scala
object Demo {
    def demoWithScalaClient(sphereClient: SphereClient): Unit = {
      //the client can be created by the apply method
      //of the ScalaSphereClient companion object
      val scalaSphereClient: ScalaSphereClient = ScalaSphereClient(sphereClient)


      //using the ScalaSphereClient instance apply method returns a Scala Future
      import scala.concurrent.Future
      val future: Future[PagedSearchResult[ProductProjection]] =
        scalaSphereClient(ProductProjectionSearch.ofCurrent())
      val future2: Future[PagedSearchResult[ProductProjection]] =
        //same as above with explicit apply method call
        scalaSphereClient.apply(ProductProjectionSearch.ofCurrent())


      //using the execute method returns a CompletionStage
      val completionStage: CompletionStage[PagedSearchResult[ProductProjection]] =
        scalaSphereClient.execute(ProductProjectionSearch.ofCurrent())

      //the ScalaSphereClient can be used in every SphereClient context
      val upcasted: SphereClient = scalaSphereClient
    }
}
```

## Scala models

[![][maven img]][maven]

```scala
libraryDependencies += "com.commercetools.sdk.jvm.scala-add-ons" %% "commercetools-scala-models" % "version"
```
Old Scala versions do not support Java 8 lambdas which are an important part in the commercetools JVM SDK.
This module provides implicit conversions to support Scala lambdas.

```scala
import io.sphere.sdk.queries.Implicits._
val query = ProductProjectionQuery.ofStaged
val queryById = query.withPredicatesScala(_.id.is("foo"))
val sortById = query.withSortScala(_.id.sort.asc)
val expandedQuery = query.plusExpansionPathsScala(_.productType)
```


[travis]:https://travis-ci.org/commercetools/commercetools-jvm-sdk-scala-add-ons
[travis img]:https://travis-ci.org/commercetools/commercetools-jvm-sdk-scala-add-ons.svg?branch=master

[maven]:http://search.maven.org/#search|gav|1|g:"com.commercetools.sdk.jvm.scala-add-ons"%20AND%20a:"commercetools-scala-client"
[maven img]:https://maven-badges.herokuapp.com/maven-central/com.commercetools.sdk.jvm.scala-add-ons/commercetools-scala-client_2.11/badge.svg

[license]:LICENSE.md
[license img]:https://img.shields.io/badge/License-Apache%202-blue.svg
