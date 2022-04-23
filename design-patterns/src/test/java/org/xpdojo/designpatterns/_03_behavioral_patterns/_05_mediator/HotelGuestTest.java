package org.xpdojo.designpatterns._03_behavioral_patterns._05_mediator;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class HotelGuestTest {

    @Test
    void sut_hotel_guest() {
        Guest guest = new Guest(123);
        String towers = guest.getTowers(3);
        assertThat(towers).isEqualTo("provide 3 towers to 123");
    }

}
