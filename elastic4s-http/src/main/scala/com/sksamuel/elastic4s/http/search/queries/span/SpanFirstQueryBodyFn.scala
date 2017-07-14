package com.sksamuel.elastic4s.http.search.queries.span

import com.sksamuel.elastic4s.http.search.queries.QueryBuilderFn
import com.sksamuel.elastic4s.searches.queries.span.SpanFirstQueryDefinition
import org.elasticsearch.common.xcontent.{XContentBuilder, XContentFactory, XContentType}

object SpanFirstQueryBodyFn {
  def apply(q: SpanFirstQueryDefinition): XContentBuilder = {
    val builder = XContentFactory.jsonBuilder()
    builder.startObject()
    builder.rawField("span_first", QueryBuilderFn(q.query).bytes, XContentType.JSON)
    q.boost.foreach(builder.field("boost", _))
    q.queryName.foreach(builder.field("_name", _))
    builder.endObject()
    builder.endObject()
  }
}
