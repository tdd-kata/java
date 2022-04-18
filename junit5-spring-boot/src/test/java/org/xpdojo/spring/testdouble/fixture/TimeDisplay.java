package org.xpdojo.spring.testdouble.fixture;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

/**
 * xUnit Test Pattern
 */
public class TimeDisplay {

    private TimeProvider timeProvider;

    public void setTimeProvider(TimeProvider timeProvider) {
        this.timeProvider = timeProvider;
    }

    public String getCurrentTimeAsHtmlFragment() {
        LocalDateTime time;

        StringBuilder sb = new StringBuilder();

        try {
            time = timeProvider.getTime();
            sb.append("<span class=\"tinyBoldText\">");

            if ((time.getHour() == 0) && (time.getMinute() <= 1)) {
                sb.append("Midnight");
            } else if ((time.getHour() == 12) && (time.getMinute() == 0)) {
                sb.append("Noon");
            } else {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("h:mm a").withLocale(Locale.KOREA);
                sb.append(time.format(formatter));
            }

            sb.append("</span>");
        } catch (Exception e) {
            sb.append("<span class=\"error\">Invalid Time</span>");
        }

        return sb.toString();
    }

}
