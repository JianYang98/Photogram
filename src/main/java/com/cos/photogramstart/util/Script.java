package com.cos.photogramstart.util;

public class Script {
    //메세지 보여주고 뒤로 back back
    public static  String back(String msg){
        StringBuffer sb = new StringBuffer();
        sb.append("<script>");
        sb.append("alert('"+ msg+ "')");
        sb.append("history.back();");
        sb.append("</script>");
        return sb.toString() ;
    }
}
