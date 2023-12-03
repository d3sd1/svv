package com.upm.svv;

import com.upm.svv.service.InputService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

@SpringBootTest
class AddCmdTests {
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
    void addCmdNullDirShouldFail() {
        inputService.init("./src/test/resources/AddCmd/add_null_dir.txt");
        Assertions.assertTrue(outputStreamCaptor.toString().contains("Dir should not be null or empty."));
        Assertions.assertTrue(inputService.cmdService.contactList.isEmpty());
    }

    @Test
    @Disabled //TODO
    void addCmdEmptyDirShouldFail() {
        inputService.init("./src/test/resources/AddCmd/add_empty_dir.txt");
        Assertions.assertTrue(outputStreamCaptor.toString().contains("Dir should not be null or empty."));
        Assertions.assertTrue(inputService.cmdService.contactList.isEmpty());
    }
    @Test
    void addCmdNewDirShouldPass() {
        inputService.init("./src/test/resources/AddCmd/add_dir_ok.txt");
        Assertions.assertTrue(outputStreamCaptor.toString().contains("Added dir: testme"));
        Assertions.assertFalse(inputService.cmdService.contactList.isEmpty());
        Assertions.assertTrue(inputService.cmdService.contactList.containsKey("testme"));
        Assertions.assertEquals("testme", inputService.cmdService.contactList.get("testme").getPath());
        Assertions.assertNotEquals("TesTMe", inputService.cmdService.contactList.get("testme").getPath());
    }

    @Test
    void addCmdDuplicatedDirShouldFail() {
        inputService.init("./src/test/resources/AddCmd/add_dir_duplicated.txt");
        Assertions.assertTrue(outputStreamCaptor.toString().contains("Dir name already exists: testme"));
        Assertions.assertFalse(inputService.cmdService.contactList.isEmpty());
        Assertions.assertTrue(inputService.cmdService.contactList.containsKey("testme"));
        Assertions.assertEquals("testme", inputService.cmdService.contactList.get("testme").getPath());
        Assertions.assertNotEquals("TesTMe", inputService.cmdService.contactList.get("testme").getPath());
        Assertions.assertEquals(1, inputService.cmdService.contactList.size());
    }

}
