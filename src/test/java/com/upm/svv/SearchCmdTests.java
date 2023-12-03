package com.upm.svv;

import com.upm.svv.service.InputService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

@SpringBootTest
class SearchCmdTests {
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
    void searchCmdEmptyPattern() {
        inputService.init("./src/test/resources/SearchCmd/empty_pattern.txt");
        Assertions.assertTrue(outputStreamCaptor.toString().contains("No coincidences found"));
        Assertions.assertTrue(inputService.cmdService.contactList.isEmpty());
    }
    @Test
    @Disabled //TODO
    void searchCmdNullPattern() {
        inputService.init("./src/test/resources/SearchCmd/empty_pattern.txt");
        Assertions.assertTrue(outputStreamCaptor.toString().contains("No coincidences found"));
        Assertions.assertTrue(inputService.cmdService.contactList.isEmpty());
    }
    @Test
    @Disabled //TODO
    void searchCmdEmptyDirname() {
        inputService.init("./src/test/resources/SearchCmd/empty_dirname.txt");
        Assertions.assertTrue(outputStreamCaptor.toString().contains("No coincidences found"));
        Assertions.assertTrue(inputService.cmdService.contactList.isEmpty());
    }

    @Test
    @Disabled //TODO
    void searchCmdNullDirname() {
        inputService.init("./src/test/resources/SearchCmd/null_dirname.txt");
        Assertions.assertTrue(outputStreamCaptor.toString().contains("No coincidences found"));
        Assertions.assertTrue(inputService.cmdService.contactList.isEmpty());
    }

}
