package ch.digitalmeat.expressions;

import java.util.ArrayList;
import java.util.List;

import ch.digitalmeat.expressions.Token.ClosingParantheseToken;
import ch.digitalmeat.expressions.Token.FunctionArgumentDelimiterToken;
import ch.digitalmeat.expressions.Token.FunctionToken;
import ch.digitalmeat.expressions.Token.NumberToken;
import ch.digitalmeat.expressions.Token.OpeningParantheseToken;
import ch.digitalmeat.expressions.Token.OperatorToken;

public class BasicTokenizer implements Tokenizer {
	private int index;
	private Token lastToken;
	private String[] chars;
	private Context context;

	public final static String NUMBERS = "0123456789";
	public final static String NUMBER_DELIMITER = ".";
	public final static String STRING_QUOTE = "'";
	public final static String PARANTHESE_OPEN = "(";
	public final static String PARANTHESE_CLOSE = ")";
	public final static String FUNCTION_PREFIX = "$";
	public final static String FUNCTION_ARGUMENT_DELIMITER = ",";

	@Override
	public List<Token> tokenize(Context context, String expressionText) {
		this.context = context;
		chars = expressionText.split("");
		index = 0;
		// Gdx.app.log("BasicTokenizer", "Input: " + expressionText);
		List<Token> tokens = new ArrayList<Token>();

		while (index < chars.length) {
			Token token = readToken();
			if (token != null) {
				tokens.add(token);
				lastToken = token;
			}
			index++;
		}

		lastToken = null;
		chars = null;
		this.context = null;
		return tokens;
	}

	private Token readToken() {
		String chr = get();

		if (PARANTHESE_OPEN.equals(chr)) {
			return new OpeningParantheseToken();
		}
		if (PARANTHESE_CLOSE.equals(chr)) {
			return new ClosingParantheseToken();
		}
		if (NUMBERS.contains(chr)) {
			return readNumber();
		}
		if (FUNCTION_ARGUMENT_DELIMITER.equals(chr)) {
			return new FunctionArgumentDelimiterToken();
		}
		if (Character.isAlphabetic(chr.charAt(0))) {
			return readFunction();
		}
		Operator operator = context.operators.get(chr);
		if (operator != null) {
			return new OperatorToken(operator);
		}
		if (!" ".equals(chr)) {
			// Gdx.app.log("BasicTokenizer", "Not recognized string token: " +
			// chr);
		}
		return null;
	}

	private StringBuilder builder = new StringBuilder();

	private Token readNumber() {
		boolean hadDelimiter = false;
		boolean run = true;
		while (run && index < chars.length) {
			String chr = get();
			boolean isNumber = NUMBERS.contains(chr);
			boolean isDelimiter = NUMBER_DELIMITER.equals(chr);

			if (isNumber || isDelimiter) {
				if (isDelimiter) {
					if (hadDelimiter) {
						// TODO: Should this throw?
						// Gdx.app.log("BasicTokenizer",
						// "Already found a delimiter. Ignoring second one.");
					} else {
						hadDelimiter = true;
						builder.append(chr);
					}
				} else {
					builder.append(chr);
				}
				index++;
			} else {
				run = false;
			}
		}

		index--;
		String text = builder.toString();
		builder.setLength(0);
		return new NumberToken(Double.parseDouble(text));
	}

	private Token readFunction() {
		int startIndex = index;
		// index++;
		while (index < chars.length) {
			String chr = get();
			if (PARANTHESE_OPEN.equals(chr)) {
				break;
			}
			builder.append(chr);
			index++;
		}

		String text = builder.toString();
		builder.setLength(0);
		return new FunctionToken(context.functions.get(text));
	}

	private String get() {
		return chars[index];
	}
}
