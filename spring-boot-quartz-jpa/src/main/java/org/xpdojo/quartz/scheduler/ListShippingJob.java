package org.xpdojo.quartz.scheduler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionContext;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;
import org.xpdojo.quartz.data.Booking;
import org.xpdojo.quartz.data.BookingRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class ListShippingJob extends QuartzJobBean {

    private final BookingRepository bookingRepository;

    @Override
    protected void executeInternal(JobExecutionContext context) {
        Booking bookingFixture = Booking.builder()
                .id(UUID.randomUUID().toString())
                .vin(UUID.randomUUID().toString())
                .shippingSchedule(UUID.randomUUID().toString())
                .exportDeclarationNumber(UUID.randomUUID().toString())
                .loadingDeadline("20230220")
                .build();

        bookingRepository.save(bookingFixture);

        List<Booking> bookings = bookingRepository.findByExportDeclarationNumberNotNull()
                .orElse(new ArrayList<>());

        long countHasExportDeclarationNumber = bookings
                .stream()
                .filter(booking -> booking.getExportDeclarationNumber() != null)
                .count();
        log.info("Count booking has Export Declaration Number: {}", countHasExportDeclarationNumber);

        long countHasShippingSchedule = bookings
                .stream()
                .filter(booking -> booking.getShippingSchedule() != null)
                .count();
        log.info("Count booking has Shipping Schedule: {}", countHasShippingSchedule);

        bookings
                .stream()
                .filter(booking -> booking.getShippingSchedule() != null)
                .forEach(booking -> log.info("Booking has Shipping Schedule: {}", booking));
    }

}
