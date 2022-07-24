package ro.ubb.repository;

import org.springframework.stereotype.Repository;

import ro.ubb.model.User;

@Repository
public interface UserRepository extends BaseRepository<User, Integer>{

	User findByUsername(String username);

}
