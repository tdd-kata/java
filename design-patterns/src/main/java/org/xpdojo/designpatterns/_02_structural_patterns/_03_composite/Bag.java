package org.xpdojo.designpatterns._02_structural_patterns._03_composite;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Bag implements Component {

    private final List<Component> components = new ArrayList<>();

    public void add(Component component) {
        components.add(component);
    }

    public List<Component> getComponents() {
        return Collections.unmodifiableList(components);
    }

    @Override
    public int getPrice() {
        return components.stream().mapToInt(Component::getPrice).sum();
    }
}
