package ch.digitalmeat.expressions;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class ExpressionBuilder {
	public final Tokenizer tokenizer;
	public final Context context = new Context();
	private final Stack<Token> stack = new Stack<Token>();

	public ExpressionBuilder(Tokenizer tokenizer) {
		this.tokenizer = tokenizer;
	}

	public void registerOperator(Operator operator) {
		context.operators.put(operator.symbol, operator);
	}

	public void registerFunction(Function function) {
		context.functions.put(function.name, function);
	}

	public void registerDefaults() {
		// TODO: Doesn't feel right having those in here... Find an appropriate
		// place for registering the default operations...
		registerOperator(new Operator.Addition());
		registerOperator(new Operator.Subtraction());
		registerOperator(new Operator.Multiplication());
		registerOperator(new Operator.Division());
		registerOperator(new Operator.Pow());
		registerOperator(new Operator.Mod());
		registerFunction(new Function.Min());
		registerFunction(new Function.Max());
		registerFunction(new Function.Floor());
		registerFunction(new Function.Ceiling());
		registerFunction(new Function.Abs());
	}

	public Expression build(String expressionText) {
		List<Token> tokens = tokenizer.tokenize(context, expressionText);
		stack.clear();
		List<Token> queue = new ArrayList<Token>();

		// Converting to RPN notation using shunting yard algorithm

		while (tokens.size() > 0) {
			Token token = tokens.remove(0);
			TokenType type = token.getType();
			if (type.equals(TokenType.Number)) {
				queue.add(token);
			} else if (type.equals(TokenType.Function)) {
				stack.push(token);
			} else if (type.equals(TokenType.FunctionArgumentDelimiter)) {
				do {
					queue.add(stack.pop());
				} while (stack.peek().getType() != TokenType.OpeningParanthese);
				stack.pop();
			} else if (type.equals(TokenType.Operator)) {
				int precedence = token.precedence();
				boolean left = token.isLeftAssociative();
				Token peek = stack.isEmpty() ? null : stack.peek();
				int peekPrecedence = peek == null ? 0 : peek.precedence();
				while (peek != null && (left && precedence <= peekPrecedence) || (!left && precedence < peekPrecedence)) {
					queue.add(stack.pop());
					peek = stack.isEmpty() ? null : stack.peek();
					peekPrecedence = peek == null ? 0 : peek.precedence();
				}
				stack.push(token);
			} else if (type.equals(TokenType.OpeningParanthese)) {
				stack.push(token);
			} else if (type.equals(TokenType.ClosingParanthese)) {
				while (!stack.isEmpty() && !stack.peek().getType().equals(TokenType.OpeningParanthese)) {
					queue.add(stack.pop());
				}
				if (!stack.isEmpty() && stack.peek().getType().equals(TokenType.Function)) {
					queue.add(stack.pop());
				}
			}
		}
		while (!stack.isEmpty()) {
			Token token = stack.pop();
			TokenType type = token.getType();
			if (type.equals(TokenType.OpeningParanthese) || type.equals(TokenType.ClosingParanthese)) {
				// Gdx.app.log("ExpressionBuilder",
				// "At this point no parantheses should show up");
			} else {
				queue.add(token);
			}
		}

		Expression expression = new Expression(queue);
		return expression;
	}
}
