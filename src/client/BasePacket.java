package client;

public class BasePacket {
    public enum PacketType {
        HandShake,
        Disconnect,
        Acknowledge,
        Error,

        CreateGame,
        GameSettings,
        JoinGame,
    }

    public enum ErrorType {
        UnrecognizedGreeting,
        InvalidPacketData,
    }

    public PacketType type;
    public ErrorType errorType;

    public BasePacket(PacketType type) {
        this.type = type;
    }
}
