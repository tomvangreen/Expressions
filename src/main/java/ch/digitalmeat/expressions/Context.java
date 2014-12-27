package ch.digitalmeat.expressions;

import java.util.HashMap;
import java.util.Map;

public class Context {
	public final Map<String, Operator> operators = new HashMap<String, Operator>();
	public final Map<String, Function> functions = new HashMap<String, Function>();
}
