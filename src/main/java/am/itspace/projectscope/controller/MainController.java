package am.itspace.projectscope.controller;

import am.itspace.projectscope.model.User;
import am.itspace.projectscope.model.UserType;
import am.itspace.projectscope.security.CurrentUser;
import am.itspace.projectscope.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
@RequiredArgsConstructor
public class MainController {


    private final UserService userService;

    @GetMapping("/")
    public String main() {
        return "index";
    }

    @GetMapping("/registerPage")
    public String registerPage() {
        return "registerPage";
    }


    @GetMapping("/loginPage")
    public String login() {
        return "loginPage";
    }

    @GetMapping("/successLogin")
    public String successe(@AuthenticationPrincipal CurrentUser currentUser) {
        if (currentUser == null) {
            return "redirect:/";
        }
        User user = currentUser.getUser();
        if (user.getUserType() == UserType.TEAM_LEADER) {
            return "redirect:/user/leader";
        } else {
            return "redirect:/user/member";
        }
    }


}
