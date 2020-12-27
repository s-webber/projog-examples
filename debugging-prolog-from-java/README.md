# Debugging Prolog from Java

## About

This module provides an example of how you can obtain debug information about [Prolog](https://en.wikipedia.org/wiki/Prolog) queries that are called from Java programs using [Projog](http://projog.org "Prolog interpreter for Java").

`src/main/java/com/example/TraceExample.java` performs queries on the facts and rules contained in `src/main/resources/TraceExample.pl` and produces output that matches what is contained in `src/test/resources/TraceExampleTestExpectedOutput.txt`.
