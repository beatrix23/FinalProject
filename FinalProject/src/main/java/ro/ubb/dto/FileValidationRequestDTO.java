package ro.ubb.dto;

import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import java.util.Objects;

public class FileValidationRequestDTO {

    @NotNull
    protected MultipartFile file;

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }

    @Override
    public int hashCode() {
        return Objects.hash(file);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        FileValidationRequestDTO other = (FileValidationRequestDTO) obj;
        return Objects.equals(file, other.file);
    }

    @Override
    public String toString() {
        return "FileValidationRequestDTO [file=" + file + "]";
    }

}
