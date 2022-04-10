package org.xpdojo.spring.testdouble;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.invocation.MockHandler;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.mock.MockCreationSettings;
import org.springframework.util.ObjectUtils;
import org.xpdojo.spring.testdouble.fixture.ActionCode;
import org.xpdojo.spring.testdouble.fixture.AuditLogConfigurableMock;
import org.xpdojo.spring.testdouble.fixture.FlightDto;
import org.xpdojo.spring.testdouble.fixture.FlightManagementFacadeImpl;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class MockDoubleTest {

    @Test
    @DisplayName("Mockito를 사용해서 Mocking test")
    void test_mockito() {
        MockitoDouble mockitoDouble = mock(MockitoDouble.class);

        given(mockitoDouble.greeting(eq("markruler"))).willReturn("Hello markruler");

        final String actual = mockitoDouble.greeting("markruler");
        assertThat(actual).isEqualTo("Hello markruler");

        verify(mockitoDouble).greeting(anyString());
    }

    /**
     * xUnit Test Pattern
     */
    @Nested
    @DisplayName("Mock Object는 내부에서 대부분의 결과를 검증하고 하나라도 실패하면 테스트를 실패시킨다")
    class TestMock {
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
            AuditLogConfigurableMock auditLogConfigurableMock = new AuditLogConfigurableMock();
            auditLogConfigurableMock.setExpectedLogMessage(
                    LocalDate.now(),
                    "test",
                    ActionCode.REMOVE_FLIGHT_ACTION_CODE,
                    "removeFlight"
            );
            facade.setAuditLog(auditLogConfigurableMock);

            // 실행
            facade.removeFlight(flightDto.getFlightNumber());

            // 상태 검증
            assertThat(facade.flightExists(flightDto.getFlightNumber())).isFalse();

            // Mock Object 호출 검증
            auditLogConfigurableMock.setExpectedNumberOfCalls(1);
            auditLogConfigurableMock.verify();
        }
    }

    /**
     * Mockito는 ByteBuddy를 사용하지만 여기서는 최대한 단순화시켰다.
     *
     * @see org.mockito.internal.creation.proxy.ProxyMockMaker#createMock(MockCreationSettings, MockHandler)
     */
    @Test
    @DisplayName("동적 프록시를 사용해서 Mocking test")
    void test_mock_object() {
        InvocationHandler invocationHandler = new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) {
                if (method.getName().equals("name")
                        && method.getParameterTypes().length == 1
                        && method.getParameterTypes()[0] == String.class
                        && !ObjectUtils.isEmpty(args[0])) {
                    StringBuilder sb = new StringBuilder();
                    sb.append("Hello ");
                    sb.append(args[0]);
                    return sb.toString();
                } else {
                    return null;
                }
            }
        };

        MockDouble mock =
                (MockDouble) Proxy.newProxyInstance(
                        getClass().getClassLoader(),
                        new Class[]{MockDouble.class},
                        invocationHandler
                );

        assertThat(mock.name("makruler")).isEqualTo("Hello makruler");

        assertThat(mock.name(null)).isBlank();
        assertThat(mock.name("")).isBlank();
    }
}
