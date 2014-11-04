import java.util.Optional
import com.typesafe.config.ConfigFactory
import io.sphere.sdk.client._
import io.sphere.sdk.http.{HttpClientTestDouble, Requestable, HttpResponse, ClientRequest}
import org.junit.Test
import org.fest.assertions.Assertions.assertThat

class PlayJavaClientTest {

  val config = ConfigFactory.load()

  def withClient(client: PlayJavaClient)(body: PlayJavaClient => Unit) {
    try {
      body(client)
    } finally {
      client.close
    }
  }

  @Test
  def `serve fetch requests providing JSON`: Unit = {
    withClient(new PlayJavaClientImpl(config, new HttpClientTestDouble {
      override def testDouble(requestable: Requestable) = HttpResponse.of(200, """{"id":1}""")
    })) { client =>
      val service = new XyzService
      val result = client.execute(service.fetchById("1"))
      assertThat(result.get(1000).get()).isEqualTo(new Xyz("1"))
    }
  }

  @Test
  def `serve fetch requests providing instance`: Unit = {
    withClient(new PlayJavaClientImpl(config, new SphereRequestExecutorTestDouble {
      override protected def result[T](fetch: ClientRequest[T]): T = Optional.of(new Xyz("1")).asInstanceOf[T]
    })) { client =>
      val service = new XyzService
      val result = client.execute(service.fetchById("1"))
      assertThat(result.get(1000).get()).isEqualTo(new Xyz("1"))
    }
  }
}