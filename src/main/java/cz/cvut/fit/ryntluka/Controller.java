package cz.cvut.fit.ryntluka;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {

    @RequestMapping(value = "/greeting")
    public void greeting(@RequestParam(value = "name", defaultValue = "World") String name) {
        System.out.println("Hello " + name);
    }
}
