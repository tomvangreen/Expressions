package ch.digitalmeat.expressions;

public abstract class Operator {
	public final String symbol;
	public final int precedence;
	public final boolean leftAssociative;

	public Operator(String symbol, int precedence) {
		this(symbol, precedence, true);
	}

	public Operator(String symbol, int precedence, boolean leftAssociative) {
		this.symbol = symbol;
		this.precedence = precedence;
		this.leftAssociative = leftAssociative;
	}

	public abstract double operate(double a, double b);

	public static class Addition extends Operator {

		public Addition() {
			super("+", 2);
		}

		@Override
		public double operate(double a, double b) {
			return a + b;
		}

	}

	public static class Subtraction extends Operator {

		public Subtraction() {
			super("-", 2);
		}

		@Override
		public double operate(double a, double b) {
			return a - b;
		}

	}

	public static class Multiplication extends Operator {

		public Multiplication() {
			super("*", 3);
		}

		@Override
		public double operate(double a, double b) {
			return a * b;
		}

	}

	public static class Division extends Operator {

		public Division() {
			super("/", 3);
		}

		@Override
		public double operate(double a, double b) {
			return a / b;
		}

	}

	public static class Pow extends Operator {

		public Pow() {
			super("^", 4, false);
		}

		@Override
		public double operate(double a, double b) {
			return Math.pow(a, b);
		}

	}

	public static class Mod extends Operator {

		public Mod() {
			super("%", 3);
		}

		@Override
		public double operate(double a, double b) {
			return a % b;
		}

	}
}
