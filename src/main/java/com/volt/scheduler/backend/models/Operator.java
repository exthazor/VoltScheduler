package com.volt.scheduler.backend.models;

public class Operator {

    private Long id;

    private String agentName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return agentName;
    }

    public void setName(String name) {
        this.agentName = name;
    }

}
