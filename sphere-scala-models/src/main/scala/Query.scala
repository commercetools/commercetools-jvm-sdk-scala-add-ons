package io.sphere.sdk.queries


import io.sphere.sdk.expansion.{MetaModelExpansionDsl, ExpansionPath}
import io.sphere.sdk.util.functional.ScalaFunctionConversions._
import scala.collection.JavaConversions._
import scala.collection.JavaConverters._

object Implicits {
  implicit class RichMetaModelQueryDsl[T,C <: MetaModelQueryDsl[T,C,Q,E],Q,E](model: MetaModelQueryDsl[T,C,Q,E]) {
    def withPredicatesScala(f: scala.Function1[Q, QueryPredicate[T]]): C = {
      val function: java.util.function.Function[Q, QueryPredicate[T]] = s2j(f)
      model.withPredicates(function)
    }

    def withSortScala(f: scala.Function1[Q, QuerySort[T]]): C = {
      val function: java.util.function.Function[Q, QuerySort[T]] = s2j(f)
      model.withSort(function)
    }

    def withSortMultiScala(f: scala.Function1[Q, scala.List[QuerySort[T]]]): C = {
      val function: java.util.function.Function[Q, java.util.List[QuerySort[T]]] = s2j(f.andThen(_.asJava.toList))
      model.withSortMulti(function)
    }
  }

  implicit class RichMetaModelGetDsl[T,C <: MetaModelExpansionDsl[T,C,E],E](model: MetaModelExpansionDsl[T,C,E]) {
    def plusExpansionPathsScala(f: scala.Function1[E, ExpansionPath[T]]): C = {
      val function: java.util.function.Function[E, ExpansionPath[T]] = s2j(f)
      model.plusExpansionPaths(function)
    }

    def withExpansionPathsScala(f: scala.Function1[E, ExpansionPath[T]]): C = {
      val function: java.util.function.Function[E, ExpansionPath[T]] = s2j(f)
      model.withExpansionPaths(function)
    }
  }
}