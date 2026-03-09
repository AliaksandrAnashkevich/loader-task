package by.alexander.backend.dao;

import by.alexander.backend.entity.ForkliftTimeout;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ForkliftTimeoutRepository extends JpaRepository<ForkliftTimeout, Long> {

    boolean existsByForkliftIdAndSolutionDateIsNotNull(Long forkliftId);
}
