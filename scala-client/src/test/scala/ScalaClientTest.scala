import java.nio.charset.StandardCharsets
import io.sphere.sdk.client._
import io.sphere.sdk.http.{HttpMethod, HttpResponse}
import org.junit.Test
import scala.concurrent.duration.Duration
import scala.concurrent.{Await, Future}
import org.fest.assertions.Assertions.assertThat

import java.util.function.Function

class ScalaClientTest {
  val intent = HttpRequestIntent.of(HttpMethod.GET, "{\"foo\" : 1}")

  @Test
  def `successful result`: Unit = {
    val client = createClient(new Function[HttpRequestIntent, HttpResponse] {
      override def apply(t: HttpRequestIntent) = HttpResponse.of(200, "la string")
    })
    val future: Future[String] = client(new SphereRequest[String] {
      override def httpRequestIntent(): HttpRequestIntent = intent

      override def resultMapper() = new Function[HttpResponse, String] {
        override def apply(t: HttpResponse): String = new String(t.getResponseBody.get, StandardCharsets.UTF_8)
      }
    })
    val result: String = Await.result(future, Duration("1 s"))
    assertThat(result).isEqualTo("la string")
  }

  @Test(expected = classOf[IllegalArgumentException])
  def `exceptional result`: Unit = {
    val client = createClient(new Function[HttpRequestIntent, HttpResponse] {
      override def apply(t: HttpRequestIntent) = HttpResponse.of(200, "la string")
    })
    val future: Future[String] = client(new SphereRequest[String] {
      override def httpRequestIntent(): HttpRequestIntent = intent

      override def resultMapper() = new Function[HttpResponse, String] {
        override def apply(t: HttpResponse): String = throw new IllegalArgumentException("boo")
      }
    })
    Await.result(future, Duration("1 s"))
  }

  private def createClient(function: Function[HttpRequestIntent, HttpResponse]): ScalaClient = {
    return ScalaClient(SphereClientFactory.of.createHttpTestDouble(function))
  }
}