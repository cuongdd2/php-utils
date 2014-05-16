package cross

import com.sandinh.phputils.PhpObject
import PhpObject.stringify
import java.io.FileOutputStream
import java.nio.charset.StandardCharsets.UTF_8
import org.scalatest.FunSuite
import sys.process._

class SerSpec extends FunSuite {
  def ser(): Unit = {
    val out = new FileOutputStream(Data.dataFile)
    out.write(stringify(Data.data).getBytes(UTF_8))
    out.close()
  }

  test("ser from PHP should be validated in PHP unser") {
    "php src/test/php/ser.php".!!

    //if php test success => exit code == 0
    //else => exit code == 1
    // => "RuntimeException: Nonzero exit value: 1"
    // => test fail
    "php src/test/php/unser.php".!!
  }

  test("ser from SCALA should be validated in PHP unser") {
    ser()
    "php src/test/php/unser.php".!!
  }
}
