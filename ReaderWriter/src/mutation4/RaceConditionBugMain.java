package mutation4;

public class RaceConditionBugMain
{
    public static void main(String[] args)
    {
        final RaceConditionBug raceConditionBug = new RaceConditionBug();
        Runtime.getRuntime().addShutdownHook(new Thread(raceConditionBug::printReadersTerminated));
        timeout(raceConditionBug);
    }

    //@formatter:off
    private static void timeout(final RaceConditionBug raceConditionBug) {
        final int timeout = 5;
        Thread thread = new Thread(() -> {
            try { Thread.sleep(timeout * 1000); }
            catch (InterruptedException ignored) { }
            finally {
                System.out.println("Timeout reached");
                raceConditionBug.printReadersTerminated();
                Runtime.getRuntime().halt(0);
            }
        });
        thread.setDaemon(true);
        thread.start();
    }
}
