package org.usfirst.frc.team4913.robot.subsystems;

import org.usfirst.frc.team4913.robot.RobotMap;
import org.usfirst.frc.team4913.robot.commands.GrabberOpen;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class Grabber extends Subsystem {

	// Put methods for controlling this subsystem
	// here. Call these from Commands.
	Spark grabberMotor = new Spark(RobotMap.GRABBER_MOTOR_PORT); //0 is a placeholder 
	DigitalInput grabberSwitch = new DigitalInput(8);//8 is a placeholder
	//True is pressed, False is unpressed
	public static double CLOSE_SPEEDCONSTANT = 0.2; // IN
	public static double OPEN_SPEEDCONSTANT = - 0.2; // OUT
	public static double STOP_SPEEDCONSTANT = 0.0;

	public void initDefaultCommand() {
		// Set the default command for a subsystem here.
		setDefaultCommand(new GrabberOpen());
	}

	public void close() {
		grabberMotor.set(CLOSE_SPEEDCONSTANT);
	}

	public void open() {
		if(grabberSwitch.get()) //limit switch is pressed
			grabberMotor.stopMotor();
		else //limit switch is unpressed
			grabberMotor.set(OPEN_SPEEDCONSTANT);
		 
	}

	public void stop() {
		grabberMotor.stopMotor();
	}
}
