package com

import java.nio.charset.Charset

package object sandinh {

  private val DefaultCharset = Charset.forName("ISO-8859-1")

  def decode(encoded: String, charset: Charset): String = {
    try {
      new String(encoded.getBytes(charset), DefaultCharset)
    } catch {
      case e: UnsupportedOperationException => encoded
    }
  }

  def encode(decoded: String, charset: Charset): String = {
    try {
      new String(decoded.getBytes(DefaultCharset), charset)
    } catch {
      case e: UnsupportedOperationException => decoded
    }
  }
}
