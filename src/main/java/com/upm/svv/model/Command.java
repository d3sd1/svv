package com.upm.svv.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@JsonIgnoreProperties
public class Command {
    private String cmd = null;
    private String dirName = null;
    private String pattern = null;
    private String pwd = null;
    private Contact contact = null;
}
