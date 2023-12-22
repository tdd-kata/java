package org.xpdojo.lettuce.redis;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.ToString;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.test.context.ActiveProfiles;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("standalone")
@SpringBootTest
class RedisStandaloneConfigTest {


    @Autowired
    RedisTemplate<String, String> redisTemplateString;

    @Autowired
    ObjectMapper objectMapper;

    /*
    {'Etc': {'C0060': {'category': 'Etc',
                       'code': 'C0060',
                       'count': 1,
                       'name': '4WD'},
             'C0100': {'category': 'Etc',
                       'code': 'C0100',
                       'count': 2,
                       'name': 'Euro5'},
             'C0240': {'category': 'Etc',
                       'code': 'C0240',
                       'count': 1,
                       'name': 'LPG'},
             'C0250': {'category': 'Etc',
                       'code': 'C0250',
                       'count': 2,
                       'name': 'Hybrid'},
             'C0370': {'category': 'Etc',
                       'code': 'C0370',
                       'count': 1,
                       'name': '9 Seats'},
             'C0550': {'category': 'Etc',
                       'code': 'C0550',
                       'count': 1,
                       'name': '20 Inch Wheel'}},
     'Exterior/Interior': {'C0120': {'category': 'Exterior/Interior',
                                     'code': 'C0120',
                                     'count': 6,
                                     'name': 'P.Sunroof'},
                           'C0130': {'category': 'Exterior/Interior',
                                     'code': 'C0130',
                                     'count': 2,
                                     'name': 'Sunroof'},
                           'C0470': {'category': 'Exterior/Interior',
                                     'code': 'C0470',
                                     'count': 2,
                                     'name': 'LED Light'}},
     'Features': {'C0010': {'category': 'Features',
                            'code': 'C0010',
                            'count': 6,
                            'name': 'No Accident'},
                  'C0020': {'category': 'Features',
                            'code': 'C0020',
                            'count': 3,
                            'name': 'Repaired'},
                  'C0030': {'category': 'Features',
                            'code': 'C0030',
                            'count': 1,
                            'name': 'Polished'},
                  'C0270': {'category': 'Features',
                            'code': 'C0270',
                            'count': 1,
                            'name': 'Chrome Kit'},
                  'C0500': {'category': 'Features',
                            'code': 'C0500',
                            'count': 2,
                            'name': 'One-Owner'},
                  'C0510': {'category': 'Features',
                            'code': 'C0510',
                            'count': 1,
                            'name': 'Hot Item'},
                  'C0540': {'category': 'Features',
                            'code': 'C0540',
                            'count': 2,
                            'name': 'Non-Smoking'},
                  'C0580': {'category': 'Features',
                            'code': 'C0580',
                            'count': 1,
                            'name': 'A/C Guarantee'},
                  'C0600': {'category': 'Features',
                            'code': 'C0600',
                            'count': 1,
                            'name': 'Ask Discount'}},
     'Safety': {'C0080': {'category': 'Safety',
                          'code': 'C0080',
                          'count': 2,
                          'name': 'ABS'},
                'C0140': {'category': 'Safety',
                          'code': 'C0140',
                          'count': 3,
                          'name': 'Smart Key'},
                'C0170': {'category': 'Safety',
                          'code': 'C0170',
                          'count': 1,
                          'name': 'Rear Camera'},
                'C0210': {'category': 'Safety',
                          'code': 'C0210',
                          'count': 1,
                          'name': 'Spare Key'},
                'C0490': {'category': 'Safety',
                          'code': 'C0490',
                          'count': 1,
                          'name': 'Rear Sensor'}}}
     */
    @Test
    @Disabled
    @DisplayName("StringRedisSerializer를 사용하면 @class 정보가 없어도 된다")
    void test_redis_template_strkey_strval() throws JsonProcessingException {
        // RedisTemplate<String, Object>로 저장된 JSON을 읽어올 때,
        // GenericJackson2JsonRedisSerializer는 @class 정보가 필요함.
        ValueOperations<String, String> vop = redisTemplateString.opsForValue();

        String carHotmarkAggregation = vop.get("car_hotmark_aggregation");
        TypeReference<Mark> typeReference = new TypeReference<Mark>() {};

        Mark aggr = objectMapper.readValue(carHotmarkAggregation, typeReference);

        assertThat(aggr)
                .extracting("etc")
                .extracting("C0060")
                .isInstanceOf(Code.class)
                .extracting("name")
                .isEqualTo("4WD");

        assertThat(aggr)
                .extracting("safety")
                .extracting("C0080")
                .isInstanceOf(Code.class)
                .extracting("name")
                .isEqualTo("ABS");
    }

    @ToString
    @Getter
    static class Mark {
        @JsonProperty("Etc")
        private Map<String, Code> etc;
        @JsonProperty("Safety")
        private Map<String, Code> safety;
        @JsonProperty("Features")
        private Map<String, Code> features;
        @JsonProperty("Exterior/Interior")
        private Map<String, Code> exteriorInterior;
    }

    @ToString
    @Getter
    static class Code {
        private String category;
        private String code;
        private String name;
        private Integer count;
    }

}
