package com.example.demo2.question;

public enum UnitEnum {
    INTRO_TO_KINEMATICS("Intro to Kinematics"),
    INTRO_TO_WAVE_MECHANICS("Intro to Wave Mechanics"),
    INTRO_TO_THERMODYNAMICS("Intro to Thermodynamics"),
    INTRO_TO_ELECTROMAGNETICS("Intro to Electro-Magnetics"),
    CLASSICAL_MECHANICS("Classical Mechanics"),
    STATISTICAL_MECHANICS("Statistical Mechanics"),
    STRING_THEORY("String Theory"),
    QUANTUM_MECHANICS("Quantum_mechanics");


    public String label;

    private UnitEnum(String label) {
        this.label = label;
    }
    public String getLabel() {
        return label;
    }
}
