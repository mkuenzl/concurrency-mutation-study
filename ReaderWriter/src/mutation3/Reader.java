package mutation3;

import static java.lang.System.out;

public class Reader extends Thread
{
    private final RaceConditionBug raceConditionBug;

    public Reader(RaceConditionBug raceConditionBug)
    {
        this.raceConditionBug = raceConditionBug;
    }

    @Override
    public void run()
    {
        try
        { // Start all 'Reader' threads at once.
            raceConditionBug.latch.await();
        } catch (InterruptedException e)
        {
            e.printStackTrace();
        }
        while (!Thread.currentThread().isInterrupted())
        {
            try
            {
                synchronized (raceConditionBug)
                {
                    while (raceConditionBug.getCounter() < RaceConditionBug.END_VALUE)
                    {
                        raceConditionBug.wait();
                    }
                    break;
                }
            } catch (InterruptedException ignored) { this.interrupt(); }
        }

        out.printf("%s: terminating\n", Thread.currentThread().getName());
        raceConditionBug.readerTerminating();
    }
}