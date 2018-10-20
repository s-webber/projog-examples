# Debugging Prolog from Java

## About

This module provides an example of how you can obtain debug information about Prolog queries that are called from Java programs using [Projog](http://www.projog.org/).

`src/main/java/com/example/TraceExample.java` performs queries on the facts and rules contained in `src/main/resources/TraceExample.pl` and produces output that matches what is contained in `src/test/resources/TraceExampleTestExpectedOutput.txt`.

Note that this module has a dependency on the `0.3.0-SNAPSHOT` version of `projog-core`.

## Resolving Gradle build errors

If when doing a Gradle build you get a `Could not resolve all files for configuration` error like:

```
FAILURE: Build failed with an exception.

* What went wrong:
Could not resolve all files for configuration ':compileClasspath'.
> Could not find org.projog:projog-core:0.3.0-SNAPSHOT.
```

then you will need to add the URL of the snapshots repository to your `build.gradle` file. e.g.

```
repositories {
   maven { url "https://oss.sonatype.org/content/repositories/snapshots" }
   maven { url "http://repo.maven.apache.org/maven2" }
}
```

See [this projects build.gradle file](https://raw.githubusercontent.com/s-webber/projog-examples/master/debugging-prolog-from-java/build.gradle) for a full example of suitable `build.gradle` file.

## Resolving Maven build errors

If when attempting to do a Maven build of this module you get a `Could not resolve dependencies` error like:

```
[ERROR] Failed to execute goal on project debugging-prolog-from-java: Could not
resolve dependencies for project org.projog:debugging-prolog-from-java:jar:0.1.0
-SNAPSHOT: Could not find artifact org.projog:projog-core:jar:0.3.0-SNAPSHOT ->
```

then you will need to update your Maven `settings.xml` to allow snapshots. Your Maven `settings.xml` file is located in the `.m2` directory of your home directory and will need to contain configuration like:

```
<settings>
  <profiles>
    <profile>
      <id>allow-snapshots</id>
      <activation>
        <activeByDefault>true</activeByDefault>
      </activation>
      <repositories>
        <repository>
          <id>snapshots-repo</id>
          <url>https://oss.sonatype.org/content/repositories/snapshots</url>
          <snapshots>
            <enabled>true</enabled>
          </snapshots>
        </repository>
      </repositories>
    </profile>
  </profiles>
</settings>
```

After updating your `settings.xml` then you should be able to successfully run a `mvn clean test -U` on this module. (The `-U` option will forces a check for updated releases and snapshots on remote repositories.)
