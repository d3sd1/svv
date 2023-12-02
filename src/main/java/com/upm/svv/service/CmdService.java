package com.upm.svv.service;

import com.upm.svv.model.Address;
import com.upm.svv.model.Contact;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class CmdService {
    private final HashMap<String, Address> contactList = new HashMap<>();

    public void help() {
        System.out.println(
                "- help\n" + "Help with no arguments. It should print all the possible commands the user can do\n" + "and nothing else is done.\n" + "- create <directory name>\n"
                        + "Create a new directory with the name provided in the parameter. If the name already\n"
                        + "exists, the program should display it saying the directory already exists and nothing\n" + "has been done.\n" + "- destroy <directory name>\n"
                        + "Delete the directory <directory name>. If the directory does not exist, a message\n"
                        + "should say that the directory does not exist so nothing has been done.\n" + "- lock <directory name> <password>\n"
                        + "Enable the user to “secure” their directory (we do not care about the level of security\n"
                        + "here since the password is in plain text). Everytime the user wants to access a\n"
                        + "secured directory they must enter the password right after the directory name (this is\n"
                        + "mentioned when needed like this: [<password>]). If the directory already has a\n"
                        + "password, an error should be raised or if the specified directory does not exist.\n" + "- unlock <directory name> <password>\n"
                        + "Enable the user to remove the password. If the specified directory does not exist then\n"
                        + "an error should be shown to the user. If the password is incorrect, the program\n" + "should alert the user.\n"
                        + "- add <directory name> [<password>] <surname> <name>\n" + "[tel=<telephone number>]* [email=<email address>]*\n"
                        + "[addr=<address>]* [nick=<nickname>]*\n" + "Create a new contact in the specified directory with the name and surname and other\n"
                        + "parameters specified. Two contacts in the same directory cannot be exactly the\n"
                        + "same, which means they must have a different name or/and surname as mentioned\n"
                        + "before. If the contact already exists, the program should alert the user. If more than 3\n" + "parameters for each field it should raise an error.\n"
                        + "- remove <directory name> [<password>] <surname> <name>\n" + "Delete the contact <surname> <name> in the <directory name>. If the directory or\n"
                        + "the contact does not exist, it should alert the user.\n" + "- modify <directory name> [<password>] <surname> <name>\n"
                        + "[add_tel=<telephone number>]* [del_tel=<telephone number>]*\n" + "[add_email=<email address>]* [del_email=<email address>]*\n"
                        + "[add_addr=<address>]* [del_addr=<address>]*\n" + "[add_nick=<nickname>]* [del_nick=<nickname>]*\n"
                        + "Modify a contact that already exists, otherwise a message should appear. If you want\n"
                        + "to add a new parameter you need to put the prefix “add_” before the parameter\n"
                        + "name. If you want to remove a parameter you need to put the prefix “del_” before the\n"
                        + "parameter name (in order not to make mistakes and delete something not wanted).\n"
                        + "The user has to specify the parameter and the value of the parameter they want to\n"
                        + "delete. If there are more than 3 values for one field the program should raise an error\n" + "or if the value of one field to be deleted is incorrect.\n"
                        + "- search <pattern> [<directory name> [<password>]]\n" + "Search in <directory name> the <pattern> specified. If the directory is not found, it\n"
                        + "should display an error. If there is no <directory name> specified then the program\n"
                        + "should look for the <pattern> in all the directories created by the user. In that case, it\n"
                        + "should print the contact(s) with all its(their) fields and its(their) directory. If the\n"
                        + "directory is specified, the program should only print the contact(s) with all its(their)\n"
                        + "fields. If nothing matches the <pattern>, the system should print to the user nothing\n" + "was found.\n" + "- reset\n"
                        + "Remove all directories without asking for a confirmation. If there is nothing to remove\n"
                        + "it should display there is nothing to remove. If there was something to remove, it\n" + "should display on success that everything was removed.\n"
                        + "- list\n" + "Show only the names of the existent directories. If there are no directories, the\n" + "program should print it.\n"
                        + "- display [<directory name> [<password>]]*\n" + "If nothing is specified, every directory is displayed, except for those protected by a\n"
                        + "password (there must be a message saying this directory is locked). You can display\n"
                        + "one, two, three… directories as long as their names are specified (and it is needed to\n"
                        + "add the password if it is protected). If one of the directories was not found, an error\n"
                        + "message should appear saying the directory specified does not exist. Even if an error\n"
                        + "occurs, the program should continue to display the rest of the directories asked.\n");
    }

    public void create(String dirName) {
        if (this.contactList.containsKey(dirName)) {
            System.out.println("The directory already exists and nothing has been done");
        } else {
            System.out.println("The directory was created");
            this.contactList.put(dirName, Address.builder().path(dirName).contacts(new ArrayList<>()).build());
        }
    }

    public void destroy(String dirName) {
        if (!this.contactList.containsKey(dirName)) {
            System.out.println("the directory does not exist so nothing has been done");
        } else {
            System.out.println("The directory was destroyed. Specifications length is excesive, isn't it? Good luck with black box.");
            this.contactList.remove(dirName);
        }
    }

    public void lock(String dirName, String pwd) {
        if (!this.contactList.containsKey(dirName) || this.contactList.get(dirName).getPwd() != null) {
            System.out.println("the directory does not exist or it already has a pwd");
        } else {
            System.out.println("The directory was protected");
            this.contactList.get(dirName).setPwd(pwd);
        }
    }

    public void unlock(String dirName, String pwd) {
        if (!this.contactList.containsKey(dirName) || this.contactList.get(dirName).getPwd() == null) {
            System.out.println("the directory does not exist or it has no pwd");
        } else if (this.contactList.get(dirName).getPwd().equals(pwd)) {
            System.out.println("The directory was unprotected");
        } else {
            System.out.println("Invalid password");
        }
    }

    public void reset() {
        if (this.contactList.isEmpty()) {
            System.out.println("Contacts are empty!");
        } else {
            this.contactList.clear();
            System.out.println("All directories removed");
        }
    }

    public void list() {
        if (this.contactList.isEmpty()) {
            System.out.println("Contacts are empty!");
        } else {
            for (Map.Entry<String, Address> contactMap : this.contactList.entrySet()) {
                System.out.println(contactMap.getKey());
            }
        }
    }

    public void add(String dirName, String pwd, Contact contact) {
        if(this.contactList.containsKey(dirName)) {
            Address a = this.contactList.get(dirName);

            if(!a.getPwd().equals(pwd)) {
                System.out.println("pwd not right for adding contact to given dirname");
                return;
            }
            if(a.getContacts().stream().anyMatch(a2 -> a2.getName().equals(contact.getName()) && a2.getSurname().equals(contact.getSurname()))) {
                System.out.println("Can't add contact - it already exists");
            } else {
                System.out.println("contact not found!");
            }
        } else {
            System.out.println("Dir name not found for removal: " + dirName);
        }
    }

    public void remove(String dirName, String pwd, Contact contact) {
        if(this.contactList.containsKey(dirName)) {
            Address a = this.contactList.get(dirName);

            if(!a.getPwd().equals(pwd)) {
                System.out.println("pwd not right for removing given dirname");
                return;
            }
            if(a.getContacts().stream().anyMatch(a2 -> a2.getName().equals(contact.getName()) && a2.getSurname().equals(contact.getSurname()))) {
                a.getContacts().removeIf(a2 -> a2.getName().equals(contact.getName()) && a2.getSurname().equals(contact.getSurname()));
                System.out.println("Contact deleted successfully! ");
            } else {
                System.out.println("contact not found!");
            }
        } else {
            System.out.println("Dir name not found for removal: " + dirName);
        }
    }

    public void modify(String dirName, String pwd, Contact contact) {
        if(this.contactList.containsKey(dirName)) {
            Address a = this.contactList.get(dirName);

            if(!a.getPwd().equals(pwd)) {
                System.out.println("pwd not right for modifying given dirname");
                return;
            }
            Optional<Contact> cOpt = a.getContacts().stream().filter(c1 -> c1.getName().equals(contact.getName()) && c1.getSurname().equals(contact.getSurname())).findFirst();
            if(cOpt.isPresent()) {
                Contact c = cOpt.get();
                if(contact.getAdd_tel() != null) {
                    if(c.getTel().size() >= 3) {
                        System.out.println("reached limit for adding tel");
                    } else {
                        c.getTel().add(contact.getAdd_tel());
                    }
                }
                if(contact.getAdd_email() != null) {
                    if(c.getEmail().size() >= 3) {
                        System.out.println("reached limit for adding email");
                    } else {
                        c.getEmail().add(contact.getAdd_email());
                    }
                }
                if(contact.getAdd_addr() != null) {
                    if(c.getAddr().size() >= 3) {
                        System.out.println("reached limit for adding addr");
                    } else {
                        c.getAddr().add(contact.getAdd_addr());
                    }
                }
                if(contact.getAdd_nick() != null) {
                    if(c.getNick().size() >= 3) {
                        System.out.println("reached limit for adding nick");
                    } else {
                        c.getNick().add(contact.getAdd_nick());
                    }
                }
                if(contact.getDel_tel() != null) {
                    if(c.getTel().contains(contact.getDel_tel())) {
                        System.out.println("tel was not found for deleting");
                    } else {
                        c.getTel().remove(contact.getDel_tel());
                    }
                }
                if(contact.getDel_addr() != null) {
                    if(c.getAddr().contains(contact.getDel_addr())) {
                        System.out.println("addr was not found for deleting");
                    } else {
                        c.getAddr().remove(contact.getDel_addr());
                    }
                }
                if(contact.getDel_email() != null) {
                    if(c.getEmail().contains(contact.getDel_email())) {
                        System.out.println("email was not found for deleting");
                    } else {
                        c.getEmail().remove(contact.getDel_email());
                    }
                }
                if(contact.getDel_nick() != null) {
                    if(c.getNick().contains(contact.getDel_nick())) {
                        System.out.println("nick was not found for deleting");
                    } else {
                        c.getNick().remove(contact.getDel_nick());
                    }
                }
            } else {
                System.out.println("contact not found!");
            }
        } else {
            System.out.println("Dir name not found for modify: " + dirName);
        }
    }

    public void search(String pattern, String dirName, String pwd) {
        if (dirName == null || dirName.isEmpty()) {
            for (Address a : this.contactList.values()) {
                for (Contact c : a.getContacts()) {
                    if (c.toString().contains(pattern)) {
                        System.out.println("Found coincidence on dir_name " + dirName + " on contact " + c);
                    }
                }
            }
        } else if (this.contactList.containsKey(dirName)) {
            Address a = this.contactList.get(dirName);
            if(!a.getPwd().equals(pwd)) {
                System.out.println("pwd not right for modifying given dirname");
                return;
            }
            for (Contact c : this.contactList.get(dirName).getContacts()) {
                if (c.toString().contains(pattern)) {
                    System.out.println("Found coincidence on dir_name " + dirName + " on contact " + c);
                }
            }
        } else {
            System.out.println("No coincidences or either dir was not found.");
        }
    }

    public void display(String dirName, String pwd) {
        if (this.contactList.isEmpty()) {
            System.out.println("Contacts are empty!");
        } else if (dirName == null) {
            for (Map.Entry<String, Address> contactMap : this.contactList.entrySet()) {
                if (contactMap.getValue().getPwd() != null) {
                    System.out.println(contactMap.getKey() + "is protected by pwd!");
                } else {
                    System.out.println(contactMap.getKey() + " : " + contactMap.getValue());
                }
            }
        } else {
            if (!this.contactList.containsKey(dirName)) {
                System.out.println("Invalid dir: " + dirName);
            } else if (!this.contactList.get(dirName).getPwd().equals(pwd)) {
                System.out.println("Invalid password for dir " + dirName);
            } else {
                System.out.println(dirName + " : " + this.contactList.get(dirName));
            }
        }
    }
}
