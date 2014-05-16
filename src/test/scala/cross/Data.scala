package cross

class DummyClass {
  val var1 = "đĐe"
  val var2 = 123
  val var3 = 0.999
  val var4 = null
  val var5 = new {
    val x = Map(0 -> 123.4567, "k1" -> "value1", "k2" -> "Tiếng Việt")
  }
  val var6 = "a dynamic var"
}

object Data {
  val dataFile = "/tmp/php-ser.data"
  val dummy = new DummyClass

  val data = Array(
    123456789012345L,
    123456789012345D,
    123.456,
    123.4567F,
    "Tiếng Việt",
    Map(0 -> "Cường", 1 -> "Đỗ", 2 -> "Đức", 3 -> "Gia", 4 -> "Bảo"),
    Map(0 -> 123, 1 -> "abc"),
    Map("k1" -> "value1", "k2" -> "Tiếng Việt"),
    Map("k1" -> 123, "k2" ->
      """aAàÀảẢãÃáÁạẠăĂằẰẳẲẵẴắẮặẶâÂầẦẩẨẫẪấẤậẬbBcCdDđĐeEèÈẻẺẽẼéÉẹẸêÊềỀểỂễỄếẾệỆ
fFgGhHiIìÌỉỈĩĨíÍịỊjJkKlLmMnNoOòÒỏỎõÕóÓọỌôÔồỒổỔỗỖốỐộỘơƠờỜởỞỡỠớỚợỢpPqQrRsStTu
UùÙủỦũŨúÚụỤưƯừỪửỬữỮứỨựỰvVwWxXyYỳỲỷỶỹỸýÝỵỴzZ"""),
    dummy
  )
  val dummyExpected = Map(
    "var1" -> dummy.var1,
    "var2" -> dummy.var2,
    "var3" -> dummy.var3,
    "var4" -> dummy.var4,
    "var5" -> Map("x" -> dummy.var5.x),
    "var6" -> dummy.var6
  )
}
