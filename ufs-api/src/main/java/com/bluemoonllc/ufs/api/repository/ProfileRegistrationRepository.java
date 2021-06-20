package com.bluemoonllc.ufs.api.repository;

import com.bluemoonllc.ufs.api.dao.ProfileDao;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.Optional;

@Repository
public interface ProfileRegistrationRepository extends JpaRepository<ProfileDao, Long> {
    @Query("select p from ProfileDao p where p.isTestData = :isTestData")
    Page<ProfileDao> findProfileDaoByIsTestData(@Param("isTestData") boolean isTestData, Pageable pageable);

    @Query("select p from ProfileDao p where p.ciamId.ciamId = :ciamId")
    Optional<ProfileDao> findAllByCiamId(@Param("ciamId") BigInteger ciamId);

    @Query("select p from ProfileDao p where p.ciamId.ciamId = :ciamId and p.isTestData = :isTestData")
    Optional<ProfileDao> findProfileDaoByCiamIdAndIsTestData(@Param("ciamId") BigInteger ciamId,
                                                             @Param("isTestData") boolean isTestData);
}
