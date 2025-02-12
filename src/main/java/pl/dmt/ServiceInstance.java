package pl.dmt;

import java.util.Objects;
import java.util.StringJoiner;

public class ServiceInstance implements Instance {
    private final String address;
    private String name;

    public ServiceInstance(String address) {
        this.address = address;
    }

    public ServiceInstance(String address, String name) {
        this.address = address;
        this.name = name;
    }

    @Override
    public String execute() {
        return "Executing instance: " + this;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        ServiceInstance instance = (ServiceInstance) o;
        return Objects.equals(address, instance.address);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(address);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", ServiceInstance.class.getSimpleName() + "[", "]")
                .add("address='" + address + "'")
                .add("name='" + name + "'")
                .toString();
    }
}
