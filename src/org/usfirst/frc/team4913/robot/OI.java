/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team4913.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import org.usfirst.frc.team4913.robot.commands.*;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {
	//// CREATING BUTTONS
	// One type of button is a joystick button which is any button on a
	//// joystick.
	// You create one by telling it which joystick it's on and which button
	// number it is.
	// Joystick stick = new Joystick(port);
	// Button button = new JoystickButton(stick, buttonNumber);
	public static XboxController xboxController = new XboxController(RobotMap.XBOX_CONTROLLER_PORT);

	public static Joystick joystick = new Joystick(RobotMap.JOYSTICK_PORT);

	public Button joystickActuatorDown = new JoystickButton(joystick, 4);
	public Button joystickActuatorUp = new JoystickButton(joystick, 5);

	public Button joystickElevatorUp = new JoystickButton(joystick, 6);
	public Button joystickElevatorDown = new JoystickButton(joystick, 7);

	// left and right bumpers
	public Button xboxButton5 = new JoystickButton(xboxController, 5);
	public Button xboxButton6 = new JoystickButton(xboxController, 6);

	// public Button joystickRelease = new JoystickButton(joystick, 1);
	public Button joystickHookUp = new JoystickButton(joystick, 3);
	public Button joystickClimb = new JoystickButton(joystick, 2);
	public Button joystickClimberStop = new JoystickButton(joystick, 10);

	// There are a few additional built in buttons you can use. Additionally,
	// by subclassing Button you can create custom triggers and bind those to
	// commands the same as any other Button.

	public OI() {
		joystickActuatorUp.whileHeld(new ActuatorUp());
		joystickActuatorDown.whileHeld(new ActuatorDown());

		joystickElevatorUp.whileHeld(new ArmUp());
		joystickElevatorDown.whileHeld(new ArmDown());

		xboxButton5.whileHeld(new GrabberClose());
		//xboxButton6.whileHeld(new BlockRelease());
		// joystickRelease.whileHeld(new BlockRelease());

		joystickHookUp.whileHeld(new HookUp());
		joystickHookUp.whenReleased(new HookDown());
		joystickClimb.whileHeld(new RobotUp());
		joystickClimberStop.whileHeld(new HookStop());
		/*
		 * xboxButton5.toggleWhenActive(new BlockIntake());
		 * xboxButton6.toggleWhenActive(new BlockIntake());
		 */
	}

	//// TRIGGERING COMMANDS WITH BUTTONS
	// Once you have a button, it's trivial to bind it to a button in one of
	// three ways:

	// xButton.whenPressed(new GrabBlock());
	// Start the command when the button is pressed and let it run the command
	// until it is finished as determined by it's isFinished method.
	// button.whenPressed(new ExampleCommand());

	// Run the command while the button is being held down and interrupt it once
	// the button is released.
	// button.whileHeld(new ExampleCommand());

	// Start the command when the button is released and let it run the command
	// until it is finished as determined by it's isFinished method.
	// button.whenReleased(new ExampleCommand());
}
