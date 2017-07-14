package com.sksamuel.elastic4s.http

import com.sksamuel.elastic4s.FieldsMapper
import com.sksamuel.elastic4s.script.ScriptDefinition
import org.elasticsearch.common.bytes.{BytesArray, BytesReference}
import org.elasticsearch.common.xcontent.{XContentBuilder, XContentFactory, XContentType}
import org.elasticsearch.script.ScriptType
import org.json4s.DefaultFormats
import org.json4s.jackson.Serialization

object ScriptBuilderFn {
  implicit val format = DefaultFormats

  def apply(script: ScriptDefinition): XContentBuilder = {

    val builder = XContentFactory.jsonBuilder()

    builder.startObject()

    script.lang.foreach(builder.field("lang", _))

    script.scriptType match {
      case ScriptType.FILE => builder.field("file", script.script)
      case ScriptType.INLINE => builder.field("inline", script.script)
    }

    if (script.params.nonEmpty) {
      builder.startObject("params")
      script.params.foreach { case (key, value) =>
        val json = new BytesArray(Serialization.write(value.asInstanceOf[AnyRef]))
        builder.rawField(key, json, XContentType.JSON)
      }
      builder.endObject()
    }

    if (script.options.nonEmpty) {
      builder.startObject("options")
      script.params.foreach { case (key, value) =>
        builder.field(key, value)
      }
      builder.endObject()
    }
    builder.endObject()
  }
}
