package pl.dmt;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class LoadBalancer<T> {
    private final Lock lock = new ReentrantLock();
    private final Random rand = new Random();
    private final int maxInstances;
    private final List<T> storage;

    public LoadBalancer(int maxInstances) {
        this.maxInstances = maxInstances;
        storage = new ArrayList<>(maxInstances);
    }

    public T getInstance() {
        if (getSize() == 0) {
            throw new LoadBalancerException("No instances registered");
        }
        int index = rand.nextInt(getSize());
        return storage.get(index);
    }

    public void register(T instance) {
        try {
            lock.lock();
            if (getSize() < maxInstances) {
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

    public void unregister(T instance) {
        try {
            lock.lock();
            if (!storage.isEmpty()) {
                if (storage.contains(instance)) {
                    storage.remove(instance);
                } else {
                    throw new LoadBalancerException("INSTANCE not registered");
                }
            } else {
                throw new LoadBalancerException("EMPTY");
            }
        } finally {
            lock.unlock();
        }
    }

    public synchronized int getSize() {
        return storage.size();
    }
}
