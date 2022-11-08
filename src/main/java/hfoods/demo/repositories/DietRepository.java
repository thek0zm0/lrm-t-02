package hfoods.demo.repositories;

import hfoods.demo.entities.Diet;
import hfoods.demo.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DietRepository extends JpaRepository<Diet, Long> {
    Page<Diet> findByUsers(User users, Pageable pageable);
}
