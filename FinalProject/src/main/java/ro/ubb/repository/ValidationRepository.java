package ro.ubb.repository;

import org.springframework.stereotype.Repository;
import ro.ubb.model.User;
import ro.ubb.model.Validation;

import java.util.Set;

@Repository
public interface ValidationRepository extends BaseRepository<Validation, Integer> {

    Set<Validation> findByHrefAndUserOrderByDateCreatedDesc(String href, User user);

}
