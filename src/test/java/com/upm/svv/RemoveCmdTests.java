package com.upm.svv;

import com.upm.svv.service.InputService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

@SpringBootTest
class RemoveCmdTests {
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
    void removeCmdNullDirShouldFail() {
        inputService.init("./src/test/resources/RemoveCmd/remove_null_dir.txt");
        Assertions.assertTrue(outputStreamCaptor.toString().contains("Dir name not found for removal:"));
        Assertions.assertFalse(inputService.cmdService.contactList.isEmpty());
    }

    @Test
    void removeCmdEmptyDirShouldFail() {
        inputService.init("./src/test/resources/RemoveCmd/remove_empty_dir.txt");
        Assertions.assertTrue(outputStreamCaptor.toString().contains("Dir name not found for removal:"));
        Assertions.assertFalse(inputService.cmdService.contactList.isEmpty());
    }

    @Test
    @Disabled //TODO
    void removeCmdRightDirNotExistsShouldFail() {
        inputService.init("./src/test/resources/RemoveCmd/remove_dir_nopwd_notexists.txt");
        Assertions.assertTrue(outputStreamCaptor.toString().contains("Dir name not found for removal:"));
        Assertions.assertFalse(inputService.cmdService.contactList.isEmpty());
    }


    @Test
    @Disabled //TODO
    void removeCmdRightDirExistsNoPwdShouldOK() {
        inputService.init("./src/test/resources/RemoveCmd/remove_dir_exists_nopwd_ok.txt");
        Assertions.assertTrue(outputStreamCaptor.toString().contains("Dir name not found for removal:"));
        Assertions.assertFalse(inputService.cmdService.contactList.isEmpty());
    }

    @Test
    void removeCmdRightDirExistsWrongPwdShouldFail() {
        inputService.init("./src/test/resources/RemoveCmd/remove_dir_exists_wrongpwd_fail.txt");
        Assertions.assertTrue(outputStreamCaptor.toString().contains("pwd not right for removing given dirname"));
        Assertions.assertFalse(inputService.cmdService.contactList.isEmpty());
    }
    @Test
    void removeCmdRightDirExistsRightPwdWrongContactShouldFail() {
        inputService.init("./src/test/resources/RemoveCmd/remove_dir_exists_rightwd_ok.txt");
        Assertions.assertTrue(outputStreamCaptor.toString().contains("contact not found!"));
        Assertions.assertFalse(inputService.cmdService.contactList.isEmpty());
    }
    @Test
    @Disabled //TODO
    void removeCmdRightDirExistsRightPwdShouldOK() {
        inputService.init("./src/test/resources/RemoveCmd/remove_dir_exists_rightwd_ok.txt");
        Assertions.assertTrue(outputStreamCaptor.toString().contains("Dir name not found for removal:"));
        Assertions.assertFalse(inputService.cmdService.contactList.isEmpty());
    }

}
