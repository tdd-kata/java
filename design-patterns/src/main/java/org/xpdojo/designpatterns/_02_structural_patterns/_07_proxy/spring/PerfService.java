package org.xpdojo.designpatterns._02_structural_patterns._07_proxy.spring;

import org.springframework.stereotype.Service;

@Service
public class PerfService {

    /**
     * @see org.springframework.transaction.annotation.Transactional
     * @see org.springframework.cache.annotation.Cacheable
     */
    public void start() {
        for (long i = 0; i < 1_000_000; i++) {
            System.out.println(i);
        }
    }

}
