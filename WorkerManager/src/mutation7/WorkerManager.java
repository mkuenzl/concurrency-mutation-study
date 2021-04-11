package mutation7;

import java.util.HashSet;
import java.util.Set;

import static java.lang.System.out;

public class WorkerManager
{
    private static WorkerManager instance = null;
    private final Set<Thread> registeredWorkers = new HashSet<>();

    //Mutation removed synchronized block - added synchronized as method keyword
    public synchronized static WorkerManager getInstance()
    {
        long start = System.nanoTime();
        while (System.nanoTime() - start < 1e7)
        {
            int i = 42;
        }
        if (instance == null)
        {
            //Bug Fix
            //synchronized (WorkerManager.class)
            //{
            //    if (instance == null)
                    instance = new WorkerManager();
            //}
        }
        return instance;
    }

    private WorkerManager()
    {   // Do some work
        long start = System.nanoTime();
        while (System.nanoTime() - start < 1e9)//1e5)
        {
            int i = 42;
        }
    }

    public void registerWorker(Thread worker)
    {
        synchronized (registeredWorkers)
        {
            registeredWorkers.add(worker);
        }
    }

    public void workerReadyToInterrupt(Thread worker)
    {
        synchronized (registeredWorkers)
        {
            if (registeredWorkers.contains(worker))
            {
                registeredWorkers.remove(worker);
                worker.interrupt();
            }
        }
    }

    public void printRegisteredWorkers()
    {
        synchronized (registeredWorkers)
        {
            for (Thread thread : registeredWorkers)
            {
                out.println(thread.getName());
            }
            out.println("------------");
        }
    }
}
