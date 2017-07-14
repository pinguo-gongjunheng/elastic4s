package com.sksamuel.elastic4s.http.search.aggs

import com.sksamuel.elastic4s.http.ScriptBuilderFn
import com.sksamuel.elastic4s.searches.aggs.MinAggregationDefinition
import org.elasticsearch.common.xcontent.{XContentBuilder, XContentFactory, XContentType}

object MinAggregationBuilder {
  def apply(agg: MinAggregationDefinition): XContentBuilder = {
    val builder = XContentFactory.jsonBuilder()
    builder.startObject()
    builder.startObject("min")
    agg.field.foreach(builder.field("field", _))
    agg.missing.foreach(builder.field("missing", _))
    agg.script.foreach { script =>
      builder.rawField("script", ScriptBuilderFn(script).bytes, XContentType.JSON)
    }
    builder.endObject()
    builder.endObject()
  }
}


