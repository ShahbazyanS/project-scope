package am.itspace.projectscope.service;

import am.itspace.projectscope.model.Log;
import am.itspace.projectscope.model.Projects;
import am.itspace.projectscope.model.User;
import am.itspace.projectscope.repository.LogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LogService {

    private final LogRepository logRepository;

    public void save(Log log){
        logRepository.save(log);
    }

    public List<Log> allLogByUser(User user){
        return logRepository.findAllByUser(user);
    }

    public void delete(int id){
        logRepository.deleteById(id);
    }
}
