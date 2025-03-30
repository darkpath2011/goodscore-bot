package cn.lucas.qtxapilearning;

import cn.lucas.qtxapilearning.listener.QQBotListener;
import com.google.gson.Gson;
import io.github.kloping.qqbot.Starter;
import io.github.kloping.qqbot.api.Intents;
import lombok.Getter;
import okhttp3.OkHttpClient;

import java.util.concurrent.TimeUnit;

@Getter
public class Main {
    public static final OkHttpClient client = new OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            .callTimeout(120, TimeUnit.SECONDS)
            .build();
    public static final Gson gson = new Gson();
    public static Starter starter;
    private static String appid = "";
    private static String token = "";
    private static String secret = "";

    public static void main(String[] args) {
        starter = new Starter(appid,token,secret);
        starter.getConfig().setCode(Intents.PUBLIC_INTENTS.and(Intents.GROUP_INTENTS));
        starter.setReconnect(true);
        starter.APPLICATION.logger.setOutFile(null);
        starter.run();
        starter.registerListenerHost(new QQBotListener());
    }
}