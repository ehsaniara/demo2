package com.example.demo2.question;

import org.python.antlr.ast.Str;

public enum TopicEnum {
    //Intro to Kinematics
    DISTANCE_AND_DISPLACEMENT("Distance and Displacement", UnitEnum.INTRO_TO_KINEMATICS),
    SPEED_AND_VELOCITY("Speed and Velocity", UnitEnum.INTRO_TO_KINEMATICS),
    ACCELERATION("Acceleration", UnitEnum.INTRO_TO_KINEMATICS),
    SCALAR_AND_VECTOR_QUANTITIES("Scalar and Vectors", UnitEnum.INTRO_TO_KINEMATICS),
    PROJECTILE_MOTION("Projectile motion", UnitEnum.INTRO_TO_KINEMATICS),
    NEWTONS_LAWS_OF_MOTION("Newton's Laws of Motion", UnitEnum.INTRO_TO_KINEMATICS),
    NORMAL_FORCES("Normal Forces", UnitEnum.INTRO_TO_KINEMATICS),
    STATIC_AND_KINETIC_FRICTION("Static and Kinetic Friction", UnitEnum.INTRO_TO_KINEMATICS),
    GRAVITATIONAL_FORCES("Gravitational Forces", UnitEnum.INTRO_TO_KINEMATICS),
    CENTRIPETAL_FORCES("Centripetal Forces", UnitEnum.INTRO_TO_KINEMATICS),
    KINETIC_AND_POTENTIAL_ENERGY("Kinetic and Potential Energy", UnitEnum.INTRO_TO_KINEMATICS),
    MOMENTUM("Momentum", UnitEnum.INTRO_TO_KINEMATICS),
    //Intro to Waves
    PERIODIC_MOTION("Periodic Motion", UnitEnum.INTRO_TO_WAVE_MECHANICS),
    TRAVELING_WAVES("Traveling Waves", UnitEnum.INTRO_TO_WAVE_MECHANICS),
    WAVE_INTERFERENCE("Wave Interference", UnitEnum.INTRO_TO_WAVE_MECHANICS),
    RESONANCE("Resonance", UnitEnum.INTRO_TO_WAVE_MECHANICS),
    //Intro to E and M
    CIRCUIT_ELEMENTS("Resistors, Capacitors, and Inductors", UnitEnum.INTRO_TO_ELECTROMAGNETICS),
    ELECTRIC_CHARGE("Electric Charge", UnitEnum.INTRO_TO_ELECTROMAGNETICS),
    COULOMBS_LAW("Coulombs Law", UnitEnum.INTRO_TO_ELECTROMAGNETICS),
    //Classical Mechanics
    //Statistical Mechanics
    //String Theory
    TRANSFORMATIONS("Galilean and Lorentz Transformations", UnitEnum.STRING_THEORY),
    LENGTH_CONTRACTION("Length Contraction", UnitEnum.STRING_THEORY),
    TIME_DILATION("Time Dilation", UnitEnum.STRING_THEORY),
    RELATIVISTIC_MASS("Relativistic Mass", UnitEnum.STRING_THEORY);
    //Quantum Mechanics

    public String label;
    public UnitEnum unit;

    private TopicEnum(String label, UnitEnum unit) {
        this.label = label;
        this.unit = unit;
    }
    public String getLabel() {
        return label;
    }

    public UnitEnum getUnit() {
        return unit;
    }
}
