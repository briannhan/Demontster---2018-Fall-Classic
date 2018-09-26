package org.usfirst.frc.team4913.robot.subsystems;

import org.usfirst.frc.team4913.robot.RobotMap;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class Arm extends Subsystem {

	// Put methods for controlling this subsystem
	// here. Call these from Commands.
	public static double ELEVATOR_UP_SPEED = 1.0;
	public static double ELEVATOR_DOWN_SPEED = -1.0;


	WPI_TalonSRX armMotor = new WPI_TalonSRX(RobotMap.ARM_MOTOR_ID);

	public void initDefaultCommand() {
		// Set the default command for a subsystem here.
		// setDefaultCommand(new MySpecialCommand());
	}

	public void up() {
		armMotor.set(ELEVATOR_UP_SPEED);
	}

	public void down() {
		armMotor.set(ELEVATOR_DOWN_SPEED);
	}

	public void stop() {
		armMotor.set(0);
	}
}
