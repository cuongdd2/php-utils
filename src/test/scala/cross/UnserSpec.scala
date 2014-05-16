package cross

import com.sandinh.phputils.PhpObject
import PhpObject.parse
import java.io.{BufferedInputStream, FileInputStream}
import org.scalatest.FunSuite
import sys.process._

class UnserSpec extends FunSuite {
  def unser(): Any = {
    val bis = new BufferedInputStream(new FileInputStream(Data.dataFile))
    val bytes = Stream.continually(bis.read).takeWhile(_ != -1).map(_.toByte).toArray
    val ret = parse(bytes)
    bis.close()
    ret
  }

  test("ser from PHP should be validated in PHP unser") {
    "php src/test/php/ser.php".!!

    //if php test success => exit code == 0
    //else => exit code == 1
    // => "RuntimeException: Nonzero exit value: 1"
    // => test fail
    "php src/test/php/unser.php".!!
  }

  test("ser from PHP should be validated in SCALA unser") {
    "php src/test/php/ser.php".!!
    unser() match {
      case result: Map[_, _] =>
        assert(result.values.dropRight(1).sameElements(Data.data.dropRight(1)))
        val dummyActual = result.values.last
        assert(dummyActual == Data.dummyExpected)

      case _ => fail()
    }
  }
}
