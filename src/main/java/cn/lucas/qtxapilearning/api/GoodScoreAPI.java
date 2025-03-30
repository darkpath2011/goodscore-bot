package cn.lucas.qtxapilearning.api;

import cn.lucas.qtxapilearning.utils.HttpRequestHelper;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GoodScoreAPI {
    public static Map<String, Object> getSubjects(int kaoshiid,String sessionId){
        Map<String,Object> map = null;
        try {
            map = HttpRequestHelper.sendGetRequest("https://hfs-be.yunxiao.com/v3/exam/"+kaoshiid+"/overview",sessionId);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return map;
    }

    public static String bindUser(int loginName,String password){
        Map<String,Object> map = null;
        try {
            Map<String, Object> payload = new HashMap<>();
            payload.put("loginName",loginName);
            payload.put("password",password);
            payload.put("loginType",1);
            payload.put("roleType",1);
            payload.put("rememberMe",1);
            map = HttpRequestHelper.sendPostRequest("https://hfs-be.yunxiao.com/v2/users/sessions",payload);
            int code = (int) map.get("code");
            if (code != 0) {
                return "1";
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Map<String, Object> data = (Map<String, Object>) map.get("data");
        return data.get("token").toString();
    }

    public static List<String> getAnswerPicture(String paperId, String pid, String examId){
        Map<String, Object> stringObjectMap = null;
        try {
            stringObjectMap = HttpRequestHelper.sendGetRequest("https://hfs-be.yunxiao.com/v3/exam/" + examId + "/papers/" + paperId + "/answer-picture?pid=" + pid, "");
            System.out.println(stringObjectMap.toString());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Map<String, Object> data = (Map<String, Object>) stringObjectMap.get("data");
        List<String> urls = (List<String>) data.get("url");
        return urls;
    }
}
