package com.hoquocvung.gamelivewallpaper.Entity;

import java.util.ArrayList;

/**
 * Created by vungho on 17/12/2016.
 */

public class GameInfo {
    private String id;
    private String name;
    private String icon;
    private ArrayList<WallpaperInfo> listWallpaper;

    public GameInfo(String id, String name, String icon) {
        this.id = id;
        this.name = name;
        this.icon = icon;
        this.listWallpaper = new ArrayList<WallpaperInfo>();
    }

    public GameInfo(String id) {
        this(id, null, null);
    }

    public GameInfo() {
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

    public boolean addWallpaper(WallpaperInfo wallpaperInfo){
        if (!listWallpaper.contains(wallpaperInfo) && wallpaperInfo != null)
            return listWallpaper.add(wallpaperInfo);
        else
            return false;
    }
    public boolean addWallpaperOff(WallpaperInfo wallpaperInfo){
            return listWallpaper.add(wallpaperInfo);
    }

    public ArrayList<WallpaperInfo> getListWallpaper() {
        return listWallpaper;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof GameInfo)) return false;

        GameInfo gameInfo = (GameInfo) o;

        return getId() != null ? getId().equals(gameInfo.getId()) : gameInfo.getId() == null;

    }

    @Override
    public int hashCode() {
        return getId() != null ? getId().hashCode() : 0;
    }

    @Override
    public String toString() {
        return "GameInfo{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", icon='" + icon + '\'' +
                ", listWallpaper=" + listWallpaper +
                '}';
    }
}
