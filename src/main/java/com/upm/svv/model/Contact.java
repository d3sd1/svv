package com.upm.svv.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Data
@ToString
@JsonIgnoreProperties
public class Contact {
    private String password = null;
    private String surname = null;
    private String name = null;
    private List<String> tel = new ArrayList<>();
    private List<String> email = new ArrayList<>();
    private List<String> addr = new ArrayList<>();
    private List<String> nick = new ArrayList<>();
    private String add_tel = null;
    private String del_tel = null;
    private String add_email = null;
    private String del_email = null;
    private String add_addr = null;
    private String del_addr = null;
    private String add_nick = null;
    private String del_nick = null;
}
