# Calling Java from Prolog

## About

Provides examples of how to extend the functionality of Prolog by writing new built-in predicates and arithmetic operations using Java.

`src/main/java/com/example/SingletonPredicateExample.java` demonstrates how to add a built-in predicate.

`src/main/java/com/example/ArithmeticOperatorExample.java` demonstrates how to add a new arithmetic operation.

`src/test/java/com/example/PrologTest.java` demonstrates how the Projog test framework (included as a dependency in the `pom.xml` file) can be used to unit test the new functionality. `PrologTest` contains a method that:

1. Extracts tests that have been written using Prolog and are contained in comments of the Java source files.
2. Runs the extracted tests and compares the actual results against the expected results.

## See Also

To view how the built-in predicates and arithmetic functions provided by Projog are implemented please see:

* [Projog's `org.projog.core.function` package](https://github.com/s-webber/projog/tree/master/src/core/org/projog/core/function)
