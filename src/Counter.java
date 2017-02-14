
public class Counter {

    public static int secs;
    public static int mins;
    public static int hrs;

    public Counter() {
        resetCounter();

    }

    public static int getHrs() {
        return hrs;
    }

    public int getSecs() {
        return secs;
    }

    public int getMins() {
        return mins;
    }

    public void resetCounter() {
        secs = 0;
        mins = 0;
        hrs = 0;
    }

    public void increment() {

        if (++secs > 59) {
            ++mins;
            secs = 0;
        }

        if (mins > 59) {
            ++hrs;
            mins = 0;
        }

    }

    public String toString() {
        String rtnHrs = String.format("%02d", hrs);
        String rtnMins = String.format("%02d", mins);
        String rtnSecs = String.format("%02d", secs);

        return (rtnHrs + ":" + rtnMins + ":" + rtnSecs);
    }
}
