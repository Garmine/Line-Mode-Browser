package net.garmine.parser.html.tokenizer.tokens;

public class HtmlCommentToken extends HtmlToken {
	private final String COMMENT;
	
	public HtmlCommentToken(String comment){
		COMMENT = comment;
	}
	
	@Override
	public String getComment(){
		return COMMENT;
	}
	
	@Override
	public HtmlTokenType getType() {
		return HtmlTokenType.COMMENT;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((COMMENT == null) ? 0 : COMMENT.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		HtmlCommentToken other = (HtmlCommentToken) obj;
		if (COMMENT == null) {
			if (other.COMMENT != null)
				return false;
		} else if (!COMMENT.equals(other.COMMENT))
			return false;
		return true;
	}
}
