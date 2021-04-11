package com.northerly.myfragmentsapp.Model.PojoClass;

public class MyDataSet {

    private String name;

    public MyDataSet(String name, String job) {
        this.name = name;
        this.job = job;
    }

    private String job;

    private String id;

    private String createdAt;

    public void setName(String name){
        this.name = name;
    }
    public String getName(){
        return this.name;
    }
    public void setJob(String job){
        this.job = job;
    }
    public String getJob(){
        return this.job;
    }
    public void setId(String id){
        this.id = id;
    }
    public String getId(){
        return this.id;
    }
    public void setCreatedAt(String createdAt){
        this.createdAt = createdAt;
    }
    public String getCreatedAt(){
        return this.createdAt;
    }
}
