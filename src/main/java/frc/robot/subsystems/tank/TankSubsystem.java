// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.tank;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.InvertType;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.wpilibj.Encoder;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants; 

public class TankSubsystem extends SubsystemBase {
  private final TalonSRX leftMain;
  private final TalonSRX leftFollow;

  private final TalonSRX rightMain;
  private final TalonSRX rightFollow;

  private final Encoder leftEncoder;
  private final Encoder rightEncoder;

  private NavXGyro gyro;

  public TankSubsystem(int fLeftId, int bLeftId, int fRightId, int bRightId, int lChannelA, int lChannelB, int rChannelA, int rChannelB) {
    super();

    this.gyro = new NavXGyro();
    gyro.reset();

    leftMain = new TalonSRX(fLeftId);
    leftMain.setNeutralMode(NeutralMode.Brake);
    
    leftFollow = new TalonSRX(bLeftId);
    leftFollow.follow(leftMain);
    leftFollow.setInverted(InvertType.FollowMaster);
    leftFollow.setNeutralMode(NeutralMode.Brake);

    rightMain = new TalonSRX(fRightId);
    rightMain.setInverted(true);
    rightMain.setNeutralMode(NeutralMode.Brake);

    
    rightFollow = new TalonSRX(bRightId);
    rightFollow.follow(rightMain);
    rightFollow.setInverted(InvertType.FollowMaster);
    rightFollow.setNeutralMode(NeutralMode.Brake);

    leftEncoder = new Encoder(lChannelA,lChannelB);
    leftEncoder.setDistancePerPulse(Constants.WHEEL_CIRCUMFERENCE/Constants.ENCODER_PPR); //scaling encoder to be in inches

    rightEncoder = new Encoder(rChannelA,rChannelB);
    rightEncoder.setDistancePerPulse(Constants.WHEEL_CIRCUMFERENCE/Constants.ENCODER_PPR); //scaling encoder to be in inches
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    // TODO: odometry
  }

  @Override
  public void simulationPeriodic() {
    // This method will be called once per scheduler run during simulation
  }

  /**
   * Drive the system with the given power scales.
   * 
   * @param yScale       Scale in the forward/backward direction, from 1 to -1.
   * @param angularScale Scale in the rotational direction, from 1 to -1,
   *                     clockwise to counterclockwise.
   */
  public void setDrivePowers(double yScale, double angularScale) {
    setDrivePowers(yScale, angularScale, true);
  }

  public void setDrivePowers(double yScale, double angularScale, boolean squareInput) {

    // Square the input if needed for finer control
    if (squareInput) {
      yScale = Math.copySign(yScale * yScale, yScale);
      angularScale = Math.copySign(angularScale * angularScale, angularScale);
    }

    double leftPower = yScale + angularScale;
    double rightPower = yScale - angularScale;

    // Scale powers greater than 1 back to 1 if needed
    double largest_power = Math.max(Math.abs(leftPower), Math.abs(rightPower));
    if (largest_power > 1.0) {
      double scale = 1.0 / largest_power;
  
      leftPower *= scale;
      rightPower *= scale;
    }

    leftMain.set(ControlMode.PercentOutput, leftPower);
    rightMain.set(ControlMode.PercentOutput, rightPower);
  }

  public void followPathCommand() {

  }

  public double getLeftEncoderPosition(){
    return leftEncoder.getDistance();
  }

  public double getRightEncoderPosition(){
    return rightEncoder.getDistance();
  }

  public double getHeading(){ // returns yaw of robot(heading)
    return gyro.getAngle();
  }

  public void stopDrive()
  {
    setDrivePowers(0,0);
  }
}
