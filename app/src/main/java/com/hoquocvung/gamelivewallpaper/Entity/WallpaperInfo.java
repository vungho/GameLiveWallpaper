package com.hoquocvung.gamelivewallpaper.Entity;

/**
 * Created by vungho on 17/12/2016.
 */

public class WallpaperInfo {
    private String parent;
    private String id;
    private String name;
    private String image;
    private String video;
    private boolean downloadState;
    private String fileUrl;

    public WallpaperInfo(String id, String name, String image, String video, String parent) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.video = video;
        this.downloadState = false;
        this.fileUrl = null;
        this.parent = parent;
    }

    public WallpaperInfo(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    public boolean isDownloadState() {
        return downloadState;
    }

    public void setDownloadState(boolean downloadState) {
        this.downloadState = downloadState;
    }

    public WallpaperInfo(String id, String parent) {
        this (id, null, null, null, parent);
    }

    public WallpaperInfo() {
        this (null, null, null, null, null);
    }

    public String getParent() {
        return parent;
    }

    public void setParent(String parent) {
        this.parent = parent;
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof WallpaperInfo)) return false;

        WallpaperInfo that = (WallpaperInfo) o;

        return getId() != null ? getId().equals(that.getId()) : that.getId() == null;

    }

    @Override
    public int hashCode() {
        return getId() != null ? getId().hashCode() : 0;
    }

    @Override
    public String toString() {
        return "WallpaperInfo{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", image='" + image + '\'' +
                ", video='" + video + '\'' +
                '}';
    }
}
