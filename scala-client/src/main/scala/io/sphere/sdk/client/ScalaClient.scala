package io.sphere.sdk.client

import java.io.Closeable
import java.util.concurrent.CompletableFuture


import scala.concurrent.Future

trait ScalaClient extends Closeable {
  def execute[T](SphereRequest: SphereRequest[T]): Future[T]
  
  def close(): Unit
}

private[client] class ScalaClientImpl(sphereClient: SphereClient) extends ScalaClient {

  import ScalaAsync._

  override def execute[T](SphereRequest: SphereRequest[T]): Future[T] = sphereClient.execute(SphereRequest).asScala

  override def close(): Unit = sphereClient.close()
}

private[client] object ScalaAsync {
  import scala.concurrent.{Promise => ScalaPromise, Future}

  implicit class RichCompletableFuture[T](future: CompletableFuture[T]) {
    def asScala = asFuture(future)
  }

  def asFuture[T](completableFuture: CompletableFuture[T]): Future[T] = {
    val promise: ScalaPromise[T] = ScalaPromise()
    completableFuture.whenComplete(new CompletableFutureMapper(promise));
    return promise.future
  }
}