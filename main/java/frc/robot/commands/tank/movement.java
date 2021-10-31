package frc.robot.commands.tank;

import edu.wpi.first.wpilibj.Filesystem;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.Constants;
import frc.robot.subsystems.tank.TankSubsystem;
import edu.wpi.first.wpilibj.Timer;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;


//encoder talonSRX.getSensorCollection().getQuadraturePosition();

public class movement extends CommandBase {
    private final TankSubsystem tankSubsystem;
    private Timer waiting = new Timer();
    public movement(TankSubsystem tankSubsystem) { // TODO add parameters
        this.tankSubsystem = tankSubsystem;

        addRequirements(tankSubsystem);
    }

    @Override
    public void initialize() {
        movements();
    }

    public void movements(){
        Queue<String> inputs = new LinkedList<>();
        try {
            BufferedReader reader = new BufferedReader (new FileReader(new File(Filesystem.getDeployDirectory(), "movementInput.txt"))); // reading all files from movementInput
            String line = reader.readLine();
            while (line != null) {
                inputs.add(line);
                line = reader.readLine();
            }
            reader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (!inputs.isEmpty()) // until empty task the robot with properly 
        {
            String splitLine[] = inputs.poll().split(" "); 
            //for turning, [0] = turn, [1] = direction to turn. [2] = angle to turn to
            //for driving, [0] = forward/backward, [1] = distance to drive
            if (splitLine[0] == "turn")
            {
                turn(splitLine[1],Double.parseDouble(splitLine[2]),Constants.TURN_SPEED);
            }
            else if (splitLine[0] == "forward" || splitLine[0] == "backwards")
            {
                drive(splitLine[0],splitLine[1], Constants.DRIVE_SPEED);  
            }
            else
            {
                //something about invalid input
            }
        }
    }

    public void drive(String direction, String destination, double driveSpeed) //drives in given destination(Destination could be in time or distance) 
    {
        if (destination.charAt(destination.length()-1) == 's') // check if using seconds
        {
            timeDrive(direction,destination,driveSpeed);
        }
        else if (destination.charAt(destination.length()-1) == 'm') // check if using distance
        {
            distanceDrive(direction,destination,driveSpeed);
        }
        else
        {
            //something about invalid input
        }
    }

    public void timeDrive(String direction, String duration, double driveSpeed)
    {
        double drivingTime = Double.parseDouble(duration.substring(0,duration.length()-1)); //in seconds
        if (direction == "forward")
        {
            tankSubsystem.setDrivePowers(driveSpeed,0);
        }
        else if (direction == "backward")
        {
            tankSubsystem.setDrivePowers(-1*driveSpeed,0);

        }
        waiting.delay(drivingTime); //replace with better suited function
        tankSubsystem.stopDrive();
    }

    public void distanceDrive(String direction, String distance, double driveSpeed)
    {
        double desiredDistance = Double.parseDouble(distance.substring(0,distance.length()-1)) * Constants.INCHES_PER_METER; // in inches
        double startPos = tankSubsystem.getLeftEncoderPosition(); //where the robot ends up
        if (direction == "backward")
        {
            desiredDistance*=-1;
        }
        tankSubsystem.setDrivePowers(Math.signum(desiredDistance)*driveSpeed,0);
        while(Math.abs(tankSubsystem.getLeftEncoderPosition() - startPos) * Constants.INCHES_PER_METER < desiredDistance) // will overshoot distance
        {
        }
        tankSubsystem.stopDrive();
    }

    public void turn(String direction, double angle, double turnSpeed) // turns in given direction a certain amount of degrees
    {
        double startingAngle = tankSubsystem.getHeading(); //get initail heading
        if (direction == "left")
        {
            tankSubsystem.setDrivePowers(0, -1*turnSpeed); // - for counter-clockwise
        }
        else if (direction == "right")
        {
            tankSubsystem.setDrivePowers(0,turnSpeed);// + for clockwise
        }
        while(Math.abs(tankSubsystem.getHeading() - startingAngle) < angle){
        }
        tankSubsystem.stopDrive();
    }
}

