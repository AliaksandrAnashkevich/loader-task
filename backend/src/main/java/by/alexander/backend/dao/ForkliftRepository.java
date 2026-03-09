package by.alexander.backend.dao;

import by.alexander.backend.entity.Forklift;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface ForkliftRepository extends JpaRepository<Forklift, Long>, JpaSpecificationExecutor<Forklift> {

}
