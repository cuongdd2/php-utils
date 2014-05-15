package com.sandinh


object PhpObject {

  def stringify(o: Object) = new Stringifier().write(o)

  def stringify(o: Int) = new Stringifier().write(o.asInstanceOf[Integer])

  def stringify(o: Double) = new Stringifier().write(o.asInstanceOf[java.lang.Double])

  def stringify(o: Float) = new Stringifier().write(o.asInstanceOf[java.lang.Float])

  def stringify(o: Char) = new Stringifier().write(o.asInstanceOf[Character])

  def parse(s: String) = new Parser(s).parse()

  def parse(b: Array[Byte]) = new Parser(new String(b)).parse()

}