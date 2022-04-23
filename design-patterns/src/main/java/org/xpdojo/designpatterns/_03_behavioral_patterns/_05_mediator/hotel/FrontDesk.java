package org.xpdojo.designpatterns._03_behavioral_patterns._05_mediator.hotel;

import org.xpdojo.designpatterns._03_behavioral_patterns._05_mediator.Guest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;

/**
 * Mediator
 *
 * @see org.springframework.web.servlet.DispatcherServlet#doDispatch(HttpServletRequest, HttpServletResponse)
 */
public class FrontDesk {

    private final CleaningService cleaningService = new CleaningService();
    private final Restaurant restaurant = new Restaurant();

    public String getTowers(Guest guest, int numberOfTowers) {
        String roomNumber = getRoomNumberFor(guest.getId());
        return cleaningService.getTowers(roomNumber, numberOfTowers);
    }

    public String getRoomNumberFor(Integer guestId) {
        return guestId.toString();
    }

    public void dinner(Guest guest, LocalDateTime dateTime) {
        restaurant.dinner(guest.getId(), dateTime);
    }
}
