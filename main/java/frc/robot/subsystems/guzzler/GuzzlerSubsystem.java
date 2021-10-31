package frc.robot.subsystems.guzzler;

import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.SubsystemBase;



public class GuzzlerSubsystem extends SubsystemBase{
    //private final [Motor type] rightIntake;
    //private final [Motor type] leftIntake;
    //private final [Ir sensor type] itemSensor;

    private final double intakeSpeed = 1.0;
    private boolean intakeRunning;
    private int storageCount;
    private boolean awaitingItem = true;

    public GuzzlerSubsystem(int rightIntakeId, int leftIntakeId, int itemSensorId)
    {
        super();

        intakeRunning = false;
        storageCount = 0;

        //rightIntake = new [Motor type](rightIntakeId);
        //leftIntake = new [Motor type](leftIntakeId);
        //itemSensor = new [Ir sensor type](itemSensorId);
    }

    @Override
     public void periodic() {
     // This method will be called once per scheduler run
    }

    @Override
    public void simulationPeriodic() {
        // This method will be called once per scheduler run during simulation
    }

    public void toggleIntake(){
        intakeRunning = !intakeRunning;
        if (intakeRunning)
        {
            //rightIntake.setMotorPower(intakeSpeed);
            //leftMotor.setMotorPower(-1*intakeSpeed);
        }
    }

    public void reverseIntake(){
        if (intakeRunning)
        {
            //rightIntake.setMotorPower(-1*intakeSpeed);
            //leftMotor.setMotorPower(intakeSpeed);
        }
    }

    public void itemUpdate() // check if new item is going in storage
    {
        if (awaitingItem // && sensor detects an object
        )
        {
            storageCount++;
            awaitingItem = false;
        }
        if (!awaitingItem // && sensor does not detect an object
        )
        {
            awaitingItem = true;
        }
    }

    public int storageAmount(){
        return storageCount;
    }

}
