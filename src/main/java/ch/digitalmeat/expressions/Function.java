package ch.digitalmeat.expressions;

import java.util.List;

public abstract class Function {
	public final String name;
	public final int parameters;

	public Function(String name, int parameters) {
		this.name = name;
		this.parameters = parameters;
	}

	public abstract double operate(List<Double> params);

	public static class Min extends Function {
		public Min() {
			super("min", 2);
		}

		@Override
		public double operate(List<Double> params) {
			return Math.min(params.get(0), params.get(1));
		}
	}

	public static class Max extends Function {
		public Max() {
			super("max", 2);
		}

		@Override
		public double operate(List<Double> params) {
			return Math.max(params.get(0), params.get(1));
		}
	}

	public static class Floor extends Function {
		public Floor() {
			super("floor", 1);
		}

		@Override
		public double operate(List<Double> params) {
			return Math.floor(params.get(0));
		}
	}

	public static class Ceiling extends Function {
		public Ceiling() {
			super("ceil", 1);
		}

		@Override
		public double operate(List<Double> params) {
			return Math.ceil(params.get(0));
		}
	}

	public static class Abs extends Function {
		public Abs() {
			super("abs", 1);
		}

		@Override
		public double operate(List<Double> params) {
			return Math.abs(params.get(0));
		}
	}

}
