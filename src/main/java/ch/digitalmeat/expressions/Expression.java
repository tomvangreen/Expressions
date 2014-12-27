package ch.digitalmeat.expressions;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

/**
 * An Expression is a simple mathematicel expression converted to a reverse
 * polish notation stack, which can be evaluated easily at runtime.
 * 
 * @author atombrot
 *
 */
public class Expression {
	private List<Token> tokens;
	private final List<Double> arguments = new ArrayList<Double>();
	// TODO: This might probably work without pool only using a double stack...
	private final Stack<Double> stack = new Stack<Double>();

	public Expression() {
	}

	public Expression(List<Token> tokens) {
		this.tokens = tokens;
	}

	public void setTokens(List<Token> tokens) {
		this.tokens = tokens;
	}

	public double evaluate(Context context) {
		stack.clear();
		arguments.clear();
		for (int index = 0; index < tokens.size(); index++) {
			Token token = tokens.get(index);
			TokenType type = token.getType();
			if (type.equals(TokenType.Number)) {
				stack.push(token.value());
			} else if (type.equals(TokenType.Operator)) {
				Operator operator = token.asOperatorToken().getOperator();
				loadArguments(2, true);
				stack.push(operator.operate(arguments.get(0), arguments.get(1)));
			} else if (type.equals(TokenType.Function)) {
				Function function = token.asFunctionToken().getFunction();
				loadArguments(function.parameters, true);
				stack.push(function.operate(arguments));
			}
		}
		loadArguments(1);
		return arguments.get(0);
	}

	public List<Double> loadArguments(int count) {
		return loadArguments(count, true);
	}

	public List<Double> loadArguments(int count, boolean reverse) {
		arguments.clear();
		while (count-- > 0) {
			arguments.add(stack.pop());
		}
		if (reverse) {
			Collections.reverse(arguments);
		}
		return arguments;
	}

	public float evaluateFloat(Context context) {
		return (float) evaluate(context);
	}

	public int evaluateInt(Context context) {
		return (int) evaluate(context);
	}

	private final StringBuilder builder = new StringBuilder();

	@Override
	public String toString() {
		for (Token token : tokens) {
			if (builder.length() > 0) {
				builder.append(" ");
			}
			builder.append(token);
		}
		String result = builder.toString();
		builder.setLength(0);
		return result;
	}

}
