package com.hathy.fblogin.com.hathy.adapters;

import android.widget.CheckBox;

import java.io.Serializable;

/**
 * Created by sriram vikas on 1/8/2016.
 */
public class LikeListItems implements Serializable {
    private static final long serialVersionUID = 1L;

    private String name;

    private String nameID;

    private boolean isSelected;

    private boolean checkAll;

    public LikeListItems() {

    }

    public LikeListItems(String name, String nameID) {

        this.name = name;
        this.nameID = nameID;

    }

    public LikeListItems(String name, String emailId, boolean isSelected, boolean checkAll) {

        this.name = name;
        this.nameID = emailId;
        this.isSelected = isSelected;
        this.checkAll = checkAll;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNameID() {
        return nameID;
    }

    public void setNameID(String nameID) {
        this.nameID = nameID;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean isSelected) {
        this.isSelected = isSelected;
    }

    public boolean getCheckAll() {
        return checkAll;
    }

    public void setCheckAll(boolean checkAll) {
        this.checkAll = checkAll;
    }
}
