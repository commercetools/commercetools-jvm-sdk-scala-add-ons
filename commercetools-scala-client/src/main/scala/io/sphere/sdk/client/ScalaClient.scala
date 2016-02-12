package io.sphere.sdk.client

import java.util.concurrent.CompletionStage
import java.util.Objects._

import scala.concurrent.Future

object ScalaClient {
  def apply(sphereClient: SphereClient): ScalaClient = {
    new ScalaClientImpl(requireNonNull(sphereClient, "Underlying client instance should not be null."))
  }
}

trait ScalaClient extends SphereClient {
  def apply[T](sphereRequest: SphereRequest[T]): Future[T]
  
  def close(): Unit
}

private[client] class ScalaClientImpl(sphereClient: SphereClient) extends ScalaClient {

  import ScalaAsync._

  override def execute[T](sphereRequest: SphereRequest[T]): CompletionStage[T] = sphereClient.execute(sphereRequest)

  override def apply[T](SphereRequest: SphereRequest[T]): Future[T] = execute(SphereRequest).asScala

  override def close(): Unit = sphereClient.close()
}

private[client] object ScalaAsync {
  import scala.concurrent.{Promise => ScalaPromise, Future}

  implicit class RichCompletableFuture[T](future: CompletionStage[T]) {
    def asScala = asFuture(future)
  }

  def asFuture[T](completableFuture: CompletionStage[T]): Future[T] = {
    val promise: ScalaPromise[T] = ScalaPromise()
    completableFuture.whenComplete(new CompletableFutureMapper(promise))
    return promise.future
  }
}