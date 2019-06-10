package common.constant;

/**
 * 
 * @author 晓东
 *
 */
public interface TianTianMessageConstant {
	 /**
     * 获取好友请求的数据，并不删除请求的消息
     */
    public static final int MESSAGE_TYPE_FRIENDS_ADD_GET=1;

    /**
     * 响应状态码，2表示失败
     */
    public static final int MESSAGE_TYPE_FRIENDS_ADD_REMOVE=2;

    /**
     * 响应状态码，3表示此条信息为 好友请求信息
     */
    public static final int MESSAGE_TYPE_Freinds_REQUEST=3;
    /**
     * 响应状态码，4表示此条信息为 好友请求返回结果信息
     */
    public static final int MESSAGE_TYPE_Freinds_REQUEST_RESPONSE=4;
    /**
     * 响应状态码，5表示此条信息为 普通信息
     */
    public static final int MESSAGE_TYPE_NORMAL=5;
    /*
     *   6表示视频请求消息
     */
    public static final int MESSAGE_TYPE_VEDIO_REQUEST=6;
  
    
    /*
     * 在打开message页面时，分页大小为5
     */
    public static final int PAGE_SIZE=10;
}
