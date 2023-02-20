package org.xpdojo.quartz.data;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BookingRepository extends JpaRepository<Booking, String> {

    Optional<List<Booking>> findByExportDeclarationNumberNotNull();

}
