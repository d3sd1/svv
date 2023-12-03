package com.upm.svv;

import com.upm.svv.service.InputService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

@SpringBootTest
class LockCmdTests {
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
    void lockCmdInvalidDirShouldFail() {
        inputService.init("./src/test/resources/LockCmd/invalid_dir.txt");
        Assertions.assertTrue(outputStreamCaptor.toString().contains("the directory does not exist or it already has a pwd"));
        Assertions.assertFalse(inputService.cmdService.contactList.isEmpty());
    }

    @Test
    void lockCmdValidDirValidPwdNoPreviousPWDShouldPass() {
        inputService.init("./src/test/resources/LockCmd/valid_dir_no_previous_pwd.txt");
        Assertions.assertTrue(outputStreamCaptor.toString().contains("The directory was protected"));
        Assertions.assertFalse(inputService.cmdService.contactList.isEmpty());
        Assertions.assertTrue(inputService.cmdService.contactList.containsKey("usemepath"));
        Assertions.assertEquals("1234", inputService.cmdService.contactList.get("usemepath").getPwd());
    }

    @Test
    @Disabled
        //TODO
    void lockCmdValidDirNullOrEmptyPwdShouldFail() {
        inputService.init("./src/test/resources/LockCmd/valid_dir_empty_pwd.txt");
        Assertions.assertTrue(outputStreamCaptor.toString().contains("the directory must be protected with non-empty and non-blank pwd"));
        Assertions.assertFalse(inputService.cmdService.contactList.isEmpty());
    }

    @Test
    void lockCmdValidDirValidPwdPreviousPWDShouldFail() {
        inputService.init("./src/test/resources/LockCmd/valid_dir_previous_pwd.txt");
        Assertions.assertTrue(outputStreamCaptor.toString().contains("the directory does not exist or it already has a pwd"));
        Assertions.assertFalse(inputService.cmdService.contactList.isEmpty());
    }

}
