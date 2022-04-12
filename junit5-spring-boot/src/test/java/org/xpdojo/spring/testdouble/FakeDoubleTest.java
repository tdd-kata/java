package org.xpdojo.spring.testdouble;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.xpdojo.spring.testdouble.fixture.flight.Airport;
import org.xpdojo.spring.testdouble.fixture.flight.FakeInMemoryDatabase;
import org.xpdojo.spring.testdouble.fixture.flight.FlightManagementFacadeImpl;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

class FakeDoubleTest {

    /**
     * xUnit Test Pattern (번역서 p232)
     * <p>
     * Fake Object는 테스트에 의해 제어되지 않고 관찰되지도 않는다는 점에서
     * Test Stub이나 Mock Object와는 다르다.
     * Fake Object는 간접 입력/출력을 검증하기 위해 사용하는 것이 아니다.
     * 테스트에서 실제 DOC의 기능을 대체해야 할 때 사용한다.
     * <p>
     * Fake Object를 쓰는 가장 일반적인 이유는 실제 DOC가 아직 구현되지 않았거나,
     * 너무 느리거나, 테스트 환경에서 쓸 수 없기 때문이다.
     */
    @Test
    @DisplayName("가짜 데이터베이스는 메모리에서 해시 테이블만 사용한다")
    void test_fake_object() {
        FlightManagementFacadeImpl facade = new FlightManagementFacadeImpl();
        FakeInMemoryDatabase dao = new FakeInMemoryDatabase();
        facade.setDao(dao);

        facade.createAirport("SFO", "San Francisco", "San Jose");
        facade.createAirport("LAX", "Los Angeles", "Pasadena");

        Airport sfoAirport = facade.getAirport(new BigDecimal(1));
        assertThat(sfoAirport.getId()).isEqualTo(new BigDecimal(1));

        Airport laxAirport = facade.getAirport(new BigDecimal(2));
        assertThat(laxAirport.getId()).isEqualTo(new BigDecimal(2));
        assertThat(laxAirport.getCode()).isEqualTo("LAX");
    }
}
