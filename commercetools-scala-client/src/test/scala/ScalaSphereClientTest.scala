import java.nio.charset.StandardCharsets
import java.util.concurrent.CompletionStage
import io.sphere.sdk.client._
import io.sphere.sdk.http.{HttpMethod, HttpResponse}
import io.sphere.sdk.products.ProductProjection
import io.sphere.sdk.products.search.ProductProjectionSearch
import io.sphere.sdk.search.PagedSearchResult
import org.junit.Test
import scala.concurrent.duration.Duration
import scala.concurrent.{Await, Future}
import org.fest.assertions.Assertions.assertThat

import java.util.function.Function

class ScalaSphereClientTest {
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



  val intent = HttpRequestIntent.of(HttpMethod.GET, "{\"foo\" : 1}")

  @Test
  def `successful result`: Unit = {
    val client = createClient(new Function[HttpRequestIntent, HttpResponse] {
      override def apply(t: HttpRequestIntent) = HttpResponse.of(200, "la string")
    })
    val request = new SphereRequest[String] {
      override def httpRequestIntent(): HttpRequestIntent = intent

      override def deserialize(httpResponse: HttpResponse) = new Predef.String(httpResponse.getResponseBody, StandardCharsets.UTF_8)
    }
    val future: Future[String] = client(request)
    val result: String = Await.result(future, Duration("1 s"))
    assertThat(result).isEqualTo("la string")
  }

  @Test(expected = classOf[IllegalArgumentException])
  def `exceptional result`: Unit = {
    val client = createClient(new Function[HttpRequestIntent, HttpResponse] {
      override def apply(t: HttpRequestIntent) = HttpResponse.of(200, "la string")
    })
    val future: Future[String] = client.apply(new SphereRequest[String] {
      override def httpRequestIntent(): HttpRequestIntent = intent

      override def deserialize(httpResponse: HttpResponse) = throw new IllegalArgumentException("boo")
    })
    Await.result(future, Duration("1 s"))
  }

  private def createClient(function: Function[HttpRequestIntent, HttpResponse]): ScalaSphereClient = {
    return ScalaSphereClient(TestDoubleSphereClientFactory.createHttpTestDouble(function))
  }
}