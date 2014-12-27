Expressions
===========

Java library to evaluate simple mathematical expressions.

*Important:* If you want to build this library locally using gradle, you currently have to create a file gradle.properties in the root directory. You can copy and rename the existing gradle.properties.example file. Otherwise the gradle tasks will fail.

The library tokenizes an input string, converts it into reverse polish notation using the "Shunting Yard" algorithm and provides a way to be evaluated. You can define the context of the evaluation, which means that you can provde own functions and operators and are not limited to the existing ones. 

There is currently no documentation available. Check the [existing test](https://github.com/tomvangreen/Expressions/blob/master/src/test/java/ch/digitalmeat/expressions/test/BasicTokenizerExpressionBlackboxTests.java) on how to use it.