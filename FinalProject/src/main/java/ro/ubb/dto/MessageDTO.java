package ro.ubb.dto;

import java.util.Objects;

public class MessageDTO {

    private String message;

    private String location;

    public MessageDTO(String message, String location) {
        super();
        this.message = message;
        this.location = location;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    @Override
    public int hashCode() {
        return Objects.hash(location, message);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        MessageDTO other = (MessageDTO) obj;
        return Objects.equals(location, other.location) && Objects.equals(message, other.message);
    }

    @Override
    public String toString() {
        return "MessageDTO [message=" + message + ", location=" + location + "]";
    }

}
