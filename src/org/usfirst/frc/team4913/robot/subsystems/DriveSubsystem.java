/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team4913.robot.subsystems;

import org.usfirst.frc.team4913.robot.OI;
import org.usfirst.frc.team4913.robot.Robot;
import org.usfirst.frc.team4913.robot.RobotMap;
import org.usfirst.frc.team4913.robot.commands.Drive;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;

/**
 * An example subsystem. You can replace me with your own Subsystem.
 */
public class DriveSubsystem extends Subsystem {

	double ySpeed = 0;
	double yJoystickInput;
	double scaledyJoystickInput;
	double ySpeedScale = 0.05;// placeholder
	double yDiff;
	double scaledYDiff;

	WPI_TalonSRX rightRearMotor = new WPI_TalonSRX(RobotMap.RIGHT_REAR_MOTOR_ID);
	WPI_TalonSRX leftRearMotor = new WPI_TalonSRX(RobotMap.LEFT_REAR_MOTOR_ID);
	WPI_TalonSRX rightFrontMotor = new WPI_TalonSRX(RobotMap.RIGHT_FRONT_MOTOR_ID);
	WPI_TalonSRX leftFrontMotor = new WPI_TalonSRX(RobotMap.LEFT_FRONT_MOTOR_ID);

	SpeedControllerGroup leftGroup = new SpeedControllerGroup(leftFrontMotor, leftRearMotor);
	SpeedControllerGroup rightGroup = new SpeedControllerGroup(rightFrontMotor, rightRearMotor);
	DifferentialDrive robotDrive = new DifferentialDrive(leftGroup, rightGroup);

	DigitalInput dio7 = new DigitalInput(RobotMap.VISION_INPUT_7);
	DigitalInput dio8 = new DigitalInput(RobotMap.VISION_INPUT_8);
	DigitalInput dio9 = new DigitalInput(RobotMap.VISION_INPUT_9);

	public void initDefaultCommand() {
		setDefaultCommand(new Drive());
	}

	public void stopMotor() {
		robotDrive.stopMotor();
	}

	/**
	 * Drive autonomously based on the inputs from DIO
	 * 
	 * pin7 pin8 action --- false true veer left true false veer right true true
	 * straight false false slow
	 * 
	 * @param leftInput
	 * @param rightInput
	 */
	public void autoDrive() {
		if (dio9.get()) {
			stopMotor();
		} else if (!dio7.get() && dio8.get()) {
			// arcadeDrive(0.0, -1.0); // turn left
			veerLeft();
		} else if (dio7.get() && !dio8.get()) {
			// arcadeDrive(0.0, 1.0); // turn right
			veerRight();
		} else if (dio7.get() && dio8.get()) {
			arcadeDrive(-0.5, 0.0); // forward half-speed
		} else if (!dio7.get() && !dio8.get()) {
			arcadeDrive(-0.25, 0.0); // forward quarter-speed
		}
		Timer.delay(1.0);
	}

	private static final double VEER_TURN_CONSTANT = 0.5;
	private static final double VEER_DRIVE_CONSTANT = 0.5;

	/**
	 * change direction to left and drive forward slightly
	 */
	public void veerLeft() {
		System.out.println("started veering left");
		double turnTime = VEER_TURN_CONSTANT;
		double driveTime = VEER_TURN_CONSTANT + VEER_DRIVE_CONSTANT;
		Timer timer = new Timer();
		timer.reset();
		timer.start();
		double time = timer.get();
		while (time < turnTime) {
			arcadeDrive(0.0, -1.0); // turn left
		}
		while (time < driveTime) {
			arcadeDrive(-0.5, 0.0); // forward half-speed
		}
		while (time < driveTime + turnTime) {
			arcadeDrive(0.0, 1.0); // turn right
		}
		timer.stop();
		System.out.println("finished veering left");
	}

	public void veerRight() {
		double turnTime = VEER_TURN_CONSTANT;
		double driveTime = VEER_TURN_CONSTANT + VEER_DRIVE_CONSTANT;
		Timer timer = new Timer();
		timer.reset();
		timer.start();
		double time = timer.get();
		if (time < turnTime) {
			arcadeDrive(0.0, 1.0); // turn right
		} else if (time < driveTime) {
			arcadeDrive(-0.5, 0.0); // forward half-speed
		} else if (time < driveTime + turnTime) {
			arcadeDrive(0.0, -1.0); // turn left
		}
		timer.stop();
	}

	public void arcadeDrive() {
		yJoystickInput = OI.joystick.getY();
		yDiff = yJoystickInput - ySpeed;
		scaledYDiff = yDiff * ySpeedScale;
		ySpeed += scaledYDiff;
		robotDrive.arcadeDrive(ySpeed, OI.joystick.getX());
	}

	public void arcadeDrive(double ySpeed, double xSpeed) {
		robotDrive.arcadeDrive(ySpeed, xSpeed);
	}
}
