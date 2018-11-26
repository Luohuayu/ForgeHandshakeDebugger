package luohuayu.MCForgeProtocol;

import java.io.IOException;

import org.spacehq.packetlib.io.NetInput;

public class MCForgeUtils {
    public static int readVarShort(NetInput in) throws IOException {
        int low = in.readUnsignedShort();
        int high = 0;
        if ((low & 0x8000) != 0) {
            low = low & 0x7FFF;
            high = in.readUnsignedByte();
        }
        return ((high & 0xFF) << 15) | low;
    }
}
