package io.sphere.sdk.client

import java.util.function.Function

import io.sphere.sdk.http.{HttpResponse, HttpRequest}
import io.sphere.sdk.models.Base

object ScalaSphereClientFactory extends Base with ClientFactory[ScalaClient] {
  override def createClient(sphereApiConfig: SphereApiConfig, sphereAccessTokenSupplier: SphereAccessTokenSupplier): ScalaClient =
    new ScalaClientImpl(SphereClientFactory.of().createClient(sphereApiConfig, sphereAccessTokenSupplier))

  override def createObjectTestDouble(function: Function[HttpRequest, AnyRef]): ScalaClient =
    new ScalaClientImpl(SphereClientFactory.of().createObjectTestDouble(function))

  override def createClient(sphereClientConfig: SphereClientConfig): ScalaClient =
    new ScalaClientImpl(SphereClientFactory.of().createClient(sphereClientConfig))

  override def createHttpTestDouble(function: Function[HttpRequest, HttpResponse]): ScalaClient =
    new ScalaClientImpl(SphereClientFactory.of().createHttpTestDouble(function))
}
