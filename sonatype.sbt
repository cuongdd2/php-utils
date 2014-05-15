xerial.sbt.Sonatype.sonatypeSettings

publishMavenStyle := true

publishArtifact in Test := false

pomIncludeRepository := { _ => false }

pomExtra := <url>https://github.com/cuongdd2/php-utils</url>
  <licenses>
    <license>
      <name>Apache 2</name>
      <url>http://www.apache.org/licenses/LICENSE-2.0</url>
      <distribution>repo</distribution>
    </license>
  </licenses>
  <scm>
    <url>git@github.com:cuongdd2/php-utils.git</url>
    <connection>scm:git:git@github.com:cuongdd2/php-utils.git</connection>
  </scm>
  <developers>
    <developer>
      <id>cuongdd2</id>
      <name>Đỗ Đức Cường</name>
      <email>cuongdd2@gmail.com</email>
      <organization>Chắn Pro</organization>
      <organizationUrl>https://chanpro.com</organizationUrl>
    </developer>
    <developer>
      <id>kayahr</id>
      <name>Klaus Reimer</name>
      <email>k@ailis.de</email>
      <url>http://www.ailis.de/~k/</url>
    </developer>
  </developers>
