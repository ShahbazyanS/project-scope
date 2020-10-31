package am.itspace.projectscope.controller;

import am.itspace.projectscope.dto.UserDto;
import am.itspace.projectscope.model.Projects;
import am.itspace.projectscope.model.User;
import am.itspace.projectscope.model.UserType;
import am.itspace.projectscope.security.CurrentUser;
import am.itspace.projectscope.service.ProjectService;
import am.itspace.projectscope.service.UserService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;


@Controller
@RequiredArgsConstructor
public class UserController {

    @Value("${file.upload.dir}")
    private String upladDir;

    private final PasswordEncoder passwordEncoder;
    private final UserService userService;
    private final ProjectService projectService;


    @GetMapping("/user/leader")
    public String leaderPage(ModelMap modelMap, @AuthenticationPrincipal CurrentUser currentUser){
        if (currentUser == null && currentUser.getUser().getUserType() == UserType.TEAM_MEMBER){
            return "redirect:/";
        }
        User user = currentUser.getUser();
        List<User> users = userService.findByUsertype(UserType.TEAM_MEMBER);
        modelMap.addAttribute("users", users);
        modelMap.addAttribute("user", user);
        return "leaderPage";
    }


    @PostMapping("/user/register")
    public String addUser(@Valid @ModelAttribute("user") UserDto userDto, BindingResult result,
                          ModelMap modelMap, @RequestParam("image")MultipartFile file) throws IOException {

        if (result.hasErrors()) {
            modelMap.addAttribute("users", userService.findAll());
            return "registerPage";
        }

        Optional<User> byUsername = userService.getUserByEmail(userDto.getEmail());
        if (byUsername.isPresent()) {
            return "redirect:/";
        }

        String name = System.currentTimeMillis() + "_" + file.getOriginalFilename();
        File image = new File(upladDir, name);
        file.transferTo(image);
        User user = User.builder()
                .name(userDto.getName())
                .surname(userDto.getSurname())
                .email(userDto.getEmail())
                .password(passwordEncoder.encode(userDto.getPassword()))
                .profilePicture(name)
                .userType(UserType.TEAM_MEMBER)
                .build();
        userService.save(user);
        return "redirect:/";

    }

    @GetMapping("/user/member")
    public String memberPage(ModelMap modelMap, @AuthenticationPrincipal CurrentUser currentUser){
        if (currentUser == null){
            return "redirect:/";
        }
        List<Projects> projects = projectService.findByMembers(currentUser.getUser().getName());
        modelMap.addAttribute("user", currentUser.getUser());
        modelMap.addAttribute("projects", projects);
        return "memberPage";
    }


    @GetMapping(
            value = "/image",
            produces = MediaType.IMAGE_JPEG_VALUE
    )
    public @ResponseBody
    byte[] getImage(@RequestParam("name") String imageName) throws IOException {

        InputStream in = new FileInputStream(upladDir + File.separator + imageName);
        return IOUtils.toByteArray(in);
    }
}
