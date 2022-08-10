package ro.ubb.model;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "validation")
public class Validation {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column
    private Integer id;

    @JoinColumn(name = "id_user", nullable = false)
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private User user;

    @Column(length = 512)
    private String href;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "validation")
    private List<ValidationMessage> validationMessages;

    @Column
    private LocalDateTime dateCreated;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public List<ValidationMessage> getValidationMessages() {
        return validationMessages;
    }

    public void setValidationMessages(List<ValidationMessage> validationMessages) {
        this.validationMessages = validationMessages;
    }

    public LocalDateTime getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(LocalDateTime dateCreated) {
        this.dateCreated = dateCreated;
    }

    @Override
    public int hashCode() {
        return Objects.hash(dateCreated, href, id);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Validation other = (Validation) obj;
        return Objects.equals(dateCreated, other.dateCreated) && Objects.equals(href, other.href) && Objects.equals(id, other.id);
    }

    @Override
    public String toString() {
        return "Validation [id=" + id + ", user=" + user.getId() + ", href=" + href + ", validationMessages=" + validationMessages + ", dateCreated="
                + dateCreated + "]";
    }

}
