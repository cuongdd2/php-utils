import com.sandinh.PhpObject
import org.scalatest.FunSuite

class StringifySuite extends FunSuite{

  test("from-long") {
    val data = 123456789012345L
    val result = PhpObject.stringify(data)
    println(result)
    assert(result contains "d:")
  }
  test("from-int") {
    val data = 123
    val result = PhpObject.stringify(data)
    println(result)
    assert(result contains "i:" + data)
  }
  test("from-short") {
    val data = 123.toShort
    val result = PhpObject.stringify(data)
    println(result)
    assert(result contains "i:" + data)
  }
  test("from-byte") {
    val data = 123.toByte
    val result = PhpObject.stringify(data)
    println(result)
    assert(result contains "i:" + data)
  }
  test("from-double") {
    val data = 123.4567
    val result = PhpObject.stringify(data)
    println(result)
    assert(result contains "d:" + data)
  }
  test("from-float") {
    val data = 123.4567F
    val result = PhpObject.stringify(data)
    println(result)
    assert(result contains "d:" + data)
  }

  test("from-string") {
    val text = "This is a string"
    val result = PhpObject.stringify(text)
    println(result)
    assert(result contains "s:" + text.length + ":\"" + text + "\"")
  }
  test("from-string-unicode") {
    val text = "Tiếng Việt"
    val result = PhpObject.stringify(text)
    println(result)
    assert(result contains "s:")
  }
  test("from-char") {
    val text = "a".charAt(0)
    val result = PhpObject.stringify(text)
    println(result)
    assert(result contains "s:1:\"" + text + "\"")
  }

  test("from-map") {
    val data = Map("a" -> "hello", "b" -> "boy")
    val result = PhpObject.stringify(data)
    println(result)
    assert(result contains "a:2:{s:")
  }

  test("from-array") {
    val data = Array("viet", "nam")
    val result = PhpObject.stringify(data)
    println(result)
    assert(result contains "a:2:{i:0;")
  }
  test("from-array2") {
    val data = Array(123, "abc")
    val result = PhpObject.stringify(data)
    println(result)
    assert(result contains "a:2:{i:0;")
  }
  test("from-object") {
    val data = new DummyClass("abc", 123, 0.999)
    val result = PhpObject.stringify(data)
    println(result)
    assert(result contains "O:")
  }

}
class DummyClass(val var1:String, val var2:Int, val var3:Double) extends Serializable {

}