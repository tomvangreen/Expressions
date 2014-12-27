package ch.digitalmeat.expressions.test;

import org.junit.Assert;
import org.junit.Test;

import ch.digitalmeat.expressions.BasicTokenizer;
import ch.digitalmeat.expressions.Expression;
import ch.digitalmeat.expressions.ExpressionBuilder;

public class BasicTokenizerExpressionBlackboxTests {

	private ExpressionBuilder builder = new ExpressionBuilder(new BasicTokenizer());

	@Test
	public void test() {
		builder.registerDefaults();
		runExpression(0, "1 + 2 - 3");
		runExpression(6, "1 + 2 + 3");
		runExpression(7, "1 + 2 * 3");
		runExpression(9, "(1 + 2) * 3");
		runExpression(4, "2^2");
		runExpression(8, "2^3");
		runExpression(9, "3^2");
		runExpression(16, "4^2");
		runExpression((1 - 5) * (1 - 5), "(1-5)*(1-5)");
		runExpression(Math.pow(1 - 5, 2), "(1-5)^2");
		runExpression(4.0 % 2.0, "4%2");
		runExpression(Math.pow(4, Math.pow(2, 3)), "4^2^3");
		runExpression(Math.pow(1.0 - 5.0, Math.pow(2.0, 3.0)), "( 1 - 5 ) ^ 2 ^ 3");
	}

	private void runExpression(double expectedValue, String expressionText) {
		System.out.println("Testing expression: " + expressionText);
		Expression expression = builder.build(expressionText);
		System.out.println("     Generated RPN: " + expression.toString());
		double result = expression.evaluate(builder.context);
		System.out.println("                  Expected: " + expectedValue);
		System.out.println("                     Found: " + result);
		Assert.assertEquals(expectedValue, result, 0);
	}
}
