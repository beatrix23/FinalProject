package ro.ubb.dto;

import javax.validation.constraints.NotNull;
import java.util.Objects;

public class ValidationRequestDTO {

    @NotNull
    private String location;

    private boolean entireWebsite;

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public boolean isEntireWebsite() {
        return entireWebsite;
    }

    public void setEntireWebsite(boolean entireWebsite) {
        this.entireWebsite = entireWebsite;
    }

    @Override
    public int hashCode() {
        return Objects.hash(entireWebsite, location);
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
        return entireWebsite == other.entireWebsite && Objects.equals(location, other.location);
    }

    @Override
    public String toString() {
        return "ValidationRequestDTO [location=" + location + ", entireWebsite=" + entireWebsite + "]";
    }

}
