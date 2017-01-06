package com.hoquocvung.gamelivewallpaper.Entity;

import java.util.ArrayList;

/**
 * Created by vungho on 17/12/2016.
 */

public class ProjectContent {
    private ArrayList<GameInfo> listGame;

    public  ProjectContent(){
        this.listGame = new ArrayList<GameInfo>();
    }

    public boolean addGame(GameInfo gameInfo){
        if (listGame.contains(gameInfo) || gameInfo == null)
            return false;
        return listGame.add(gameInfo);
    }

    public ArrayList<GameInfo> getListGame() {
        return listGame;
    }

    public GameInfo getGameInfo(String id){
        for (GameInfo item: listGame){
            if (item.getId().equalsIgnoreCase(id)){
                return item;
            }
        }
        return null;
    }
}
