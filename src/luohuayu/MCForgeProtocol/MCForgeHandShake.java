package luohuayu.MCForgeProtocol;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import org.spacehq.mc.protocol.packet.ingame.client.ClientPluginMessagePacket;
import org.spacehq.mc.protocol.packet.ingame.server.ServerPluginMessagePacket;
import org.spacehq.packetlib.Session;
import org.spacehq.packetlib.io.stream.StreamNetOutput;

import luohuayu.ForgeHandshakeDebugger.Utils;

public class MCForgeHandShake {
    private MCForge forge;

    public MCForgeHandShake(MCForge forge) {
        this.forge = forge;
    }

    public void handle(ServerPluginMessagePacket packet) {
        Session session = forge.session;

        byte[] data = packet.getData();
        int packetID = data[0];

        switch (packetID) {
        case 0: // Hello
            Utils.log("Forge Handshake(S->C): Hello");
            sendPluginMessage(session, "FML|HS", new byte[] { 0x01, 0x02 });
            Utils.log("Forge Handshake(C->S): Hello");

            // ModList
            ByteArrayOutputStream buf = new ByteArrayOutputStream();
            StreamNetOutput out = new StreamNetOutput(buf);
            try {
                out.writeVarInt(2);
                out.writeByte(forge.modList.size());
                forge.modList.forEach((k, v) -> {
                    try {
                        out.writeString(k);
                        out.writeString(v);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
            } catch (IOException e) {
                e.printStackTrace();
            }
            sendPluginMessage(session, "FML|HS", buf.toByteArray());
            Utils.log("Forge Handshake(C->S): ModList");
            break;
        case 2: // ModList
            Utils.log("Forge Handshake(S->C): ModList");
            sendPluginMessage(session, "FML|HS", new byte[] { -0x1, 0x02 }); // ACK(WAITING SERVER DATA)
            Utils.log("Forge Handshake(C->S): ACK(WAITING SERVER DATA)");
            break;
        case 3: // RegistryData
            Utils.log("Forge Handshake(S->C): RegistryData");
            sendPluginMessage(session, "FML|HS", new byte[] { -0x1, 0x03 }); // ACK(WAITING SERVER COMPLETE)
            Utils.log("Forge Handshake(C->S): ACK(WAITING SERVER COMPLETE)");
            break;
        case -1: // HandshakeAck
            int ackID = data[1];
            switch (ackID) {
            case 2: // WAITING CACK
                Utils.log("Forge Handshake(S->C): WAITING CACK");
                sendPluginMessage(session, "FML|HS", new byte[] { -0x1, 0x04 }); // PENDING COMPLETE
                Utils.log("Forge Handshake(C->S): PENDING COMPLETE");
                break;
            case 3: // COMPLETE
                Utils.log("Forge Handshake(S->C): COMPLETE");
                sendPluginMessage(session, "FML|HS", new byte[] { -0x1, 0x05 }); // COMPLETE
                Utils.log("Forge Handshake(C->S): COMPLETE");
                break;
            default:
            }
        default:
        }
    }

    private void sendPluginMessage(Session session, String channel, byte[] data) {
        session.send(new ClientPluginMessagePacket(channel, data));
    }
}
