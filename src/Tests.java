import client.BasePacket;
import client.Client;
import server.SlotMachineGame;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class Tests {
    @Test
    public void clientServerHandshake() throws IOException {
        Client client = new Client();
        try {
            client.startConnection("127.0.0.1", 6666);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        BasePacket responsePacket = client.sendPacket(new BasePacket(BasePacket.PacketType.HandShake));
        assertEquals(BasePacket.PacketType.Acknowledge, responsePacket.type);
    }

    @Test
    public void TestSlotMachineMatrix() {
        SlotMachineGame slotMachine = new SlotMachineGame();
        System.out.println(slotMachine.getSlots(3, 5));
    }
}
