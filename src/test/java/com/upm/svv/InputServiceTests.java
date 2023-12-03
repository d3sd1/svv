package com.upm.svv;

import com.upm.svv.service.InputService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

@SpringBootTest
class InputServiceTests {
    private final PrintStream standardOut = System.out;
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
    @Autowired
    private InputService inputService;

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
    void badFileInput() {
        inputService.init("badfile_l");
        Assertions.assertTrue(outputStreamCaptor.toString().contains("Could not read file"));
    }

    @Test
    void goodFileInputBadJson() {
        inputService.init("./src/test/resources/bad_json.txt");
        Assertions.assertTrue(outputStreamCaptor.toString().contains("Could not read file"));
    }

    @Test
    void goodFileInputGoodJson() {
        inputService.init("./src/test/resources/good_json.txt");
        Assertions.assertTrue(outputStreamCaptor.toString().contains("got json"));
    }

}
