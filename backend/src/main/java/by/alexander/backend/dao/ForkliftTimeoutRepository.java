package by.alexander.backend.dao;

import by.alexander.backend.entity.ForkliftTimeout;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ForkliftTimeoutRepository extends JpaRepository<ForkliftTimeout, Long> {

    List<ForkliftTimeout> findAllByForkliftId(Long forkliftId, Sort sort);

    boolean existsByForkliftIdAndSolutionDateIsNotNull(Long forkliftId);
}
