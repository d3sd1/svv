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
class ListCmdTests {
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
    void listCmdNoDirs() {
        inputService.init("./src/test/resources/ListCmd/list_no_dirs_cmd.txt");
        Assertions.assertTrue(outputStreamCaptor.toString().contains("Dirs are empty! Could not list"));
        Assertions.assertTrue(inputService.cmdService.contactList.isEmpty());
    }
    @Test
    void listCmdWithDirs() {
        inputService.init("./src/test/resources/ListCmd/list_with_dirs_cmd.txt");
        Assertions.assertTrue(outputStreamCaptor.toString().contains("Found List Path: usemepath"));
        Assertions.assertTrue(inputService.cmdService.contactList.containsKey("usemepath"));
    }


}
