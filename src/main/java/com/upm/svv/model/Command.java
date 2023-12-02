package com.upm.svv.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.ToString;

import java.util.ArrayList;

@Data
@ToString
@JsonIgnoreProperties
public class Command {
    private String cmd= null;
    private String dirName = null;
    private String pattern = null;
    private String pwd = null;
    private Contact contact = null;
}
