package ro.ubb.dto;

import java.util.List;
import java.util.Objects;

public class ValidationDTO {

    private String location;

    private List<MessageDTO> messages;

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public List<MessageDTO> getMessages() {
        return messages;
    }

    public void setMessages(List<MessageDTO> messages) {
        this.messages = messages;
    }

    @Override
    public int hashCode() {
        return Objects.hash(location, messages);
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
        return Objects.equals(location, other.location) && Objects.equals(messages, other.messages);
    }

    @Override
    public String toString() {
        return "ValidationDTO [location=" + location + ", messages=" + messages + "]";
    }

}
