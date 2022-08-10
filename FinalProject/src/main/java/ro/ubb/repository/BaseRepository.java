package ro.ubb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;

@NoRepositoryBean
public interface BaseRepository<EN, ID extends Serializable> extends JpaRepository<EN, ID>, JpaSpecificationExecutor<EN> {

    EN findOneById(ID id);

}
