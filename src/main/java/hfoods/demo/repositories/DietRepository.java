package hfoods.demo.repositories;

import hfoods.demo.entities.Diet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DietRepository extends JpaRepository<Diet, Long> {
}
