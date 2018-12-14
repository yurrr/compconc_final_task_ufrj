public class Prisoner extends Thread {
    int id;
    Room room;
    Warden warden;
    int state = 0;
    int count = 0;

    public static final int
            IN_CELL    = 1,
            IN_ROOM    = 2,
            FREE       = 3,
            DEAD       = 4;

    final Object DentroDaCela = new Object();
    final Object SaiuDaCela = new Object();

    public Prisoner(int id, Room room, Warden warden){
        this.id = id;
        this.room = room;
        this.warden = warden;
    }

    public void run(){
        try {
            this.live();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    void live() throws InterruptedException{
        while (true) {
            synchronized (DentroDaCela) {
                this.state = IN_CELL;
                DentroDaCela.notifyAll();
            }

            synchronized (SaiuDaCela) {
                while (this.state == IN_CELL)
                    SaiuDaCela.wait();
            }
            // I got out of the cell...
            // am I free or dead? or should I go to the room?
            if (this.state != IN_ROOM)
                break;

            // I am in the room
            // do my custom action
            this.roomAction();

            // tell warden that I'm leaving the room
            this.warden.notifyPrisonerLeavingRoom();
        }

        if (this.state == FREE)
            // I'm free... I'm free!!!
            System.out.println("Prisoner " + this.id + " is free!");
        else
            // DEAD
            System.out.println("Prisoner " + this.id + " is dead!");
    }

    protected void roomAction() {
        // If I didn't switch the trigger on yet, then do it
        if (this.count < 1 && !this.room.isTriggerSet())
        {
            System.out.println("Prisoner: " + this.id + " turns trigger on!");
            this.count++;
            this.room.setTrigger();
        }
        else {
            System.out.println("Prisoner: " + this.id + " in room... nothing to do!");
        }
    }

    public void free() {
        synchronized (SaiuDaCela) {
            this.state = FREE;
            SaiuDaCela.notifyAll();
        }
    }

    public void kill() {
        synchronized (SaiuDaCela) {
            this.state = DEAD;
            SaiuDaCela.notifyAll();
        }
    }

    public void gotoRoom() {
        synchronized (SaiuDaCela) {
            System.out.println("Prisoner: " + this.id + " going to room.");
            this.state = IN_ROOM;
            SaiuDaCela.notifyAll();
        }
    }

    public void waitUntilInCell() {
        synchronized (DentroDaCela) {
            try {
                while (this.state != Prisoner.IN_CELL) {
                    this.DentroDaCela.wait();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
