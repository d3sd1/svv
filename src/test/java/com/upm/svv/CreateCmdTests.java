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
class CreateCmdTests {
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
    void createCmdNoDirShouldFail() {
        inputService.init("./src/test/resources/CreateCmd/create_no_dir.txt");
        Assertions.assertTrue(outputStreamCaptor.toString().contains("Invalid name for pathname!"));
        Assertions.assertTrue(inputService.cmdService.contactList.isEmpty());
    }
    @Test
    void createCmdEmptyDirShouldFail() {
        inputService.init("./src/test/resources/CreateCmd/create_empty_dir.txt");
        Assertions.assertTrue(outputStreamCaptor.toString().contains("Invalid name for pathname!"));
        Assertions.assertTrue(inputService.cmdService.contactList.isEmpty());
    }
    @Test
    void createCmdNewDirShouldOK() {
        inputService.init("./src/test/resources/CreateCmd/create_new_dir.txt");
        Assertions.assertTrue(outputStreamCaptor.toString().contains("Added dir: usemepath"));
        Assertions.assertFalse(inputService.cmdService.contactList.isEmpty());
        Assertions.assertTrue(inputService.cmdService.contactList.containsKey("usemepath"));
    }
    @Test
    void createCmdDuplicatedDirShouldFail() {
        inputService.init("./src/test/resources/CreateCmd/create_duplicated_dir.txt");
        Assertions.assertTrue(outputStreamCaptor.toString().contains("Dir name already exists: usemepath"));
        Assertions.assertFalse(inputService.cmdService.contactList.isEmpty());
        Assertions.assertTrue(inputService.cmdService.contactList.containsKey("usemepath"));
    }

}