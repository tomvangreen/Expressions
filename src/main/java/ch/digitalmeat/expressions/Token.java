package ch.digitalmeat.expressions;

public abstract class Token {
	public abstract TokenType getType();

	@Override
	public String toString() {
		return getType().toString();
	}

	public boolean isOperator() {
		return getType().equals(TokenType.Operator);
	}

	public OperatorToken asOperatorToken() {
		return null;
	}

	public FunctionToken asFunctionToken() {
		return null;
	}

	public int precedence() {
		if (isOperator()) {
			return asOperatorToken().getOperator().precedence;
		}
		return 0;
	}

	public boolean isLeftAssociative() {
		return true;
	}

	public double value() {
		return 0;
	}

	public NumberToken asNumber() {
		return null;
	}

	public static class NumberToken extends Token {

		private double value;
		public final boolean pooled;

		public NumberToken(boolean pooled) {
			this(0.0, true);
		}

		public NumberToken() {
			this(0.0, false);
		}

		public NumberToken(double value) {
			this(value, false);
		}

		public NumberToken(double value, boolean pooled) {
			this.value = value;
			this.pooled = pooled;
		}

		@Override
		public TokenType getType() {
			return TokenType.Number;
		}

		public double getValue() {
			return value;
		}

		public void setValue(double value) {
			this.value = value;
		}

		@Override
		public double value() {
			return value;
		}

		@Override
		public String toString() {
			return "" + value;
		}

		@Override
		public NumberToken asNumber() {
			return this;
		}

	}

	public static class OperatorToken extends Token {
		private Operator operator;

		public OperatorToken() {
			this(null);
		}

		public OperatorToken(Operator operator) {
			this.operator = operator;
		}

		@Override
		public TokenType getType() {
			return TokenType.Operator;
		}

		public Operator getOperator() {
			return operator;
		}

		public void setOperator(Operator operator) {
			this.operator = operator;
		}

		@Override
		public String toString() {
			return operator.symbol;
		}

		@Override
		public boolean isLeftAssociative() {
			return getOperator().leftAssociative;
		}

		@Override
		public OperatorToken asOperatorToken() {
			return this;
		}
	}

	public static class FunctionToken extends Token {
		private Function function;

		public FunctionToken() {
			this(null);
		}

		public FunctionToken(Function function) {
			this.function = function;
		}

		@Override
		public TokenType getType() {
			return TokenType.Function;
		}

		public Function getFunction() {
			return function;
		}

		public void setFunction(Function function) {
			this.function = function;
		}

		@Override
		public String toString() {
			return function.name;
		}

		public FunctionToken asFunctionToken() {
			return this;
		}

	}

	public static class OpeningParantheseToken extends Token {

		@Override
		public TokenType getType() {
			return TokenType.OpeningParanthese;
		}

		@Override
		public String toString() {
			return "(";
		}
	}

	public static class ClosingParantheseToken extends Token {

		@Override
		public TokenType getType() {
			return TokenType.ClosingParanthese;
		}

		@Override
		public String toString() {
			return ")";
		}

	}

	public static class FunctionArgumentDelimiterToken extends Token {

		@Override
		public TokenType getType() {
			return TokenType.FunctionArgumentDelimiter;
		}

		@Override
		public String toString() {
			return ",";
		}

	}

}
