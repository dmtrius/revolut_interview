package pl.dmt;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class LoadBalancerTest {

    @Test()
    void test_lb_register() {
        LoadBalancer lb = new LoadBalancer(1);
        Instance instance = new Instance("addr1");
        Assertions.assertDoesNotThrow(() -> lb.register(instance));
        assertEquals(1, lb.getSize());
    }

    @Test()
    void test_lb_register_twice_throw_exception() {
        LoadBalancer lb = new LoadBalancer(2);
        Instance instance = new Instance("addr1");
        Instance instance2 = new Instance("addr1");
        Assertions.assertDoesNotThrow(() -> lb.register(instance));
        Assertions.assertThrows(RuntimeException.class ,() -> lb.register(instance2));
    }

    @Test()
    void test_lb_register_max_throw_exception() {
        LoadBalancer lb = new LoadBalancer(1);
        Instance instance = new Instance("addr1");
        Instance instance2 = new Instance("addr2");
        Assertions.assertDoesNotThrow(() -> lb.register(instance));
        Assertions.assertThrows(RuntimeException.class ,() -> lb.register(instance2));
    }

    @Test
    void test_lb_get_random_instance() {
        LoadBalancer lb = new LoadBalancer(3);
        Instance instance = new Instance("addr1");
        Instance instance2 = new Instance("addr2");
        Instance instance3 = new Instance("addr3");
        instance3.setName("INSTANCE_3");
        lb.register(instance);
        lb.register(instance2);
        lb.register(instance3);
        System.out.println(lb.getInstance());
        System.out.println(lb.getInstance());
        System.out.println(lb.getInstance());
        System.out.println(lb.getInstance());
        System.out.println(lb.getInstance());
        System.out.println(lb.getInstance());
        System.out.println(lb.getInstance());
        System.out.println(lb.getInstance());
        System.out.println(lb.getInstance());
        assertEquals(3, lb.getSize());
    }
}
