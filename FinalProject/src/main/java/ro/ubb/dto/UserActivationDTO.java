package ro.ubb.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.util.Objects;

public class UserActivationDTO {

    @Email
    @NotNull
    private String email;

    @NotNull
    private String code;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public int hashCode() {
        return Objects.hash(code, email);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        UserActivationDTO other = (UserActivationDTO) obj;
        return Objects.equals(code, other.code) && Objects.equals(email, other.email);
    }

    @Override
    public String toString() {
        return "UserDTO [username=" + email + ", code=" + code + "]";
    }

}
