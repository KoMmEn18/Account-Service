package account.business;

import account.persistance.LogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LogService {
    private final LogRepository logRepository;

    @Autowired
    public LogService(LogRepository logRepository) {
        this.logRepository = logRepository;
    }

    public List<Log> findAll() {
        return logRepository.findAllByOrderById();
    }

    public void save(Log log) {
        logRepository.save(log);
    }
}
