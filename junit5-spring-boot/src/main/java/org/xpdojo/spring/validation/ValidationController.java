package org.xpdojo.spring.validation;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Optional;

/**
 * Java Bean Validation to Jakarta Bean Validation<p>
 * v2.0 to v3.0<p>
 * <code>javax.validation</code> to <code>jakarta.validation</code><p>
 *
 * @see <a href="https://jcp.org/en/jsr/detail?id=380">JSR 380 - Bean Validation 2.0</a>
 * @see <a href="https://meetup.toast.com/posts/223">Validation 어디까지 해봤니?</a>
 */
@Slf4j
@RequestMapping("/validation")
@RestController
public class ValidationController {

    private final UserService userService;

    public ValidationController(UserService userService) {
        this.userService = userService;
    }

    /**
     * GET Method의 경우 Request-URI로 어떤 정보를 조회할 것인지를 지정한다.
     * Body로 요청 데이터를 보내지 말자.
     *
     * <blockquote>
     * <a href="https://www.rfc-editor.org/rfc/rfc2616#section-4.3">[RFC-2616] HTTP/1.1 spec, section 4.3</a>
     * <br>
     * ...if the request method does not include defined semantics for an entity-body,
     * then the message-body SHOULD be ignored when handling the request.
     * </blockquote>
     *
     * <blockquote>
     * <a href="https://www.rfc-editor.org/rfc/rfc2616#section-9.3">[RFC-2616] HTTP/1.1 spec, section 9.3: <code>GET</code> Method</a>
     * <br>
     * The GET method means retrieve whatever information ([...]) is identified by the Request-URI.
     * </blockquote>
     *
     * The RFC2616 referenced as "HTTP/1.1 spec" is now obsolete.
     * In 2014 it was replaced by RFCs 7230-7237.
     * Quote "the message-body SHOULD be ignored when handling the request" has been deleted.
     * It's now just "Request message framing is independent of method semantics, even if the method doesn't define any use for a message body"
     * The 2nd quote "The GET method means retrieve whatever information ... is identified by the Request-URI" was deleted.
     *
     * <blockquote>
     * <a href="https://www.rfc-editor.org/rfc/rfc7231#section-4.3.1">[RFC-7231] HTTP/1.1 spec, section 4.3.1: <code>GET</code> Method</a>
     * <br>
     * A payload within a GET request message has no defined semantics;
     * sending a payload body on a GET request might cause some existing implementations to reject the request.
     * </blockquote>
     * <p>
     * Usages:
     * <pre>
     * curl 'http://localhost:8080/validation/get?name=testestest&age=18&email=test@email.com'
     * </pre>
     *
     * @param user 사용자
     * @return 사용자
     * @see <a href="https://jcp.org/en/jsr/detail?id=380">JSR 380 - Bean Validation 2.0</a>
     * @see <a href="https://stackoverflow.com/questions/978061/http-get-with-request-body">HTTP GET with request body?</a>
     */
    @GetMapping("/get")
    public UserDto getUser(@Valid UserDto user) {
        return userService.findUser(user);
    }

    /**
     * Usages:
     * <pre>
     * curl 'http://localhost:8080/validation/get/request-param?name=test&age=1&email=test'
     * curl 'http://localhost:8080/validation/get/request-param?name=test&email=test'
     * </pre>
     *
     * @param email 이메일
     * @param name  이름
     * @param age   나이
     * @return 사용자
     */
    @GetMapping("/get/request-param")
    public UserDto getUserWithRequestParam(
            @RequestParam("email") String email,
            @RequestParam("name") String name,
            @RequestParam(value = "age", required = false) Integer age) {

        log.info("email: {}, name: {}, age: {}", email, name, age);
        Integer replaced =
                Optional.ofNullable(age)
                        .orElse(0);

        UserDto user = UserDto.builder()
                .email(email)
                .name(name)
                .age(replaced)
                .build();

        return userService.findUser(user);
    }

    /**
     * Usages:
     * <pre>
     *     curl --verbose \
     *          --request POST 'http://localhost:8080/validation/post' \
     *          --header 'Content-Type: application/json' \
     *          --data-raw '{
     *              "email": "test@test.com",
     *              "name": "testestest",
     *              "age": 18
     *          }'
     * </pre>
     *
     * @param user 사용자
     * @return 사용자
     */
    @PostMapping("/post")
    public UserDto postUser(@Valid @RequestBody UserDto user) {
        return userService.findUser(user);
    }
}
