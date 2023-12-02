package com.upm.svv.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Data
@ToString
@Builder
@JsonIgnoreProperties
public class Address {
    private String path = null;
    private String pwd = null;
    private List<Contact> contacts = new ArrayList<>();
}
