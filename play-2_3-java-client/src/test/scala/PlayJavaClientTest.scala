import java.util.Optional
import com.typesafe.config.ConfigFactory
import io.sphere.sdk.client._
import io.sphere.sdk.http.{HttpRequest, Requestable, HttpResponse}
import org.junit.Test
import org.fest.assertions.Assertions.assertThat

import java.util.function.Function

class PlaySphereClientTest {

  val config = ConfigFactory.load()

  def withClient(client: PlayJavaSphereClient)(body: PlayJavaSphereClient => Unit) {
    try {
      body(client)
    } finally {
      client.close
    }
  }

  @Test
  def `serve fetch requests providing JSON`: Unit = {
    withClient(PlayJavaSphereClientFactory.of().createHttpTestDouble(new Function[HttpRequest, HttpResponse] {
      override def apply(t: HttpRequest): HttpResponse = HttpResponse.of(200, """{"id":1}""")
    })) { client =>
      val service = new XyzService
      val result = client.execute(service.fetchById("1"))
      assertThat(result.get(1000).get()).isEqualTo(new Xyz("1"))
    }
  }

  @Test
  def `serve fetch requests providing instance`: Unit = {
    withClient(PlayJavaSphereClientFactory.of().createObjectTestDouble(new Function[HttpRequest, AnyRef] {
      override def apply(t: HttpRequest): AnyRef = Optional.of(new Xyz("1"))
    })) { client =>
      val service = new XyzService
      val result = client.execute(service.fetchById("1"))
      assertThat(result.get(1000).get()).isEqualTo(new Xyz("1"))
    }
  }
}