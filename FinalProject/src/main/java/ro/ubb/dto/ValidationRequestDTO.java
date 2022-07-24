package ro.ubb.dto;

import java.util.Objects;

public class ValidationRequestDTO {

	private String href;
	
	private boolean entireWebsite;

	public String getHref() {
		return href;
	}

	public void setHref(String href) {
		this.href = href;
	}

	public boolean isEntireWebsite() {
		return entireWebsite;
	}

	public void setEntireWebsite(boolean entireWebsite) {
		this.entireWebsite = entireWebsite;
	}

	@Override
	public int hashCode() {
		return Objects.hash(entireWebsite, href);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ValidationRequestDTO other = (ValidationRequestDTO) obj;
		return entireWebsite == other.entireWebsite && Objects.equals(href, other.href);
	}

	@Override
	public String toString() {
		return "ValidationRequestDTO [href=" + href + ", entireWebsite=" + entireWebsite + "]";
	}
	
}
