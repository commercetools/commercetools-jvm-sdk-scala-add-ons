import java.util.Optional
import com.typesafe.config.ConfigFactory
import io.sphere.sdk.client._
import io.sphere.sdk.http.{HttpRequest, Requestable, HttpResponse}
import org.junit.Test
import scala.concurrent.duration.Duration
import scala.concurrent.{Await, Future}
import org.fest.assertions.Assertions.assertThat

import java.util.function.Function

class ScalaClientTest {

  val config = ConfigFactory.load()

  def withClient(client: ScalaClient)(body: ScalaClient => Unit) {
    try {
      body(client)
    } finally {
      client.close
    }
  }

  @Test
  def `serve fetch requests providing JSON`: Unit = {
    withClient(ScalaSphereClientFactory.createHttpTestDouble(new Function[HttpRequest, HttpResponse] {
      override def apply(t: HttpRequest): HttpResponse = HttpResponse.of(200, """{"id":1}""")
    })) { client =>
      val service = new XyzService
      val result = client.execute(service.fetchById("1"))
      assertThat(Await.result(result, Duration("1 s")).get()).isEqualTo(new Xyz("1"))
    }
  }

  @Test
  def `serve fetch requests providing instance`: Unit = {
    withClient(ScalaSphereClientFactory.createObjectTestDouble(new Function[HttpRequest, AnyRef] {
      override def apply(t: HttpRequest): AnyRef = Optional.of(new Xyz("1"))
    })) { client =>
      val service = new XyzService
      val result: Future[Optional[Xyz]] = client.execute(service.fetchById("1"))
      val actual: Xyz = Await.result(result, Duration("1 s")).get()
      assertThat(actual).isEqualTo(new Xyz("1"))
    }
  }
}