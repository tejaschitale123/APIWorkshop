package com.trello.api.board;

public class Board {
    private String name;
    private String desc;
    private String prefs_permissionLevel;

    public Board(String name, String desc, String prefs_permissionLevel) {
        this.name = name;
        this.desc = desc;
        this. prefs_permissionLevel = prefs_permissionLevel;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getPrefs_permissionLevel() {
        return prefs_permissionLevel;
    }

    public void setPrefs_permissionLevel(String prefs_permissionLevel) {
        this.prefs_permissionLevel = prefs_permissionLevel;
    }
}
