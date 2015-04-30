package pl.edu.agh.awi.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.edu.agh.awi.persistence.PersistenceService;

@Controller
@RequestMapping("/metar")
public class MetarController {

    @Autowired
    private PersistenceService persistenceService;


}
