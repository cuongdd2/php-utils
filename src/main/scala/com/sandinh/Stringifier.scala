package com.sandinh

import scala.collection.mutable.ArrayBuffer
import java.lang.reflect.Modifier
import java.nio.charset.Charset

class SerializeException(s: String) extends Exception

class Stringifier {
  private val charset = Charset.forName("UTF-8")
  private val refs = ArrayBuffer.empty[Any]

  def write(o: Object): String = {
    val buffer = new StringBuffer()
    serObject(o, buffer)
    buffer.toString
  }

  private def serObject(o: Any, buffer: StringBuffer, allowReference: Boolean = true) = {
    if (o == null) serNull(buffer)
    else if (allowReference && serReference(o, buffer)) true
    else o match {
      case s: String => serString(s, buffer)
      case s: Char => serCharacter(s, buffer)
      case s: Int => serInteger(s, buffer)
      case s: Short => serInteger(s.toInt, buffer)
      case s: Byte => serInteger(s.toInt, buffer)
      case s: Long => serLong(s, buffer)
      case s: Double => serDouble(s, buffer)
      case s: Float => serFloat(s, buffer)
      case s: Boolean => serBoolean(s, buffer)
      case s: Array[Any] => serArray(s, buffer)
      case s: Map[Any, Any] => serMap(s, buffer)
      case s: Iterable[Any] => serCollection(s, buffer)
      case s: Serializable => serSerializable(s, buffer)
      case _ => throw new SerializeException("Unable to ser " + o.getClass.getName)
    }
    refs += o
  }

  private def serReference(o: Any, buffer: StringBuffer): Boolean = {

    // Don't allow references for simple types because here PHP and
    // Java are VERY different and the best way it to simply disallow
    // References for these types
    if (o.isInstanceOf[Number] || o.isInstanceOf[Boolean] || o.isInstanceOf[String]) return false
    val index = refs.indexOf(o)
    if (index != -1) {
      buffer.append("R:").append(index + 1).append(";")
      true
    } else false
  }

  private def serString(string: String, buffer: StringBuffer) {
    val decoded: String = decode(string, charset)
    buffer.append("s:").append(decoded.length).append(":\"").append(decoded).append("\";")
  }

  private def serCharacter(value: Char, buffer: StringBuffer) {
    buffer.append("s:1:\"").append(value).append("\";")
  }

  private def serNull(buffer: StringBuffer) {
    buffer.append("N;")
  }

  private def serInteger(number: Int, buffer: StringBuffer) {
    buffer.append("i:").append(number).append(";")
  }

  private def serLong(number: Long, buffer: StringBuffer) {
    if ((number >= Integer.MIN_VALUE) && (number <= Integer.MAX_VALUE)) buffer.append("i:")
    else buffer.append("d:")
    buffer.append(number).append(";")
  }

  private def serDouble(number: Double, buffer: StringBuffer) {
    buffer.append("d:").append(number).append(";")
  }

  private def serFloat(number: Float, buffer: StringBuffer) {
    buffer.append("d:").append(number).append(";")
  }

  private def serBoolean(value: Boolean, buffer: StringBuffer) {
    buffer.append("b:").append(if (value) 1 else 0).append(";")
  }

  private def serCollection(collection: Iterable[Any], buffer: StringBuffer) {
    refs += collection
    buffer.append("a:").append(collection.size).append(":{")
    val iterator = collection.iterator
    var index = 0
    while (iterator.hasNext) {
      serObject(index, buffer, allowReference = false)
      refs.trimEnd(1)
      serObject(iterator.next(), buffer)
      index += 1
    }
    buffer.append('}')
  }

  private def serArray(array: Array[Any], buffer: StringBuffer) {
    refs += array
    val size = array.length
    buffer.append("a:").append(size).append(":{")
    for (i <- 0 until size) {
      serObject(i, buffer, allowReference = false)
      refs.trimEnd(1)
      serObject(array(i), buffer)
    }
    buffer.append('}')
  }

  private def serMap(map: Map[Any, Any], buffer: StringBuffer) {
    refs += map
    buffer.append("a:").append(map.size).append(":{")
    map.foreach(e => {
      serObject(e._1, buffer, allowReference = false)
      refs.trimEnd(1)
      serObject(e._2, buffer)
    })
    buffer.append('}')
  }


  private def serSerializable(o: Serializable, buffer: StringBuffer) {

    refs += o
    var c: Class[_] = o.getClass
    val className = c.getSimpleName
    buffer.append("O:").append(className.length()).append(":\"").append(className).append("\":")

    val fieldBuffer = new StringBuffer()
    var fieldCount = 0
    while (c != null) {
      val fields = c.getDeclaredFields
      for (i <- 0 until fields.length) {
        val field = fields(i)
        if (!Modifier.isStatic(field.getModifiers) || !Modifier.isVolatile(field.getModifiers))
          try {
            field.setAccessible(true)
            serObject(field.getName, fieldBuffer)
            refs.trimEnd(1)
            serObject(field.get(o), fieldBuffer)
            fieldCount += 1
          } catch {
            case e: Exception => println("unable serialize " + className)
          }
      }
      c = c.getSuperclass
    }
    buffer.append(fieldCount).append(":{").append(fieldBuffer).append("}")
  }
}

