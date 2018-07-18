package Helpers;

import javax.servlet.http.HttpSession;

public class MsgHelper {
    /**
     * 在线程里放置消息
     * @param session
     * @param msg_text
     */
    public static void setMsg(HttpSession session, String msg_text) {
        session.setAttribute("msg", msg_text);
    }

    /**
     * 从线程里移除消息
     * @param session
     */
    public static void removeMsg(HttpSession session) {
        session.removeAttribute("msg");
    }
}
