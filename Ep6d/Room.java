import java.util.Random;

public class Room {
    public static final int
            TRIG_INDEF = 0,
            TRIG_ON    = 1,
            TRIG_OFF   = 2;

    int trigger = TRIG_INDEF;
    boolean hasPrisoner = false;

    public void setTrigger() {
        this.trigger = TRIG_ON;
    }

    public void clearTrigger() {
        this.trigger = TRIG_OFF;
    }

    public boolean isTriggerSet() {
        return this.trigger == TRIG_ON;
    }

    public void closeDoor() {
        System.out.println("Room: door closed.");
        this.hasPrisoner = false;
    }

    public void openDoor() {
        System.out.println("Room: door opened.");
        this.hasPrisoner = true;
    }

    public boolean isDoorOpen() {
        return this.hasPrisoner;
    }

    public void RandomStartTrigger(){
        Random random = new Random();
        int initialState = random.nextInt(1000000000) % 100;
        if(initialState > 50)
            this.setTrigger();
        else
            this.clearTrigger();

        System.out.println("Initial Trigger State: " + this.isTriggerSet());
    }
}
