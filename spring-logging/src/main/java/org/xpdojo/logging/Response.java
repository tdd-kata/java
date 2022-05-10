package org.xpdojo.logging;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
public class Response {
    private String message;
}
