package am.itspace.projectscope.service;


import am.itspace.projectscope.model.Projects;
import am.itspace.projectscope.model.User;
import am.itspace.projectscope.repository.ProjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProjectService {

    private final ProjectRepository projectRepository;

    public void save(Projects projects) {
        projectRepository.save(projects);
    }

    public List<Projects> allProjectsByUser(User user) {
        return projectRepository.findAllByUser(user);
    }

    public void delete(int id) {
        projectRepository.deleteById(id);
    }

    public List<Projects> findByMembers(String members) {
       return projectRepository.findAllByMembers(members);
    }
}
