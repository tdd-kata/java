package com.demo.serializer;

public class DemoDto {

    private String id;

    private String name;

    private DemoSubDto children;

    public DemoDto() {
    }

    public DemoDto(String id, String name, DemoSubDto children) {
        this.id = id;
        this.name = name;
        this.children = children;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public DemoSubDto getChildren() {
        return children;
    }
}
