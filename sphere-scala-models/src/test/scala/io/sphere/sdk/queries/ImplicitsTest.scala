package io.sphere.sdk.queries

import io.sphere.sdk.products.ProductProjectionType.STAGED
import io.sphere.sdk.products.queries.{ProductProjectionByIdFetch, ProductProjectionQuery}
import org.junit.Test
import io.sphere.sdk.queries.Implicits._
import org.assertj.core.api.Assertions._

class ImplicitsTest {
  val query = ProductProjectionQuery.ofStaged
  val fetch = ProductProjectionByIdFetch.of("foo", STAGED)

  @Test
  def `withPredicate`: Unit = {
    assertThat(query.withPredicateScala(_.id.is("foo")).predicate.get.toSphereQuery).isEqualTo("""id="foo"""")
  }

  @Test
  def `withSort`: Unit = {
    assertThat(query.withSortScala(_.id.sort.asc).sort.get(0).toSphereSort).isEqualTo("""id asc""")
  }

  @Test
  def `withSortMulti`: Unit = {
    assertThat(query.withSortMultiScala(m => m.id.sort.asc :: Nil).sort.get(0).toSphereSort).isEqualTo("""id asc""")
  }

  @Test
  def `query plusExpansionPaths`: Unit = {
    assertThat(query.plusExpansionPathsScala(_.productType).expansionPaths.get(0).toSphereExpand).isEqualTo("""productType""")
  }

  @Test
  def `query withExpansionPaths`: Unit = {
    assertThat(query.withExpansionPathsScala(_.productType).expansionPaths.get(0).toSphereExpand).isEqualTo("""productType""")
  }

  @Test
  def `fetch plusExpansionPaths`: Unit = {
    assertThat(fetch.plusExpansionPathsScala(_.productType).expansionPaths.get(0).toSphereExpand).isEqualTo("""productType""")
  }

  @Test
  def `fetch withExpansionPaths`: Unit = {
    assertThat(fetch.withExpansionPathsScala(_.productType).expansionPaths.get(0).toSphereExpand).isEqualTo("""productType""")
  }
}
