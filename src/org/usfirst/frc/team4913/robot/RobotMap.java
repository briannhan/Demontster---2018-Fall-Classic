/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team4913.robot;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {

	// Drive motor ports
	public static int RIGHT_REAR_MOTOR_ID = 0;
	public static int LEFT_REAR_MOTOR_ID = 1;
	public static int RIGHT_FRONT_MOTOR_ID = 2;
	public static int LEFT_FRONT_MOTOR_ID = 3;

	// limit switches for elevator
	//public static int SWITCH_UP_PORT = 0;
	//public static int SWITCH_DOWN_PORT = 1;

	public static int GRABBER_MOTOR_PORT = 0;
	
	public static int ROTATOR_MOTOR_ID = 4;
	public static int CLIMBER_MOTOR_ID = 5;
	public static int ARM_MOTOR_ID = 6;

	public static int VISION_INPUT_7 = 7;
	public static int VISION_INPUT_8 = 8;
	public static int VISION_INPUT_9 = 9;

	public static int XBOX_CONTROLLER_PORT = 0;
	public static int JOYSTICK_PORT = 1;
	
	//Encoder Ports
	public static int ENC_SOURCE_1 = 1;
	public static int ENC_SOURCE_2 = 2;
}
