package com.tiantian.pojo.utils;

import java.io.Serializable;

import com.tiantian.pojo.TbBlack;
import com.tiantian.pojo.TbUser;

/**
 * 用于FriendManage。jsp的拉黑
 *将TbUser left join TbBlack表
 * @author 晓东
 *
 */
public class TbUserWithBlackDto  implements Serializable{
	private TbUser tbUser;
	private TbBlack tbBlack;
	public TbUser getTbUser() {
		return tbUser;
	}
	public void setTbUser(TbUser tbUser) {
		this.tbUser = tbUser;
	}
	public TbBlack getTbBlack() {
		return tbBlack;
	}
	public void setTbBlack(TbBlack tbBlack) {
		this.tbBlack = tbBlack;
	}

}
