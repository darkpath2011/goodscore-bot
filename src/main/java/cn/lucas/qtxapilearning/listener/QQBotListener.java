package cn.lucas.qtxapilearning.listener;

import cn.lucas.qtxapilearning.api.GoodScoreAPI;
import io.github.kloping.qqbot.api.v2.GroupMessageEvent;
import io.github.kloping.qqbot.entities.ex.MessageAsyncBuilder;
import io.github.kloping.qqbot.impl.ListenerHost;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class QQBotListener extends ListenerHost {
    @EventReceiver
    public void onMessage(GroupMessageEvent event) throws IOException {
        String message = event.getMessage().get(0).toString().trim();
        String[] part = message.split("\\s+");
        switch (part[0]) {
            case "绑定":
                break;
            case "查分":
                generateSubjectsString(event,part);
                break;
            case "答题卡":
                getAnswerPicture(event,part);
                break;
            default:
                event.sendMessage("是不会带参数吗？sb.（somebody）");
        }
    }

    public static void generateSubjectsString(GroupMessageEvent event,String[] part) {
        Map<String, Object> subjects = GoodScoreAPI.getSubjects(Integer.valueOf(part[1]),"");
        Map<String, Object> dataMap = (Map<String, Object>) subjects.get("data");
        List<Map<String, Object>> paperList = (List<Map<String, Object>>) dataMap.get("papers");
        StringBuilder builder = new StringBuilder();
        for (Map<String, Object> paper : paperList) {
            String subject = (String) paper.get("subject");
            Double manfen = (Double) paper.get("manfen");
            Double score = (Double) paper.get("score");
            builder.append("科目: ").append(subject).append(", 满分: ").append(manfen).append(", 我的成绩: ").append(score).append("\n");
        }
        event.sendMessage(builder.toString());
    }

    private static void getAnswerPicture(GroupMessageEvent event,String[] part){
        String id = part[1];
        Map<String, Object> subjects = GoodScoreAPI.getSubjects(Integer.valueOf(id),"");
        Map<String, Object> dataMap = (Map<String, Object>) subjects.get("data");
        List<Map<String, Object>> paperList = (List<Map<String, Object>>) dataMap.get("papers");
        String paperId = null;
        String pid = null;
        for (Map<String, Object> paper : paperList) {
            paperId = (String) paper.get("paperId");
            pid = (String) paper.get("pid");
        }
        List<String> answerPicture = GoodScoreAPI.getAnswerPicture(paperId, pid, id);
        MessageAsyncBuilder builder = new MessageAsyncBuilder();
        for (String a : answerPicture){
            builder.image(a);
        }
        event.sendMessage(builder.build());
    }
}
