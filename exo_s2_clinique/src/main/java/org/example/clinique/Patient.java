package org.example.clinique;

public class Patient {

    private final int gravity;
    private final String name;

    // Getter
    public String getName() {
        return name;
    }

    public int getGravity() {
        return this.gravity;
    }

    public Patient(String name, int gravity) {
        this.name = name;
        this.gravity = gravity;
    }
}
