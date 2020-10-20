package cz.cvut.fit.ryntluka;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.awt.*;
import java.util.concurrent.atomic.AtomicLong;

@RestController
public class Controller {

    private static final String template = "Hello, %s";
    private final AtomicLong counter = new AtomicLong();

    @RequestMapping(value = "/greeting")
    public void greeting(@RequestParam(value = "name", defaultValue = "World") String name) {
        System.out.println("Hello " + name);
    }
}
