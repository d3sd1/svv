package com.upm.svv;

import com.upm.svv.service.InputService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.DefaultApplicationArguments;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SvvcApplicationTests {

    @Autowired
    private InputService inputService;
    @Test
    void badFileInput() {
        inputService.run(new DefaultApplicationArguments("badfile_l"));
        Assertions.assertTrue(true);
    }

    @Test
    void goodFileInput() {
        inputService.run(new DefaultApplicationArguments("./src/test/resources/input_file.txt"));
        Assertions.assertTrue(true);
    }

}
