package am.itspace.projectscope.controller;

import am.itspace.projectscope.dto.ProjectDto;
import am.itspace.projectscope.model.Projects;
import am.itspace.projectscope.model.User;
import am.itspace.projectscope.model.UserType;
import am.itspace.projectscope.security.CurrentUser;
import am.itspace.projectscope.service.ProjectService;
import am.itspace.projectscope.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;
import java.util.List;

@Controller
@RequiredArgsConstructor
@Log4j2
public class ProjectController {

    private final ProjectService projectService;
    private final UserService userService;


    @PostMapping("/project")
    public String addProject(@AuthenticationPrincipal CurrentUser currentUser,@ModelAttribute ProjectDto projectDto, BindingResult result, ModelMap modelMap){

        Projects projects = Projects.builder()
                .name(projectDto.getName())
                .date(new Date())
                .deadline(projectDto.getDeadline())
                .members(projectDto.getMembers())
                .hours(projectDto.getHours())
                .user(currentUser.getUser())
                .build();
        projectService.save(projects);
        log.info("project was added");
        log.info(new Date());

        return "redirect:/user/leader/projects";
    }

    @GetMapping("/user/leader/projects")
    public String allProjects(@AuthenticationPrincipal CurrentUser currentUser, ModelMap modelMap){
        if (currentUser == null && currentUser.getUser().getUserType() == UserType.TEAM_MEMBER){
            return "redirect:/";
        }

        List<Projects> projects = projectService.allProjectsByUser(currentUser.getUser());
        modelMap.addAttribute("projects", projects);
        return "projects";
    }

    @GetMapping("/user/leader/projects/add")
    public String addProjects(ModelMap modelMap){
        List<User> users = userService.findByUsertype(UserType.TEAM_MEMBER);
        modelMap.addAttribute("users", users);
        return "addProjects";
    }

    @GetMapping("/user/leader/projects/delete")
    public String delete(@RequestParam("id") int id){
        projectService.delete(id);
        log.info("project with {} id was deleted", id);
        return "redirect:/user/leader/projects";
    }
}
