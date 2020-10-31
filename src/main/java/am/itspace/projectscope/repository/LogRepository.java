package am.itspace.projectscope.repository;

import am.itspace.projectscope.model.Log;
import am.itspace.projectscope.model.Projects;
import am.itspace.projectscope.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LogRepository extends JpaRepository<Log,Integer> {

    List<Log> findAllByUser(User user);
}
