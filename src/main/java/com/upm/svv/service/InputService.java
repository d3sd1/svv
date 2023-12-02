package com.upm.svv.service;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.upm.svv.model.Command;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Scanner;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class InputService  implements ApplicationRunner {
    private final CmdService cmdService;

    @Override
    public void run( ApplicationArguments args )
    {
        System.out.println("At first...");
        System.out.println("--------");
        System.out.println("Input file is defined as a text file (.txt only). Lower and upper case is relevant, for instance,\n"
                + "the name “Rogers” is not the same as “rogers”. One space separates each parameter.");
        System.out.println("--------");
        System.out.println("As you can see, the input is a .txt text file (not plain text but json, which is also text ;) ) with case sensitive decoding. Also, "
                + "each parameter is separated by a space!!");
        try {
            String file = args.getSourceArgs()[0];
            System.out.println("Using file: " + file);
            String json =  Files.readAllLines(Paths.get(file)).stream()
                                   .map(String::valueOf)
                                   .collect(Collectors.joining(""));

            System.out.println("got json " + json);
            Command[] commands = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false).readValue(json, Command[].class);

            System.out.println("parsed commands " + Arrays.toString(commands));
            for(Command cmd:commands) {
                this.executeCommand(cmd);
            }
        } catch (Exception e) {
            System.out.println("Could not read file " + e.getMessage());
        }
    }

    public void executeCommand(Command cmd) {
        System.out.println("-------- BEGIN COMMAND " + cmd.getCmd() + " -----------");
        if(cmd.getCmd() == null || cmd.getCmd().isBlank()) {
            System.out.println("You returning null command! Be a nice person and don't try to catch 'em all ;) ");
            System.out.println("Found a bug in the code? Huh! Report it, we are prideful of this :)). You could try to put an hydrogen bomb over the code, maybe then it would fail. But I'd rather say it won't ");
            return;
        }
        switch (cmd.getCmd()) {
            case "help":
                cmdService.help();
                break;
            case "create":
                cmdService.create(cmd.getDirName());
                break;
            case "destroy":
                cmdService.destroy(cmd.getDirName());
                break;
            case "lock":
                cmdService.lock(cmd.getDirName(), cmd.getPwd());
                break;
            case "unlock":
                cmdService.unlock(cmd.getDirName(), cmd.getPwd());
                break;
            case "add":
                cmdService.add(cmd.getDirName(), cmd.getPwd(), cmd.getContact());
                break;
            case "remove":
                cmdService.remove(cmd.getDirName(), cmd.getPwd(), cmd.getContact());
                break;
            case "modify":
                cmdService.modify(cmd.getDirName(), cmd.getPwd(), cmd.getContact());
                break;
            case "search":
                cmdService.search(cmd.getPattern(), cmd.getDirName(), cmd.getPwd());
                break;
            case "reset":
                cmdService.reset();
                break;
            case "list":
                cmdService.list();
                break;
            case "display":
                cmdService.display(cmd.getDirName(), cmd.getPwd());
                break;
                default:
                    System.out.println("Unrecognized command: " + cmd);
        }
        System.out.println("-------- END COMMAND " + cmd.getCmd() + " -----------");
    }
}
