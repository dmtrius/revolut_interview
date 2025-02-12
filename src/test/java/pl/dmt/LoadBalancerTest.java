package pl.dmt;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.internal.verification.VerificationModeFactory.times;

@ExtendWith(MockitoExtension.class)
@PrepareForTest(LoadBalancer.class)
class LoadBalancerTest {

    @Test
    void test_lb_register() {
        LoadBalancer<Instance> lb = new LoadBalancer<>(1);
        Instance instance = new ServiceInstance("addr1", "NAME_1");
        assertDoesNotThrow(() -> lb.register(instance));
        assertEquals(1, lb.getSize());
        ServiceInstance toTest = (ServiceInstance) lb.getInstance();
        assertEquals("addr1", toTest.getAddress());
    }

    @Test
    void test_lb_register_and_execute() {
        LoadBalancer<Instance> lb = new LoadBalancer<>(1);
        Instance instance = PowerMockito.spy(new ServiceInstance("addr1", "NAME_1"));
        assertDoesNotThrow(() -> lb.register(instance));
        assertEquals(1, lb.getSize());
        assertDoesNotThrow(instance::execute);
        assertEquals("Executing instance: ServiceInstance[address='addr1', name='NAME_1']",
                instance.execute());
        Mockito.verify(instance, times(2)).execute();
    }

    @Test
    void test_lb_register_unregister() {
        LoadBalancer<Instance> lb = new LoadBalancer<>(1);
        Instance instance = new ServiceInstance("addr1");
        assertDoesNotThrow(() -> lb.register(instance));
        assertEquals(1, lb.getSize());
        assertDoesNotThrow(() -> lb.unregister(instance));
        assertEquals(0, lb.getSize());
    }

    @Test
    void test_lb_register_twice_throw_exception() {
        LoadBalancer<Instance> lb = new LoadBalancer<>(2);
        Instance instance = new ServiceInstance("addr1");
        Instance instance2 = new ServiceInstance("addr1");
        assertDoesNotThrow(() -> lb.register(instance));
        assertThrows(LoadBalancerException.class,
                () -> lb.register(instance2));
    }

    @Test
    void test_lb_register_max_throw_exception() {
        LoadBalancer<Instance> lb = new LoadBalancer<>(1);
        Instance instance = new ServiceInstance("addr1");
        Instance instance2 = new ServiceInstance("addr2");
        assertDoesNotThrow(() -> lb.register(instance));
        assertThrows(LoadBalancerException.class,
                () -> lb.register(instance2));
    }

    @Test
    void test_lb_get_random_instance() throws IllegalAccessException {
        Random rand = PowerMockito.mock(Random.class);
        LoadBalancer<Instance> lb = PowerMockito.spy(new LoadBalancer<>(3));
        PowerMockito.field(LoadBalancer.class, "rand").set(lb, rand);
        Instance instance = new ServiceInstance("addr1");
        Instance instance2 = new ServiceInstance("addr2");
        Instance instance3 =
                new ServiceInstance("addr3", "NAME_3");
        lb.register(instance);
        lb.register(instance2);
        lb.register(instance3);
        int counter = 9;
        for (int i = 0; i < counter; i++) {
            lb.getInstance();
        }
        assertEquals(3, lb.getSize());
        Mockito.verify(rand, times(counter))
                .nextInt(anyInt());
    }
}
