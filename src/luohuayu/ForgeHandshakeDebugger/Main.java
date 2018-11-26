package luohuayu.ForgeHandshakeDebugger;

import java.util.Scanner;

import luohuayu.MCForgeProtocol.MCForgeInject;

public class Main {
    public static void main(String[] args) {
        ASMInject.inject();
        MCForgeInject.inject();

        BotClient client = new BotClient();
        client.modList.put("ic2", "2.8.101-ex112");
        client.connect("172.20.16.197", 31000, "Luohuayu");

        input(client);
    }

    public static void input(BotClient client) {
        Scanner sc = new Scanner(System.in);
        while (true) {
            String msg = sc.nextLine();
            client.chat(msg);
        }
    }
}
