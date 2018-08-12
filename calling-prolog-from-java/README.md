# Calling Prolog from Java

## About

Provides an example of how you can interact with a Prolog inference engine from a Java application.

`src/main/java/com/example/ProjogExample.java` demonstrates how to:

1. Create a new instance of `org.projog.api.Projog`
2. Load rules and facts, written in Prolog, from a file (`src/main/resources/test.pl`).
3. Run queries, written in Prolog, which use the rules and facts.

## See Also

For examples of how `org.projog.api.Projog` is used within the Projog project please see:

* [`ProjogConsole.java`](https://github.com/s-webber/projog/blob/master/src/core/org/projog/tools/ProjogConsole.java)
* [`ProjogTestRunner.java`](https://github.com/s-webber/projog/blob/master/src/build-utils/org/projog/test/ProjogTestRunner.java)
