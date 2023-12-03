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
    void searchCmdEmptyDirname() {
        inputService.init("./src/test/resources/SearchCmd/empty_dirname.txt");
        Assertions.assertTrue(outputStreamCaptor.toString().contains("No coincidences or either dir was not found."));
        Assertions.assertTrue(inputService.cmdService.contactList.isEmpty());
    }
    @Test
    void searchCmdNullDirname() {
        inputService.init("./src/test/resources/SearchCmd/null_pattern.txt");
        Assertions.assertTrue(outputStreamCaptor.toString().contains("Contactlist is empty!"));
        Assertions.assertTrue(inputService.cmdService.contactList.isEmpty());
    }
    @Test
    void searchCmdNullDirnameWithAddresses() {
        inputService.init("./src/test/resources/SearchCmd/null_pattern_width_addresses.txt");
        Assertions.assertTrue(outputStreamCaptor.toString().contains("Full list on dir_name testme on contact Contact(password=null, surname=Jones3, name=Paco2, tel=[], email=[], addr=[], nick=[], add_tel=null, del_tel=null, add_email=null, del_email=null, add_addr=null, del_addr=null, add_nick=null, del_nick=null)"));
        Assertions.assertFalse(inputService.cmdService.contactList.isEmpty());
    }
    @Test
    void searchCmdNullPatternWithDirNameWithAddressesInvalidPwd() {
        inputService.init("./src/test/resources/SearchCmd/null_pattern_width_dir_addresses.txt");
        Assertions.assertTrue(outputStreamCaptor.toString().contains("pwd not right for modifying given dirname"));
        Assertions.assertFalse(inputService.cmdService.contactList.isEmpty());
    }
    @Test
    void searchCmdNullPatternWithDirNameWithAddressesNoPwd() {
        inputService.init("./src/test/resources/SearchCmd/null_pattern_width_dir_addresses_no_pwd.txt");
        Assertions.assertTrue(outputStreamCaptor.toString().contains("Given pattern is null. Can't search."));
        Assertions.assertFalse(inputService.cmdService.contactList.isEmpty());
    }

    @Test
    void searchCmdPatternWithDirNameWithAddressesNoPwd() {
        inputService.init("./src/test/resources/SearchCmd/pattern_width_dir_addresses.txt");
        Assertions.assertTrue(outputStreamCaptor.toString().contains("Found coincidence on dir_name testme on contact Contact(password=null, surname=Jones3, name=Paco2, tel=[], email=[], addr=[], nick=[], add_tel=null, del_tel=null, add_email=null, del_email=null, add_addr=null, del_addr=null, add_nick=null, del_nick=null)"));
        Assertions.assertFalse(inputService.cmdService.contactList.isEmpty());
    }

    @Test
    void searchCmdPatternWithDirNameNoCoincidences() {
        inputService.init("./src/test/resources/SearchCmd/pattern_not_found_width_dir_addresses.txt");
        Assertions.assertTrue(outputStreamCaptor.toString().contains("This was not a coincidence."));
        Assertions.assertFalse(inputService.cmdService.contactList.isEmpty());
    }
    @Test
    void searchCmdPatternWithDirInvalidPwd() {
        inputService.init("./src/test/resources/SearchCmd/null_pattern_width_dir_addresses_invalid_pwd.txt");
        Assertions.assertTrue(outputStreamCaptor.toString().contains("pwd not right for modifying given dirname"));
        Assertions.assertFalse(inputService.cmdService.contactList.isEmpty());
    }
    @Test
    void searchCmdPatternWithDirValidPwd() {
        inputService.init("./src/test/resources/SearchCmd/null_pattern_width_dir_addresses_valid_pwd.txt");
        Assertions.assertTrue(outputStreamCaptor.toString().contains("Given pattern is null. Can't search."));
        Assertions.assertFalse(inputService.cmdService.contactList.isEmpty());
    }

}
