package org.usfirst.frc.team4913.robot.subsystems;

import org.usfirst.frc.team4913.robot.RobotMap;

import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class Actuator extends Subsystem {

	// Put methods for controlling this subsystem
	// here. Call these from Commands.
	// Left Trigger from 0 to 1
	// Right Trigger from 0 to 1
	public static double ACTUATOR_UP_SPEED = 1.0;
	public static double ACTUATOR_DOWN_SPEED = -0.85;
	Spark actuatorMotor = new Spark(RobotMap.ACTUATOR_MOTOR_PORT);

	public void initDefaultCommand() {
		// Set the default command for a subsystem here.
		// setDefaultCommand(new MySpecialCommand());
	}

	public void up() {
		actuatorMotor.set(ACTUATOR_UP_SPEED);
	}

	public void down() {
		actuatorMotor.set(ACTUATOR_DOWN_SPEED);
	}

	public void stop() {
		actuatorMotor.set(0);
	}
}
