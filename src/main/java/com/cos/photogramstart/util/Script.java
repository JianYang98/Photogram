package com.cos.photogramstart.util;

public class Script {
    //메세지 보여주고 뒤로 back back
    public static  String back(String msg){
        System.out.println("Script 왔슘 ");
        StringBuffer sb = new StringBuffer();
        sb.append("<script>");
        sb.append("alert('"+ msg+ "')");
        sb.append("history.back();");
        sb.append("</script>");

        System.out.println(    sb.toString()    );
        return sb.toString() ;
    }
}
