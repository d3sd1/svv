package com.upm.svv;

import com.upm.svv.service.InputService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.ByteArrayOutputStream;
import java.io.Console;
import java.io.PrintStream;

@SpringBootTest
class InputEdgeTests {
    @Autowired
    private InputService inputService;
    private final PrintStream standardOut = System.out;

    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
    @BeforeEach
    public void setUp() {
        outputStreamCaptor.reset();
        System.setOut(new PrintStream(outputStreamCaptor));
    }
    @AfterEach
    public void tearDown() {
        String out = outputStreamCaptor.toString();
        outputStreamCaptor.reset();
        System.setOut(standardOut);
        System.out.println(out);
    }
    @Test
    void edgeCaseInvalidCmd() {
        inputService.init("./src/test/resources/InputEdgeTests/invalid_cmd.txt");
       Assertions.assertTrue(outputStreamCaptor.toString().contains("Unrecognized command"));
    }

    @Test
    void edgeCaseNullCmd() {
        inputService.init("./src/test/resources/InputEdgeTests/null_cmd.txt");
       Assertions.assertTrue(outputStreamCaptor.toString().contains("You returning null command! Be a nice person and don't try to catc"));
    }

    @Test
    void edgeCaseEmptyCmd() {
        inputService.init("./src/test/resources/InputEdgeTests/blank_cmd.txt");
       Assertions.assertTrue(outputStreamCaptor.toString().contains("You returning null command! Be a nice person and don't try to catc"));
    }

}
