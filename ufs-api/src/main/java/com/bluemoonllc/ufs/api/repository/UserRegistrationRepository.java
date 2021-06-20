package com.bluemoonllc.ufs.api.repository;

import com.bluemoonllc.ufs.api.dao.CiamUserDao;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.Optional;

@Repository
public interface UserRegistrationRepository extends JpaRepository<CiamUserDao, Long> {
    @Query("select c from CiamUserDao c where c.isTestData = :isTestData")
    Page<CiamUserDao> findCiamUserDaoByIsTestData(@Param("isTestData") boolean isTestData, Pageable pageable);

    Optional<CiamUserDao> findByCiamId(BigInteger ciamId);
}
