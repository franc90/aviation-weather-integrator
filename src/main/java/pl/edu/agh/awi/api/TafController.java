package pl.edu.agh.awi.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.edu.agh.awi.persistence.PersistenceService;

@RestController
@RequestMapping("/taf")
public class TafController {

    @Autowired
    private PersistenceService persistenceService;
}
