package com.tiantian.pojo;

import java.io.Serializable;

public class TbFriends implements Serializable{
    private Integer id;

    private Integer useid;

    private Integer friendid;
    

  

	public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUseid() {
        return useid;
    }

    public void setUseid(Integer useid) {
        this.useid = useid;
    }

    public Integer getFriendid() {
        return friendid;
    }

    public void setFriendid(Integer friendid) {
        this.friendid = friendid;
    }
}