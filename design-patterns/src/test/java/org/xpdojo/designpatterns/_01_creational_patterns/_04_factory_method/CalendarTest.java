package org.xpdojo.designpatterns._01_creational_patterns._04_factory_method;

import org.junit.jupiter.api.Test;

import java.util.Calendar;
import java.util.Locale;

import static org.assertj.core.api.Assertions.assertThat;

class CalendarTest {

    @Test
    void sut_gregory_calendar() {
        Calendar defaultCalendar = Calendar.getInstance();
        Calendar koreanCalendar = Calendar.getInstance(Locale.KOREA);

        assertThat(defaultCalendar.getCalendarType()).isEqualTo("gregory");
        assertThat(koreanCalendar.getCalendarType()).isEqualTo("gregory");
    }

    @Test
    void sut_buddhist_calendar() {
        Calendar taiwanCalendar = Calendar.getInstance(Locale.forLanguageTag("th-TH-x-lvariant-TH"));

        assertThat(taiwanCalendar.getCalendarType()).isEqualTo("buddhist");
    }

    @Test
    void sut_japanese_calendar() {
        Calendar japaneseCalendar = Calendar.getInstance(Locale.forLanguageTag("ja-JP-x-lvariant-JP"));

        assertThat(japaneseCalendar.getCalendarType()).isEqualTo("japanese");
    }
}
