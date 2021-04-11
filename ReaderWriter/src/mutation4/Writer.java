package mutation4;

import static java.lang.System.out;

public class Writer extends Thread
{
    private final RaceConditionBug raceConditionBug;

    public Writer(RaceConditionBug raceConditionBug)
    {
        this.raceConditionBug = raceConditionBug;
    }

    @Override
    public void run()
    {
        raceConditionBug.latch.countDown();
        for (int i = 0; i < RaceConditionBug.END_VALUE; i++)
        {
            raceConditionBug.increaseCounter();
            long start = System.nanoTime(), j = 0;
            while (System.nanoTime() - start < 1e3)
            { // Simulation of some computational runtime
                j += i;
            }
        }
        out.println(Thread.currentThread().getName() + " current counter variable value = " + raceConditionBug.getCounter());
        out.println("END_VALUE = " + RaceConditionBug.END_VALUE);
    }
}
