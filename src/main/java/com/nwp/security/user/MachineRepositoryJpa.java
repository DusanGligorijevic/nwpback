package com.nwp.security.user;

import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.nwp.security.user.Machine;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface MachineRepositoryJpa extends JpaRepository<Machine, Long> {
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    List<Machine> findAllByCreatedBy(User user);


    @Query(value = "SELECT * FROM machines WHERE (:name IS NULL OR column1 = :name) AND (:status IS NULL OR column2 = :status)" +
            "AND (:dateFrom IS NULL OR column2 = :dateFrom) AND (:dateTo IS NULL OR column2 = :dateTo)", nativeQuery = true)
    List<Machine> findByParams(@Param("name") String name, @Param("status") List<String> status, @Param("dateFrom") LocalDate dateFrom,
                               @Param("dateTo") LocalDate dateTo);



}
