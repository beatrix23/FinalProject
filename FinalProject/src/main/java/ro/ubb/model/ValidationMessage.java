package ro.ubb.model;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "validation_message")
public class ValidationMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column
    private Integer id;

    @JoinColumn(name = "id_validation", nullable = false)
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Validation validation;

    @Column(length = 16384)
    private String message;

    @Column(length = 512)
    private String location;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Validation getValidation() {
        return validation;
    }

    public void setValidation(Validation validation) {
        this.validation = validation;
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
        return Objects.hash(id, location, message);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        ValidationMessage other = (ValidationMessage) obj;
        return Objects.equals(id, other.id) && Objects.equals(location, other.location) && Objects.equals(message, other.message);
    }

    @Override
    public String toString() {
        return "ValidationMessage [id=" + id + ", validation=" + validation.getId() + ", message=" + message + ", location=" + location + "]";
    }

}
