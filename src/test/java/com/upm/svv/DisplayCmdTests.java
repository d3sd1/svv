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
class DisplayCmdTests {
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
    void displayCmdNoArgsNoPwd() {
        inputService.init("./src/test/resources/DisplayCmd/display_no_args_no_pwd.txt");
        Assertions.assertTrue(outputStreamCaptor.toString().contains("Addresses and contacts are empty!"));
        Assertions.assertTrue(inputService.cmdService.contactList.isEmpty());
    }

    @Test
    void displayCmdNoArgsWithPwd() {
        inputService.init("./src/test/resources/DisplayCmd/display_no_args_with_pwd.txt");
        Assertions.assertTrue(outputStreamCaptor.toString().contains("usemepath is protected by pwd!"));
        Assertions.assertFalse(inputService.cmdService.contactList.isEmpty());
        Assertions.assertFalse(inputService.cmdService.contactList.get("usemepath").getPwd().isEmpty());
    }

    @Test
    void displayCmdArgsRightPwdWithPwd() {
        inputService.init("./src/test/resources/DisplayCmd/display_args_with_file_no_pwd.txt");
        Assertions.assertTrue(outputStreamCaptor.toString().contains("usemepath : Address(path=usemepath, pwd=null, contacts=[]"));
        Assertions.assertFalse(inputService.cmdService.contactList.isEmpty());
        Assertions.assertNull(inputService.cmdService.contactList.get("usemepath").getPwd());
    }

    @Test
    void displayCmdArgsWithDirInvalidDirWithoutPwd() {
        inputService.init("./src/test/resources/DisplayCmd/display_args_with_dir_invalid_dir.txt");
        Assertions.assertTrue(outputStreamCaptor.toString().contains("Invalid dir: invalid_path"));
        Assertions.assertFalse(inputService.cmdService.contactList.isEmpty());
        Assertions.assertFalse(inputService.cmdService.contactList.containsKey("invalid_path"));
    }

    @Test
    void displayCmdArgsWithDirValidDirWithoutPwd() {
        inputService.init("./src/test/resources/DisplayCmd/display_args_with_dir_valid_dir_no_pwd.txt");
        Assertions.assertTrue(outputStreamCaptor.toString().contains("usemepath : Address(path=usemepath, pwd=null, contacts=[])"));
        Assertions.assertFalse(inputService.cmdService.contactList.isEmpty());
        Assertions.assertTrue(inputService.cmdService.contactList.containsKey("usemepath"));
        Assertions.assertNull(inputService.cmdService.contactList.get("usemepath").getPwd());
    }

    @Test
    void displayCmdArgsWithDirValidDirWithWrongPwd() {
        inputService.init("./src/test/resources/DisplayCmd/display_args_with_dir_valid_dir_wrong_pwd.txt");
        Assertions.assertTrue(outputStreamCaptor.toString().contains("Invalid password for dir usemepath"));
        Assertions.assertFalse(inputService.cmdService.contactList.isEmpty());
        Assertions.assertEquals(inputService.cmdService.contactList.get("usemepath").getPwd(), "1234");
    }

    @Test
    void displayCmdArgsWithDirValidDirWithRightPwd() {
        inputService.init("./src/test/resources/DisplayCmd/display_args_with_dir_valid_dir_right_pwd.txt");
        Assertions.assertTrue(outputStreamCaptor.toString().contains("usemepath : Address(path=usemepath, pwd=1234, contacts=[])"));
        Assertions.assertFalse(inputService.cmdService.contactList.isEmpty());
        Assertions.assertTrue(inputService.cmdService.contactList.containsKey("usemepath"));
        Assertions.assertEquals(inputService.cmdService.contactList.get("usemepath").getPwd(), "1234");
    }

}
