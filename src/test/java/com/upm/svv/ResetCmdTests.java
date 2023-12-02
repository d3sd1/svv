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
class ResetCmdTests {
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
    void resetCmdNoDirs() {
        inputService.init("./src/test/resources/ResetCmd/reset_with_no_dirs.txt");
        Assertions.assertTrue(outputStreamCaptor.toString().contains("Dirs are empty! Could not reset."));
    }
    @Test
    void resetCmdWithDirs() {
        inputService.init("./src/test/resources/ResetCmd/reset_with_dirs.txt");
        Assertions.assertTrue(outputStreamCaptor.toString().contains("All directories removed with reset."));
    }


}
