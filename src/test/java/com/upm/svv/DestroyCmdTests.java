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
class DestroyCmdTests {
    @Autowired
    private InputService inputService;
    private final PrintStream standardOut = System.out;

    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
    @BeforeEach
    public void setUp() {
        inputService.cmdService.contactList.clear();
        outputStreamCaptor.reset();
        System.setOut(new PrintStream(outputStreamCaptor));
    }
    @AfterEach
    public void tearDown() {
        inputService.cmdService.contactList.clear();
        String out = outputStreamCaptor.toString();
        outputStreamCaptor.reset();
        System.setOut(standardOut);
        System.out.println(out);
    }
    @Test
    void destroyCmdInvalidDirShouldFail() {
        inputService.init("./src/test/resources/DestroyCmd/destroy_invalid_dir.txt");
        Assertions.assertTrue(outputStreamCaptor.toString().contains("the directory does not exist so nothing has been done"));
        Assertions.assertFalse(inputService.cmdService.contactList.isEmpty());
        Assertions.assertTrue(inputService.cmdService.contactList.containsKey("usemepath"));
    }
    @Test
    void destroyCmdDirShouldOK() {
        inputService.init("./src/test/resources/DestroyCmd/destroy_valid_dir.txt");
        Assertions.assertTrue(outputStreamCaptor.toString().contains("The directory was destroyed."));
        Assertions.assertTrue(inputService.cmdService.contactList.isEmpty());
    }

}
