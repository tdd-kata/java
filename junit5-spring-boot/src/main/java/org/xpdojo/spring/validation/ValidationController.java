package org.xpdojo.spring.validation;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * Java Bean Validation to Jakarta Bean Validation<p>
 * v2.0 to v3.0<p>
 * <code>javax.validation</code> to <code>jakarta.validation</code><p>
 *
 * @see <a href="https://jcp.org/en/jsr/detail?id=380">JSR 380 - Bean Validation 2.0</a>
 * @see <a href="https://meetup.toast.com/posts/223">Validation 어디까지 해봤니?</a>
 */
@RequestMapping("/validation")
@RestController
public class ValidationController {

    private final UserService userService;

    public ValidationController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/get")
    public UserDto findUser(@Valid @RequestBody UserDto user) {
        return userService.findUser(user);
    }
}
