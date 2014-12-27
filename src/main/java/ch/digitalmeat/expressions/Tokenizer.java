package ch.digitalmeat.expressions;

import java.util.List;

public interface Tokenizer {
	public List<Token> tokenize(Context context, String expressionText);
}
