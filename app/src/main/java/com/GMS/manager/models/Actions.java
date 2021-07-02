package com.GMS.manager.models;

public class Actions {
    private String neighborhoodName;
    private String representativeName;
    private String agentName;
    private String date;
    private boolean ActionState;

    public String getNeighborhoodName() {
        return neighborhoodName;
    }

    public void setNeighborhoodName(String neighborhoodName) {
        this.neighborhoodName = neighborhoodName;
    }

    public String getRepresentativeName() {
        return representativeName;
    }

    public void setRepresentativeName(String representativeName) {
        this.representativeName = representativeName;
    }

    public String getAgentName() {
        return agentName;
    }

    public void setAgentName(String agentName) {
        this.agentName = agentName;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public boolean isActionState() {
        return ActionState;
    }

    public void setActionState(boolean actionState) {
        ActionState = actionState;
    }
}
