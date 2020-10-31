package am.itspace.projectscope.repository;

import am.itspace.projectscope.model.Projects;
import am.itspace.projectscope.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProjectRepository extends JpaRepository<Projects,Integer> {

    List<Projects> findAllByUser(User user);

    void deleteProjectsById(int id);

    List<Projects> findAllByMembers(String members);
}
