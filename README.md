php-utils
=========

Utils for serialize/un-serialize Scala object to PHP serialized string 

### Sample usage
```scala
	PhpObject.stringify(Map("key" -> "value", "key2" -> "value2")
	PhpObject.parse("a:2:{i:0;i:123;i:1;s:3:\"abc\";}")
```
### Output
	a:2:{s:1:"a";s:5:"hello";s:1:"b";s:3:"boy";}
	Map(0 -> 123, 1 -> "abc")

### See test cases for more information :D
