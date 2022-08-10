package ro.ubb.dto;

import javax.validation.constraints.NotNull;
import java.util.Objects;

public class TextValidationRequestDTO {

    @NotNull
    private String text;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public int hashCode() {
        return Objects.hash(text);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        TextValidationRequestDTO other = (TextValidationRequestDTO) obj;
        return Objects.equals(text, other.text);
    }

    @Override
    public String toString() {
        return "TextValidationRequestDTO [text=" + text + "]";
    }

}
