package com.upm.svv.service;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.upm.svv.model.Command;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class InputService {
    public final CmdService cmdService;

    public void init(String file) {
        System.out.println("At first...");
        System.out.println("--------");
        System.out.println("Input file is defined as a text file (.txt only). Lower and upper case is relevant, for instance,\n"
                + "the name “Rogers” is not the same as “rogers”. One space separates each parameter.");
        System.out.println("--------");
        System.out.println("As you can see, the input is a .txt text file (not plain text but json, which is also text ;) ) with case sensitive decoding. Also, "
                + "each parameter is separated by a space!!");
        try {
            System.out.println("Using file: " + file);
            String json = Files.readAllLines(Paths.get(file)).stream().map(String::valueOf).collect(Collectors.joining(""));

            System.out.println("got json " + json);
            Command[] commands = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false).readValue(json, Command[].class);

            System.out.println("parsed commands " + Arrays.toString(commands));
            for (Command cmd : commands) {
                this.executeCommand(cmd);
            }
        } catch (Exception e) {
            System.out.println("Could not read file " + e.getMessage());
        }
    }

    public void executeCommand(Command cmd) {
        System.out.println("-------- BEGIN COMMAND " + cmd.getCmd() + " -----------");
        if (cmd.getCmd() == null || cmd.getCmd().isBlank()) {
            System.out.println("You returning null command! Be a nice person and don't try to catch 'em all ;) ");
            System.out.println(
                    "Found a bug in the code? Huh! Report it, we are prideful of this :)). You could try to put an hydrogen bomb over the code, maybe then it would fail. But I'd rather say it won't ");
            return;
        }
        switch (cmd.getCmd()) {
            case "help" -> cmdService.help();
            case "create" -> cmdService.create(cmd.getDirName());
            case "destroy" -> cmdService.destroy(cmd.getDirName());
            case "lock" -> cmdService.lock(cmd.getDirName(), cmd.getPwd());
            case "unlock" -> cmdService.unlock(cmd.getDirName(), cmd.getPwd());
            case "add" -> cmdService.add(cmd.getDirName(), cmd.getPwd(), cmd.getContact());
            case "remove" -> cmdService.remove(cmd.getDirName(), cmd.getPwd(), cmd.getContact());
            case "modify" -> cmdService.modify(cmd.getDirName(), cmd.getPwd(), cmd.getContact());
            case "search" -> cmdService.search(cmd.getPattern(), cmd.getDirName(), cmd.getPwd());
            case "reset" -> cmdService.reset();
            case "list" -> cmdService.list();
            case "display" -> cmdService.display(cmd.getDirName(), cmd.getPwd());
            default -> System.out.println("Unrecognized command: " + cmd);
        }
        System.out.println("-------- END COMMAND " + cmd.getCmd() + " -----------");
    }
}
