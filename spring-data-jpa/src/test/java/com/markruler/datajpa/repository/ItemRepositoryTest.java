package com.markruler.datajpa.repository;

import com.markruler.datajpa.entity.Item;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class ItemRepositoryTest {

    @Autowired
    ItemRepository itemRepository;

    @Nested
    @DisplayName("save() 메서드는")
    class Describe_save {

        final Long validItemId = 1L;

        @Test
        @DisplayName("엔터티가 DB에 없을 경우 em.merge()를 사용하기 때문에 Persistable#isNew()를 재정의해서 사용한다")
        void persistable_isNew() {
            final Item item = new Item(validItemId);
            itemRepository.save(item);

            final Item findItem = itemRepository.findById(validItemId).orElse(null);
            assertThat(findItem).isNotNull();
            assertThat(findItem.getId()).isEqualTo(validItemId);
            assertThat(findItem.getCreatedAt()).isNotNull();
        }
    }
}
