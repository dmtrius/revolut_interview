package pl.dmt;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class LoadBalancer {
    private final Lock lock = new ReentrantLock();
    private final Random rand = new Random();
    private final int maxInstances;
    private final List<Instance> storage;

    public LoadBalancer(int maxInstances) {
        this.maxInstances = maxInstances;
        storage = new ArrayList<>(maxInstances);
    }

    public Instance getInstance() {
        int index = rand.nextInt(storage.size());
        return storage.get(index);
    }

    public void register(Instance instance) {
        try {
            lock.lock();
            if (storage.size() < maxInstances) {
                if (!storage.contains(instance)) {
                    storage.add(instance);
                } else {
                    throw new LoadBalancerException("INSTANCE already registered");
                }
            } else {
                throw new LoadBalancerException("exceeded MAX SIZE");
            }
        } finally {
            lock.unlock();
        }
    }

    public int getSize() {
        return storage.size();
    }
}
