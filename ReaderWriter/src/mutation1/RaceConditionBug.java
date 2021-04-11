package mutation1;

import java.util.concurrent.CountDownLatch;

class RaceConditionBug
{
    private int counterVariable = 0;

    public static final int END_VALUE = 10000;
    CountDownLatch latch;

    RaceConditionBug()
    {
        int numberOfReaders = 10;
        int numberOfWriters = 2;

        latch = new CountDownLatch(numberOfWriters);

        for (int i = 0; i < numberOfReaders; i++)
        {
            Reader reader = new Reader(this);
            reader.start();
        }

        for (int i = 0; i < numberOfWriters; i++)
        {
            final Writer writer = new Writer(this);
            writer.start();
        }
    }

    int getCounter()
    {
        synchronized (this)
        {
            return counterVariable;
        }
    }

    void increaseCounter()
    {
        synchronized (this)
        {
            int tmp = counterVariable;
            tmp = tmp + 1;
            counterVariable = tmp;
            if (counterVariable == END_VALUE)
            {
                //mutation notifyAll -> notify
                //notifyAll();
                notify();
            }
        }
    }

    private int readersTerminated = 0;
    private final Object readersTerminatingLock = new Object();

    void readerTerminating() { synchronized (readersTerminatingLock) { readersTerminated++; } }

    int getReadersTerminated() {synchronized (readersTerminatingLock) { return readersTerminated;} }

    void printReadersTerminated()
    {
        System.out.println("Reader threads terminated = " + getReadersTerminated());
    }
}
