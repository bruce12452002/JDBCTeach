package spi;

import java.util.ServiceLoader;

public interface Animal {
    String getName();

    static void main(String[] args) {
        ServiceLoader<Animal> s = ServiceLoader.load(Animal.class);
        for (Animal a : s) {
            System.out.println(a.getName());
        }
    }
}
