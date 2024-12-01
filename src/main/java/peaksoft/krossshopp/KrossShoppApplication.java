package peaksoft.krossshopp;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@SpringBootApplication
public class KrossShoppApplication {

    public static void main(String[] args) {
        SpringApplication.run(KrossShoppApplication.class, args);
    }

    @GetMapping("/")
    public String greetingPage() {
        return "<h1>Welcome to KrossShopp Application!!!<h1/>";
    }
}
