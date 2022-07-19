package Model;

/**
 *
 * @author Yousef Hussien
 */
public enum Message {
    PUMP ("Pump it >.<"),
    LOW_BATTERY ("Recharge or die with extra sugar x_x"),
    EMERGENCY("You are so dead xd");

    private final String name;

    private Message(String s) {
        name = s;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
