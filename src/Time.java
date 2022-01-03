import java.util.Date;

public class Time {

    public static long Time;
    public static long StartTime;
    public static double DeltaTime;
    public static int FPS;


    private static long FrameEndTime;
    private static int Frame = 0;

    public static void Start() {
        Time = new Date().getTime();
        StartTime = Time;
        FrameEndTime = Time + 1000;
    }

    public static void BeforeUpdate() {
        Frame++;
        long time = new Date().getTime();
        DeltaTime = (double) (time - Time) / 1000;
        Time = time;

        if(FrameEndTime < Time) {
            FPS = Frame;
            Frame = 1;
            FrameEndTime = Time + 1000;
            System.out.println(FPS + " FPS");
        }
    }

}
