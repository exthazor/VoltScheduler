package com.volt.scheduler.backend.models;

public class Operator {

    private Long operatorId;

    private String agentName;

    public Long getId() {
        return operatorId;
    }

    public void setOperatorId(Long operatorId) {
        this.operatorId = operatorId;
    }

    public String getName() {
        return agentName;
    }

    public void setName(String name) {
        this.agentName = name;
    }

}
