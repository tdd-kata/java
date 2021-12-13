package com.markruler.datajpa.dto;

import org.springframework.beans.factory.annotation.Value;

public interface UsernameOnly {

    @Value("#{target.username + ' ' + target.age}") // Open Projection
    String getUsername();
}
