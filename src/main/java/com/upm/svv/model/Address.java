package com.upm.svv.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
@Builder
@JsonIgnoreProperties
public class Address {
    private String path;
    private String pwd;
    private List<Contact> contacts;
}
