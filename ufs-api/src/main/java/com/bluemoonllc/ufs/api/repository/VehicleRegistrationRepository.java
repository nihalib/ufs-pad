package com.bluemoonllc.ufs.api.repository;

import com.bluemoonllc.ufs.api.dao.VehicleDao;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.Optional;

@Repository
public interface VehicleRegistrationRepository extends JpaRepository<VehicleDao, Long> {
    @Query("select v from VehicleDao v where v.ciamId = :ciamId")
    Page<VehicleDao> findVehicleDaoByCiamId(@Param("ciamId") BigInteger ciamId, Pageable pageable);

    @Query("select v from VehicleDao v where v.ciamId.ciamId = :ciamId and v.isTestData = :isTestData")
    Page<VehicleDao> findVehicleDaoByIsTestData(@Param("ciamId") BigInteger ciamId,
                                                @Param("isTestData") boolean isTestData, Pageable pageable);

    @Query("select v from VehicleDao v where v.vin = :vin and v.isTestData = :isTestData")
    Optional<VehicleDao> findByVinAndAndIsTestData(@Param("vin") String vin, @Param("isTestData") boolean isTestData);

    Optional<VehicleDao> findByVin(@Param("vin") String vin);
}
