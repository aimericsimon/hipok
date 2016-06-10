package com.happy.hipok.repository;

import com.happy.hipok.domain.Device;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data JPA repository for the Device entity.
 */
public interface DeviceRepository extends JpaRepository<Device,Long> {

    @Query("SELECT d FROM Device d WHERE d.deviceProfile.id = :profileId and d.token = :token  and d.type = :type")
    Optional<Device> findDevice(@Param("profileId") Long profileId, @Param("token") String token, @Param("type") String type);

    @Query("SELECT d FROM Device d WHERE d.deviceProfile.id = :profileId and d.enabled = true")
    List<Device> getProfileDevices(@Param("profileId") Long profileId);
}
