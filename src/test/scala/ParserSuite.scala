import com.sandinh.PhpObject
import org.scalatest.FunSuite

class ParserSuite extends FunSuite {

  test("to-string") {
    val data = "s:3:\"abc\";"
    val result = PhpObject.parse(data)
    println(result)
    assert(result.isInstanceOf[String])
  }
  test("to-int") {
    val data = "i:123456;"
    val result = PhpObject.parse(data)
    println(result)
    assert(result.isInstanceOf[Int])
  }
  test("to-double") {
    val data = "d:123.456;"
    val result = PhpObject.parse(data)
    println(result)
    assert(result.isInstanceOf[Double])
  }
  test("to-array") {
    val data = "a:2:{i:0;s:2:\"e1\";i:1;s:2:\"e2\";}"
    val result = PhpObject.parse(data)
    println(result.asInstanceOf[Map[Int, String]](0))
    println(result)
    assert(result.isInstanceOf[Map[Int, String]])
  }
  test("to-array2") {
    val data = "a:2:{s:2:\"k1\";s:6:\"value1\";s:2:\"k2\";s:14:\"Tiáº¿ng Viá»\u0087t\";}"
    val result = PhpObject.parse(data)
    println(result)
    assert(result.isInstanceOf[Map[String, String]])
  }

}
