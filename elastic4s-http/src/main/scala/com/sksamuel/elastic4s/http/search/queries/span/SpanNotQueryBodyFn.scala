package com.sksamuel.elastic4s.http.search.queries.span

import com.sksamuel.elastic4s.http.search.queries.QueryBuilderFn
import com.sksamuel.elastic4s.searches.queries.span.SpanNotQueryDefinition
import org.elasticsearch.common.xcontent.{XContentBuilder, XContentFactory, XContentType}

object SpanNotQueryBodyFn {
  def apply(q: SpanNotQueryDefinition): XContentBuilder = {
    val builder = XContentFactory.jsonBuilder()

    builder.startObject()
    builder.startObject("span_not")
    builder.rawField("include", QueryBuilderFn(q.include).bytes, XContentType.JSON)
    builder.rawField("exclude", QueryBuilderFn(q.exclude).bytes, XContentType.JSON)

    q.pre.foreach(builder.field("pre", _))
    q.post.foreach(builder.field("post", _))
    q.dist.foreach(builder.field("dist", _))
    q.boost.foreach(builder.field("boost", _))
    q.queryName.foreach(builder.field("_name", _))

    builder.endObject()
    builder.endObject()
  }
}
