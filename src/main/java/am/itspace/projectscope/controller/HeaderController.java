package am.itspace.projectscope.controller;

import am.itspace.projectscope.model.User;
import am.itspace.projectscope.security.CurrentUser;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;


@ControllerAdvice
public class HeaderController {

    @ModelAttribute("user")
    public User username(@AuthenticationPrincipal CurrentUser currentUser) {
        User user = null;
        if (currentUser != null){
            user = currentUser.getUser();
        }
        return user;
    }
}
