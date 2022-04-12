package org.xpdojo.spring.validation;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

@Getter
@Setter
public class UserDto {

    @NotBlank
    @Email
    private String email;

    @NotBlank
    @Size(min = 10,
            max = 200,
            message = "이름은 {min}자에서 {max}자까지 입력할 수 있습니다. 입력한 글자: ${validatedValue}")
    private String name;

    @Positive
    @Min(value = 18, message = "{value}세 이상만 가입할 수 있습니다")
    @Max(value = 60, message = "{value}세 이하만 가입할 수 있습니다")
    private int age;

    /**
     * Cannot construct instance of `org.xpdojo.spring.validation.UserDto` (no Creators, like default constructor, exist): cannot deserialize from Object value (no delegate- or property-based Creator)
     */
    public UserDto() {
    }

    @Builder
    public UserDto(String email, String name, int age) {
        this.email = email;
        this.name = name;
        this.age = age;
    }
}
