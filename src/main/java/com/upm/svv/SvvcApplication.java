package com.upm.svv;

import com.upm.svv.service.InputService;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Arrays;

@SpringBootApplication
public class SvvcApplication implements ApplicationRunner {

    private InputService inputService;

    public static void main(String[] args) {
        SpringApplication.run(SvvcApplication.class, args);
    }

    @Override
    public void run(ApplicationArguments args) {
        try {
            System.out.println("ARGS " + Arrays.toString(args.getSourceArgs()));
            String file = args.getSourceArgs()[0];
            inputService.init(file);

        } catch (Exception e) {
            System.out.println("No args provided (ignore this during testing)");
        }
    }
}
