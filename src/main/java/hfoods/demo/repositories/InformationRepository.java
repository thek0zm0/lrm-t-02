package hfoods.demo.repositories;

import hfoods.demo.entities.Information;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InformationRepository extends JpaRepository<Information, Long> {
}
