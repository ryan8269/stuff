// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide numerical or boolean
 * constants. This class should not be used for any other purpose. All constants should be declared
 * globally (i.e. public static). Do not put anything functional in this class.
 *
 * <p>It is advised to statically import this class (or one of its inner classes) wherever the
 * constants are needed, to reduce verbosity.
 */
public final class Constants {
    public static final int    TICKS_PER_ROTATION     = 1000;     //Amount of encoder ticks in one revolution //get using getCountsPerRevolution()
    public static final double WHEEL_DIAMETER         = 20;       //Diameter of wheel
    public static final double WHEEL_CIRCUMFERENCE    = WHEEL_DIAMETER * Math.PI; // Circumference of wheel
    public static final double DRIVE_SPEED            = 0.6;      //Amount of power that should be used when running drive train (between 0 and 1) 
    public static final int    ENCODER_PPR            = 20;       //Encoder pulse per revolution
    public static final double TURN_SPEED             = 0.4;      //Amount of power that should be used when running drive train (between 0 and 1) 
    
    public static final double INCHES_PER_METER       = 39.3701;  //Amount of inches per meter
    public static final double DEGREE_TO_RAD          = Math.PI /180; // converts degrees to radians
    public static final int    TIMEOUT_LIMIT          = 10000;    //Amount of time before timeout 
}
