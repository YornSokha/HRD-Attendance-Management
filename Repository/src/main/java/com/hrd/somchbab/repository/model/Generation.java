package com.hrd.somchbab.repository.model;

import javax.validation.constraints.NotEmpty;



public class Generation {
    private int id;
    @NotEmpty
    private String name;
    private Academic academic;
    private boolean status;
    private boolean deleted;

    public Generation() {
    }

    public Generation(int id, String name, Academic academic, boolean status,boolean deleted) {
        this.id = id;
        this.name = name;
        this.academic = academic;
        this.status = status;
        this.deleted = deleted;
    }

    public Generation(Academic academic){
        this.academic = academic;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Academic getAcademic() {
        return academic;
    }

    public void setAcademic(Academic academic) {
        this.academic = academic;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    @Override
    public String toString() {
        return "Generation{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", academic=" + academic +
                ", status=" + status +
                ", deleted=" + deleted +
                '}';
    }
}
