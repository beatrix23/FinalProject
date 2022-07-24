package ro.ubb.dto;

import java.util.List;
import java.util.Objects;

public class ValidationDTO {

	private String href;

	private List<MessageDTO> messages;

	public String getHref() {
		return href;
	}

	public void setHref(String href) {
		this.href = href;
	}

	public List<MessageDTO> getMessages() {
		return messages;
	}

	public void setMessages(List<MessageDTO> messages) {
		this.messages = messages;
	}

	@Override
	public int hashCode() {
		return Objects.hash(href, messages);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ValidationDTO other = (ValidationDTO) obj;
		return Objects.equals(href, other.href) && Objects.equals(messages, other.messages);
	}

	@Override
	public String toString() {
		return "ValidationDTO [href=" + href + ", messages=" + messages + "]";
	}

}
