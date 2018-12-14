import java.util.Random;

public class Main {

    private static int numPrisoners = 10;


    public static void run() throws Exception {

        System.out.println(numPrisoners);

        Prisoner[] prisoners = new Prisoner[numPrisoners];
        Room room = new Room();

        room.RandomStartTrigger(); //randomize trigger initial state

        Warden warden = new Warden(room, prisoners);
        prisoners[0] = new Leader(0, room, warden, prisoners.length);

        for (int it = 1; it < prisoners.length; it++)
            prisoners[it] = new Prisoner(it, room, warden);

        warden.start();
        for (int it = 0; it < prisoners.length; it++)
            prisoners[it].start();

        warden.join();
        for (int it = 0; it < prisoners.length; it++)
            prisoners[it].join();

    }

    public static void RunTests(int times) throws Exception{
        for(int i = 0; i < 100000; i++){
            run();
        }
    }

    public static void RunOnce() throws Exception{
            run();
    }

    public static void main(String[] args) throws Exception {
        //runs once and return
        //RunOnce();

        //run the n number of times and return
        RunTests(100);
    }
}
