package hfoods.demo.repositories;

import hfoods.demo.entities.Information;
import hfoods.demo.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InformationRepository extends JpaRepository<Information, Long> {

    Page<Information> findByUser(User user, Pageable pageable);
}
