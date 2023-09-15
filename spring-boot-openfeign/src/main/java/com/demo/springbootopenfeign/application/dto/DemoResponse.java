package com.demo.springbootopenfeign.application.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class DemoResponse<T> {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String result;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String errorMessage;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String errorCode;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String userMessage;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String userCode;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T data;

    public static <T> DemoResponse<T> success() {
        return DemoResponse.<T>builder()
                .result("SUCCESS")
                .build();
    }

    public static <T> DemoResponse<T> success(T t) {
        return DemoResponse.<T>builder()
                .result("SUCCESS")
                .data(t)
                .build();
    }

    public static <T> DemoResponse<T> fail(String errorMessage) {
        return DemoResponse.<T>builder()
                .result("FAIL")
                .errorMessage(errorMessage)
                .build();
    }

    public static <T> DemoResponse<T> fail(String errorCode, String errorMessage) {
        return DemoResponse.<T>builder()
                .result("FAIL")
                .errorCode(errorCode)
                .errorMessage(errorMessage)
                .build();
    }

}
