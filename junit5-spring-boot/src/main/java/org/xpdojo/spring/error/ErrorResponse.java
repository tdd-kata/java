package org.xpdojo.spring.error;

public class ErrorResponse {
    private String message;

    public ErrorResponse(String message) {
        this.message = message;
    }

    /**
     * getter를 만들지 않으면 아래 오류 발생
     *
     * @return Error Message
     * @throws org.springframework.web.HttpMediaTypeNotAcceptableException: Could not find acceptable representation
     */
    public String getMessage() {
        return message;
    }
}
