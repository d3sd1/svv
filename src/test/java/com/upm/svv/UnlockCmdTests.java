package com.upm.svv;

import com.upm.svv.service.InputService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

@SpringBootTest
class UnlockCmdTests {
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
    void unlockCmdInvalidDirShouldFail() {
        inputService.init("./src/test/resources/UnlockCmd/invalid_dir.txt");
        Assertions.assertTrue(outputStreamCaptor.toString().contains("the directory does not exist or it has no pwd"));
        Assertions.assertFalse(inputService.cmdService.contactList.isEmpty());
    }

    @Test
    void unlockCmdValidDirValidPwdNoPreviousPWDShouldFail() {
        inputService.init("./src/test/resources/UnlockCmd/valid_dir_no_previous_pwd.txt");
        Assertions.assertTrue(outputStreamCaptor.toString().contains("the directory does not exist or it has no pwd"));
        Assertions.assertFalse(inputService.cmdService.contactList.isEmpty());
        Assertions.assertTrue(inputService.cmdService.contactList.containsKey("usemepath"));
        Assertions.assertNull(inputService.cmdService.contactList.get("usemepath").getPwd());
    }

    @Test
    void unlockCmdValidDirValidPwdShouldPass() {
        inputService.init("./src/test/resources/UnlockCmd/valid_dir_right_pwd.txt");
        Assertions.assertTrue(outputStreamCaptor.toString().contains("The directory was unprotected"));
        Assertions.assertFalse(inputService.cmdService.contactList.isEmpty());
        Assertions.assertTrue(inputService.cmdService.contactList.containsKey("usemepath"));
        Assertions.assertEquals("1234",inputService.cmdService.contactList.get("usemepath").getPwd());
    }

    @Test
    void unlockCmdValidDirInvalidPwdShouldFail() {
        inputService.init("./src/test/resources/UnlockCmd/valid_dir_invalid_pwd.txt");
        Assertions.assertTrue(outputStreamCaptor.toString().contains("Invalid password"));
        Assertions.assertFalse(inputService.cmdService.contactList.isEmpty());
        Assertions.assertTrue(inputService.cmdService.contactList.containsKey("usemepath"));
        Assertions.assertEquals("1234", inputService.cmdService.contactList.get("usemepath").getPwd());
    }

}
