package net.garmine.parser.html.tokenizer.tokens;

public class HtmlDoctypeToken extends HtmlToken {
	private final String NAME; 
	private final String PUBLIC;
	private final String SYSTEM;
	private final boolean QUIRKS;
	
	public HtmlDoctypeToken(String name, String publik, String system, boolean forceQuirks){
		NAME = name;
		PUBLIC = publik;
		SYSTEM = system;
		QUIRKS = forceQuirks;
	}
	
	@Override
	public String getDocName(){
		return NAME;
	}

	@Override
	public String getDocPublic(){
		return PUBLIC;
	}

	@Override
	public String getDocSystem(){
		return SYSTEM;
	}

	@Override
	public boolean doForceQuirks(){
		return QUIRKS;
	}
	
	@Override
	public HtmlTokenType getType() {
		return HtmlTokenType.DOCTYPE;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((NAME == null) ? 0 : NAME.hashCode());
		result = prime * result + ((PUBLIC == null) ? 0 : PUBLIC.hashCode());
		result = prime * result + (QUIRKS ? 1231 : 1237);
		result = prime * result + ((SYSTEM == null) ? 0 : SYSTEM.hashCode());
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
		HtmlDoctypeToken other = (HtmlDoctypeToken) obj;
		if (QUIRKS != other.QUIRKS)
			return false;
		if (NAME == null) {
			if (other.NAME != null)
				return false;
		} else if (!NAME.equals(other.NAME))
			return false;
		if (PUBLIC == null) {
			if (other.PUBLIC != null)
				return false;
		} else if (!PUBLIC.equals(other.PUBLIC))
			return false;
		if (SYSTEM == null) {
			if (other.SYSTEM != null)
				return false;
		} else if (!SYSTEM.equals(other.SYSTEM))
			return false;
		return true;
	}
}
