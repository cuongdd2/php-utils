package com.sandinh

import scala.collection.mutable.ArrayBuffer
import java.nio.charset.Charset

class UnserializeException(s: String) extends Exception

class Parser(private val data: String) {

  private var pos: Int = 0
  private val charset = Charset.forName("UTF-8")

  private val refs = ArrayBuffer.empty[Any]

  def parse(): Any = {
    val result = data.charAt(pos) match {
      case 's' => unserString()
      case 'i' => unserInteger()
      case 'd' => unserDouble()
      case 'b' => unserBoolean()
      case 'N' => unserNull()
      case 'a' => return unserArray()
      case 'R' => unserReference()
      case 'O' => unserObject()
      case _   => throw new UnserializeException("Unable to unserialize unknown type ")
    }
    refs += result
    result
  }

  private def unserString(): String = {
    val end = data.indexOf(':', pos + 2)
    val length = data.substring(pos + 2, end).toInt
    val raw = data.substring(end + 2, end + 2 + length)
    pos = end + length + 4
    encode(raw, charset)
  }

  private def unserInteger(): Int = {
    val end = data.indexOf(';', pos + 2)
    val result = data.substring(pos + 2, end).toInt
    pos = end + 1
    result
  }

  private def unserDouble(): Double = {
    val end = data.indexOf(';', pos + 2)
    val result = data.substring(pos + 2, end).toDouble
    pos = end + 1
    result
  }

  private def unserReference(): Any = {
    val end = data.indexOf(';', pos + 2)
    val index = data.substring(pos + 2, end).toInt
    pos = end + 1
    refs(index - 1)
  }

  private def unserBoolean(): Boolean = {
    pos += 4
    data.charAt(pos + 2) == '1'
  }

  private def unserNull(): Null = {
    pos += 2
    null
  }

  private def unserArray(): Map[Any, Any] = {
    val end = data.indexOf(':', pos + 2)
    val size = data.substring(pos + 2, end).toInt
    pos = end + 2
    var map = Map.empty[Any, Any]
    refs += map
    for (i <- 0 until size) {
      val key = parse()
      refs.trimEnd(1)
      val value = parse()
      map += key -> value
    }
    pos += 1
    map
  }

  def unserObject(): (String, Map[String, Any]) = {
    val startName = data.indexOf(':', pos + 2)
    val nameLength = data.substring(pos + 2, startName).toInt
    val endName = startName + 2 + nameLength
    val className = data.substring(startName + 2, endName)
    val startField = data.indexOf(":", endName + 2)
    val fieldCount = data.substring(endName + 2, startField).toInt
    var map = Map.empty[String, Any]
    refs += map
    for (i <- 0 until fieldCount) {
      val key = unserString()
      refs.trimEnd(1)
      val value = parse()
      map += key -> value
    }
    pos += 1
    (className, map)
  }

}
