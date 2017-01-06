package com.hoquocvung.gamelivewallpaper.Entity;

/**
 * Created by vungho on 13/12/2016.
 */

public class MenuInfo {
    private String id;
    private String name;
    private String icon;

    public MenuInfo(String id, String name, String icon) {
        this.id = id;
        this.name = name;
        this.icon = icon;
    }

    public MenuInfo(){
        this(null, null, null);
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MenuInfo)) return false;

        MenuInfo menuInfo = (MenuInfo) o;

        return getId() != null ? getId().equals(menuInfo.getId()) : menuInfo.getId() == null;

    }

    @Override
    public int hashCode() {
        return getId() != null ? getId().hashCode() : 0;
    }

    @Override
    public String toString() {
        return "MenuInfo{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", icon='" + icon + '\'' +
                '}';
    }
}
