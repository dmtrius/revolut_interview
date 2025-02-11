package pl.dmt;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.Objects;

public class Instance {
    private final String address;
    private String name;

    public Instance(String address) {
        this.address = address;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Instance instance = (Instance) o;
        return Objects.equals(address, instance.address);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(address);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("address", address)
                .append("name", Objects.isNull(name) ? address.toUpperCase() : name)
                .toString();
    }
}
