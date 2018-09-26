package org.usfirst.frc.team4913.robot.subsystems;

import org.usfirst.frc.team4913.robot.RobotMap;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class Climber extends Subsystem {

	// Put methods for controlling this subsystem
	// here. Call these from Commands.
	// Left Trigger from 0 to 1
	// Right Trigger from 0 to 1

	WPI_TalonSRX num1Motor = new WPI_TalonSRX(RobotMap.CLIMBER_NUM1_MOTOR_PORT);
	WPI_TalonSRX num2Motor = new WPI_TalonSRX(RobotMap.CLIMBER_NUM2_MOTOR_PORT);

	public static double UP_SPEEDCONSTANT = 1.0;
	public static double DOWN_SPEEDCONSTANT = -0.5;
	public int currentHeight = 0;

	public void initDefaultCommand() {
		// Set the default command for a subsystem here.
		// setDefaultCommand(new MySpecialCommand());

	}

	public void hookUp() {
		num1Motor.set(UP_SPEEDCONSTANT);
		currentHeight++;
	}

	public void hookDown() {
		if(currentHeight > 5) {
		num1Motor.set(DOWN_SPEEDCONSTANT);
		currentHeight--;
		}
	}

	public void robotUp() {
		num2Motor.set(UP_SPEEDCONSTANT);
	}

	public void stop() {
		num1Motor.set(0);
		num2Motor.set(0);
	}
}
