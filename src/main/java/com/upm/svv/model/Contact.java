package com.upm.svv.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
@JsonIgnoreProperties
public class Contact {
    private String password = null;
    private String surname = null;
    private String name = null;
    private String tel = null;
    private String email = null;
    private String addr = null;
    private String nick = null;
    private String add_tel = null;
    private String del_tel = null;
    private String add_email = null;
    private String del_email = null;
    private String add_addr = null;
    private String del_addr = null;
    private String add_nick = null;
    private String del_nick = null;
}
