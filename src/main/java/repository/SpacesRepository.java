package repository;

import models.Spaces;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpacesRepository extends JpaRepository<Spaces, Integer> {
}
