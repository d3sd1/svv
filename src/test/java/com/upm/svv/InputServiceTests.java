package com.upm.svv;

import com.upm.svv.service.InputService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.DefaultApplicationArguments;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

@SpringBootTest
class InputServiceTests {
    @Autowired
    private InputService inputService;
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
    @BeforeEach
    public void setUp() {
        System.setOut(new PrintStream(outputStreamCaptor));
        outputStreamCaptor.reset();
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
