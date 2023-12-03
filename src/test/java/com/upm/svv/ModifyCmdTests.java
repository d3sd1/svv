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
class ModifyCmdTests {
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
    void modifyCmdInvalidPathEmpty() {
        inputService.init("./src/test/resources/ModifyCmd/empty_path.txt");
        Assertions.assertTrue(outputStreamCaptor.toString().contains("the directory does not exist or it has no pwd"));
        Assertions.assertTrue(inputService.cmdService.contactList.isEmpty());
    }
    @Test
    void modifyCmdInvalidPathNull() {
        inputService.init("./src/test/resources/ModifyCmd/null_path.txt");
        Assertions.assertTrue(outputStreamCaptor.toString().contains("the directory does not exist or it has no pwd"));
        Assertions.assertTrue(inputService.cmdService.contactList.isEmpty());
    }

    @Test
    void modifyCmdInvalidPathNotFound() {
        inputService.init("./src/test/resources/ModifyCmd/not_found_path.txt");
        Assertions.assertTrue(outputStreamCaptor.toString().contains("Dir name not found for modify: notvaliD"));
        Assertions.assertTrue(inputService.cmdService.contactList.isEmpty());
    }

    @Test
    void modifyCmdRightPathNoContacts() {
        inputService.init("./src/test/resources/ModifyCmd/right_path_no_contacts.txt");
        Assertions.assertTrue(outputStreamCaptor.toString().contains("contact not found!"));
        Assertions.assertFalse(inputService.cmdService.contactList.isEmpty());
    }
    @Test
    void modifyCmdRightPathWithContactsNoChanges() {
        inputService.init("./src/test/resources/ModifyCmd/right_path_with_valid_contact.txt");
        Assertions.assertTrue(outputStreamCaptor.toString().contains("contact found! begin modifications (if needed)"));
        Assertions.assertFalse(inputService.cmdService.contactList.isEmpty());
    }
    @Test
    void modifyCmdRightPathWithInvalidContactName() {
        inputService.init("./src/test/resources/ModifyCmd/right_path_with_invalid_contact_name.txt");
        Assertions.assertTrue(outputStreamCaptor.toString().contains("contact not found!"));
        Assertions.assertFalse(inputService.cmdService.contactList.isEmpty());
    }
    @Test
    void modifyCmdRightPathWithInvalidContactSurname() {
        inputService.init("./src/test/resources/ModifyCmd/right_path_with_invalid_contact_surname.txt");
        Assertions.assertTrue(outputStreamCaptor.toString().contains("contact not found!"));
        Assertions.assertFalse(inputService.cmdService.contactList.isEmpty());
    }
    @Test
    void modifyCmdRightPathWithContactChangAddTelLessThan3() {
        inputService.init("./src/test/resources/ModifyCmd/right_path_with_valid_contact_addtel_less3.txt");
        Assertions.assertTrue(outputStreamCaptor.toString().contains("Added tel!"));
        Assertions.assertFalse(inputService.cmdService.contactList.isEmpty());
    }
    @Test
    void modifyCmdRightPathWithContactChangAddTelMoreThan3() {
        inputService.init("./src/test/resources/ModifyCmd/right_path_with_valid_contact_addtel_more3.txt");
        Assertions.assertTrue(outputStreamCaptor.toString().contains("reached limit for adding tel"));
        Assertions.assertFalse(inputService.cmdService.contactList.isEmpty());
    }
    @Test
    void modifyCmdRightPathWithContactChangAddEmailLessThan3() {
        inputService.init("./src/test/resources/ModifyCmd/right_path_with_valid_contact_addemail_less3.txt");
        Assertions.assertTrue(outputStreamCaptor.toString().contains("Added email!"));
        Assertions.assertFalse(inputService.cmdService.contactList.isEmpty());
    }
    @Test
    void modifyCmdRightPathWithContactChangAddEmailMoreThan3() {
        inputService.init("./src/test/resources/ModifyCmd/right_path_with_valid_contact_addemail_more3.txt");
        Assertions.assertTrue(outputStreamCaptor.toString().contains("reached limit for adding email"));
        Assertions.assertFalse(inputService.cmdService.contactList.isEmpty());
    }
    @Test
    void modifyCmdRightPathWithContactChangAddAddrLessThan3() {
        inputService.init("./src/test/resources/ModifyCmd/right_path_with_valid_contact_addaddr_less3.txt");
        Assertions.assertTrue(outputStreamCaptor.toString().contains("Added addr!"));
        Assertions.assertFalse(inputService.cmdService.contactList.isEmpty());
    }
    @Test
    void modifyCmdRightPathWithContactChangAddAddrMoreThan3() {
        inputService.init("./src/test/resources/ModifyCmd/right_path_with_valid_contact_addaddr_more3.txt");
        Assertions.assertTrue(outputStreamCaptor.toString().contains("reached limit for adding addr"));
        Assertions.assertFalse(inputService.cmdService.contactList.isEmpty());
    }
    @Test
    void modifyCmdRightPathWithContactChangNickAddrLessThan3() {
        inputService.init("./src/test/resources/ModifyCmd/right_path_with_valid_contact_addnick_less3.txt");
        Assertions.assertTrue(outputStreamCaptor.toString().contains("Added nick!"));
        Assertions.assertFalse(inputService.cmdService.contactList.isEmpty());
    }
    @Test
    void modifyCmdRightPathWithContactChangNickAddrMoreThan3() {
        inputService.init("./src/test/resources/ModifyCmd/right_path_with_valid_contact_addnick_more3.txt");
        Assertions.assertTrue(outputStreamCaptor.toString().contains("reached limit for adding nick"));
        Assertions.assertFalse(inputService.cmdService.contactList.isEmpty());
    }
    @Test
    void modifyCmdRightPathWithContactChangDelTelLessThan3() {
        inputService.init("./src/test/resources/ModifyCmd/right_path_with_valid_contact_deltel_less3.txt");
        Assertions.assertTrue(outputStreamCaptor.toString().contains("Deleted tel!"));
        Assertions.assertFalse(inputService.cmdService.contactList.isEmpty());
    }
    @Test
    void modifyCmdRightPathWithContactChangDelTelMoreThan3() {
        inputService.init("./src/test/resources/ModifyCmd/right_path_with_valid_contact_deltel_more3.txt");
        Assertions.assertTrue(outputStreamCaptor.toString().contains("tel was not found for deleting"));
        Assertions.assertFalse(inputService.cmdService.contactList.isEmpty());
    }
    @Test
    void modifyCmdRightPathWithContactChangDelEmailLessThan3() {
        inputService.init("./src/test/resources/ModifyCmd/right_path_with_valid_contact_delemail_less3.txt");
        Assertions.assertTrue(outputStreamCaptor.toString().contains("Deleted email!"));
        Assertions.assertFalse(inputService.cmdService.contactList.isEmpty());
    }
    @Test
    void modifyCmdRightPathWithContactChangDelEmailMoreThan3() {
        inputService.init("./src/test/resources/ModifyCmd/right_path_with_valid_contact_delemail_more3.txt");
        Assertions.assertTrue(outputStreamCaptor.toString().contains("email was not found for deleting"));
        Assertions.assertFalse(inputService.cmdService.contactList.isEmpty());
    }
    @Test
    void modifyCmdRightPathWithContactChangDelAddrrLessThan3() {
        inputService.init("./src/test/resources/ModifyCmd/right_path_with_valid_contact_deladdr_less3.txt");
        Assertions.assertTrue(outputStreamCaptor.toString().contains("Deleted addr!"));
        Assertions.assertFalse(inputService.cmdService.contactList.isEmpty());
    }
    @Test
    void modifyCmdRightPathWithContactChangDelAddrrMoreThan3() {
        inputService.init("./src/test/resources/ModifyCmd/right_path_with_valid_contact_deladdr_more3.txt");
        Assertions.assertTrue(outputStreamCaptor.toString().contains("addr was not found for deleting"));
        Assertions.assertFalse(inputService.cmdService.contactList.isEmpty());
    }
    @Test
    void modifyCmdRightPathWithContactChangNickDelrLessThan3() {
        inputService.init("./src/test/resources/ModifyCmd/right_path_with_valid_contact_delnick_less3.txt");
        Assertions.assertTrue(outputStreamCaptor.toString().contains("Deleted nick!"));
        Assertions.assertFalse(inputService.cmdService.contactList.isEmpty());
    }
    @Test
    void modifyCmdRightPathWithContactChangNickDelrMoreThan3() {
        inputService.init("./src/test/resources/ModifyCmd/right_path_with_valid_contact_delnick_more3.txt");
        Assertions.assertTrue(outputStreamCaptor.toString().contains("nick was not found for deleting"));
        Assertions.assertFalse(inputService.cmdService.contactList.isEmpty());
    }
    @Test
    void modifyCmdRightPathWithContactInvalidPwd() {
        inputService.init("./src/test/resources/ModifyCmd/right_path_with_valid_pwd.txt");
        Assertions.assertTrue(outputStreamCaptor.toString().contains("nick was not found for deleting"));
        Assertions.assertFalse(inputService.cmdService.contactList.isEmpty());
    }
    @Test
    void modifyCmdRightPathWithContactValidPwd() {
        inputService.init("./src/test/resources/ModifyCmd/right_path_with_invalid_pwd.txt");
        Assertions.assertTrue(outputStreamCaptor.toString().contains("nick was not found for deleting"));
        Assertions.assertFalse(inputService.cmdService.contactList.isEmpty());
    }
}
