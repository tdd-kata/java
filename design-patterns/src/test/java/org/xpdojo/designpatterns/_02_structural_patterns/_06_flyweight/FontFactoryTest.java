package org.xpdojo.designpatterns._02_structural_patterns._06_flyweight;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class FontFactoryTest {

    @Test
    @DisplayName("자주 사용하는 데이터를 Caching 하면 메모리 사용을 줄일 수 있다")
    void sut_cache_flyweight() {
        FontFactory fontFactory = new FontFactory();
        Character c1 = new Character('H', "white", fontFactory.getFont("nanum:12"));
        Character c2 = new Character('i', "white", fontFactory.getFont("nanum:12"));

        assertThat(c1.getFont()).isEqualTo(c2.getFont());
        assertThat(c1.getFont()).isSameAs(c2.getFont());
    }
}
