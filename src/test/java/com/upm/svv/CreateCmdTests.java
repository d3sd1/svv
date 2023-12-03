package com.upm.svv;

import com.upm.svv.service.InputService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

@SpringBootTest
class CreateCmdTests {
    private final PrintStream standardOut = System.out;
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
    @Autowired
    private InputService inputService;

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
    @Disabled //TODO
    void createCmdNoDirShouldFail() {
        inputService.init("./src/test/resources/CreateCmd/create_no_dir.txt");
        Assertions.assertTrue(outputStreamCaptor.toString().contains("The directory already exists and nothing has been done"));
        Assertions.assertTrue(inputService.cmdService.contactList.isEmpty());
    }

    @Test
    @Disabled //TODO
    void createCmdEmptyDirShouldFail() {
        inputService.init("./src/test/resources/CreateCmd/create_empty_dir.txt");
        Assertions.assertTrue(outputStreamCaptor.toString().contains("The directory already exists and nothing has been done"));
        Assertions.assertTrue(inputService.cmdService.contactList.isEmpty());
    }

    @Test
    void createCmdNewDirShouldOK() {
        inputService.init("./src/test/resources/CreateCmd/create_new_dir.txt");
        Assertions.assertTrue(outputStreamCaptor.toString().contains("The directory was created"));
        Assertions.assertFalse(inputService.cmdService.contactList.isEmpty());
        Assertions.assertTrue(inputService.cmdService.contactList.containsKey("usemepath"));
    }

    @Test
    void createCmdDuplicatedDirShouldFail() {
        inputService.init("./src/test/resources/CreateCmd/create_duplicated_dir.txt");
        Assertions.assertTrue(outputStreamCaptor.toString().contains("The directory already exists and nothing has been done"));
        Assertions.assertFalse(inputService.cmdService.contactList.isEmpty());
        Assertions.assertTrue(inputService.cmdService.contactList.containsKey("usemepath"));
    }

}
