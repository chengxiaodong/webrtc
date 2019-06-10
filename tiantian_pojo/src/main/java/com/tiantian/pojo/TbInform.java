package com.tiantian.pojo;

import java.io.Serializable;

/**
 * 
 * @author 晓东
 *
 */
public class TbInform implements Serializable{
    private Integer id;

    private Integer target;

    private String message;

    private Integer original;

    private String createDate;
    
    
  
    



	public int getReaded() {
		return readed;
	}

	public void setReaded(int readed) {
		this.readed = readed;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	private int readed;
    
    private int type;
    
    public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getTarget() {
        return target;
    }

    public void setTarget(Integer target) {
        this.target = target;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message == null ? null : message.trim();
    }

    public Integer getOriginal() {
        return original;
    }

    public void setOriginal(Integer original) {
        this.original = original;
    }
}