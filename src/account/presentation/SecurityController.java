package account.presentation;

import account.business.Log;
import account.business.LogService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/security")
public class SecurityController {

    private final LogService logService;

    public SecurityController(LogService logService) {
        this.logService = logService;
    }

    @GetMapping("/events")
    public List<Log> getEvents() {
        return logService.findAll();
    }
}
