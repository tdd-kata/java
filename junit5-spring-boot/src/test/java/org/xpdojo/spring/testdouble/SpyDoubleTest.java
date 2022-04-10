package org.xpdojo.spring.testdouble;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.xpdojo.spring.testdouble.fixture.ActionCode;
import org.xpdojo.spring.testdouble.fixture.AuditLogSpy;
import org.xpdojo.spring.testdouble.fixture.FlightDto;
import org.xpdojo.spring.testdouble.fixture.FlightManagementFacadeImpl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class SpyDoubleTest {

    @Test
    @DisplayName("Mockito를 사용해서 Mock test")
    void test_mockito_mock() {
        List<Integer> mockList = Mockito.mock(ArrayList.class);

        mockList.add(100);
        assertThat(mockList).hasSize(0);

        given(mockList.size()).willReturn(5);
        assertThat(mockList).hasSize(5);
    }

    @Test
    @DisplayName("Mockito를 사용해서 Spy test")
    void test_mockito_spy() {
        List<Integer> list = new ArrayList<>();
        List<Integer> mockList = Mockito.spy(list);

        // Spy는 Stub의 기능에 더해 메소드를 호출한 내역을 기록할 수 있다.
        // Mock은 이와 달리 실제 메소드를 호출하지 않는다.
        mockList.add(100);
        assertThat(mockList).hasSize(1);

        given(mockList.size()).willReturn(5);
        assertThat(mockList).hasSize(5);
    }

    /**
     * xUnit Test Pattern
     */
    @Nested
    @DisplayName("Test Spy는 메서드를 호출한 내역을 기록할 수 있다")
    class TestSpy {
        FlightManagementFacadeImpl facade = new FlightManagementFacadeImpl();

        private FlightDto createAnUnregisteredFlight() {
            FlightDto flight = new FlightDto(1, "LAX", "SFO");
            facade.registerFlight(flight);
            return flight;
        }

        @Test
        void test_remove_flight_logging_recording_test_stub() {
            // setup fixture
            FlightDto flightDto = createAnUnregisteredFlight();

            // setup test double
            AuditLogSpy auditLogSpy = new AuditLogSpy();
            facade.setAuditLog(auditLogSpy);

            // 실행
            facade.removeFlight(flightDto.getFlightNumber());

            // 상태 검증
            assertThat(facade.flightExists(flightDto.getFlightNumber())).isFalse();

            // Test Spy의 검색 인터페이스로 간접 출력 검증
            assertThat(auditLogSpy.getNumberOfCalls()).isEqualTo(1);
            assertThat(auditLogSpy.getActionCode()).isEqualTo(ActionCode.REMOVE_FLIGHT_ACTION_CODE);
            assertThat(auditLogSpy.getDate()).isEqualTo(LocalDate.now());
            assertThat(auditLogSpy.getUser()).isEqualTo("test");
            assertThat(auditLogSpy.getDetail()).isEqualTo("removeFlight");
        }


    }
}
