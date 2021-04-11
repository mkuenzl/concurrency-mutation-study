package mutation4;

public class WorkerManagerMain
{
    public static void main(String[] args)
    {
        final int nrOfThreads = 10;
        for (int i = 0; i < nrOfThreads; i++)
        {
            new Worker().start();
        }
        timeout();
    }

    //@formatter:off
    private static void timeout() {
        final int timeout = 5;
        Thread thread = new Thread(() -> {
            try { Thread.sleep(timeout * 1000); }
            catch (InterruptedException ignored) { }
            finally {
                System.out.println("Timeout reached");
                Runtime.getRuntime().halt(0);
            }
        });
        thread.setDaemon(true);
        thread.start();
    }
}
