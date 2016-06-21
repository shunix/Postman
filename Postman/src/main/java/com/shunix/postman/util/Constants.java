package com.shunix.postman.util;

/**
 * @author shunix
 * @since 2016/6/21
 */
public class Constants {
    public final static int RETCODE_NEXT_PACKET = 1; // server is ready for receiving packet
    public final static int RETCODE_MSG_FINISHED = 2; // server already got full message
    public final static int RETCODE_ERROR = 3; // errors occurred
}
