package mutation4;

import static java.lang.System.out;

class Worker extends Thread
{
    @Override
    public void run()
    {
        WorkerManager.getInstance().registerWorker(this);
        while(!Thread.currentThread().isInterrupted())
        {
            int index = 1;
            long start = System.nanoTime();
            while (System.nanoTime() - start < 5e6)
            {
                index++;
            }
            if (index > 0)
            {
                WorkerManager.getInstance().printRegisteredWorkers();
                WorkerManager.getInstance().workerReadyToInterrupt(this);
            }
        }
        out.printf("%s done\n", Thread.currentThread().getName());
    }
}
