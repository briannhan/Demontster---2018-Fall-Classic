/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team4913.robot;

import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc.team4913.robot.commands.*;
import org.usfirst.frc.team4913.robot.subsystems.Actuator;
import org.usfirst.frc.team4913.robot.subsystems.Climber;
import org.usfirst.frc.team4913.robot.subsystems.DriveSubsystem;
import org.usfirst.frc.team4913.robot.subsystems.Arm;
import org.usfirst.frc.team4913.robot.subsystems.Grabber;
import org.usfirst.frc.team4913.robot.subsystems.Rotator;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.properties file in the
 * project.
 */
public class Robot extends TimedRobot {

	Preferences prefs;
	public static final DriveSubsystem driveSubsystem = new DriveSubsystem();
	public static final Grabber grabber = new Grabber();
	public static final Rotator rotator = new Rotator();
	public static final Actuator actuator = new Actuator();
	public static final Arm arm = new Arm();
	public static final Climber climber = new Climber();
	public static OI m_oi;

	Command m_autonomousCommand;
	SendableChooser<Command> m_chooser = new SendableChooser<>();

	public enum TURN {
		LEFT, RIGHT, STRAIGHT;
	}

	public enum DELIVERCUBE {
		YES, NO;
	}

	/**
	 * This function is run when the robot is first started up and should be used
	 * for any initialization code.
	 */
	@Override
	public void robotInit() {
		CameraServer.getInstance().startAutomaticCapture();
		m_oi = new OI();
		m_chooser.addDefault("Default Auto", new Drive());
		m_chooser.addObject("Position 1 STRAIGHT", new
				AutonomousOutsideDrive(TURN.STRAIGHT, false, false));
		m_chooser.addObject("Position 1 DELIVER", new
				AutonomousOutsideDrive(TURN.RIGHT, true, false));
		m_chooser.addObject("Position 2 LEFT", new
				AutonomousOutsideDrive(TURN.LEFT, true, false));
		m_chooser.addObject("Position 2 RIGHT", new
				AutonomousOutsideDrive(TURN.RIGHT, true, false));
		m_chooser.addObject("Position 2 NO DELIVER", new
				AutonomousOutsideDrive(TURN.STRAIGHT, false, false));
		m_chooser.addObject("Position 3 STRAIGHT", new
				AutonomousOutsideDrive(TURN.STRAIGHT, false, false));
		m_chooser.addObject("Position 3 DELIVER", new
				AutonomousOutsideDrive(TURN.LEFT, true, false));
		SmartDashboard.putData("Auto mode", m_chooser);
		SmartDashboard.putData(arm);
		SmartDashboard.putData(actuator);
		SmartDashboard.putData(rotator);
		SmartDashboard.putData(grabber);
		SmartDashboard.putData(climber);
		SmartDashboard.putData(driveSubsystem);
		SmartDashboard.putData("ElevatorUp", new ArmUp());
		SmartDashboard.putData("RotatorMove", new RotatorMove());
		SmartDashboard.putData("BlockIntake", new GrabberOpen());
		SmartDashboard.putData("BlockRelease", new GrabberClose());
		SmartDashboard.putData("HookDown", new HookDown());
		SmartDashboard.putData("HookUp", new HookUp());
		SmartDashboard.putData("RobotUp", new RobotUp());
		prefs = Preferences.getInstance();
	}

	/**
	 * This function is called once each time the robot enters Disabled mode. You
	 * can use it to reset any subsystem information you want to clear when the
	 * robot is disabled.
	 */
	@Override
	public void disabledInit() {

	}

	@Override
	public void disabledPeriodic() {
		Scheduler.getInstance().run();
	}

	/**
	 * This autonomous (along with the chooser code above) shows how to select
	 * between different autonomous modes using the dashboard. The sendable chooser
	 * code works with the Java SmartDashboard. If you prefer the LabVIEW Dashboard,
	 * remove all of the chooser code and uncomment the getString code to get the
	 * auto name from the text box below the Gyro
	 *
	 * <p>
	 * You can add additional auto modes by adding additional commands to the
	 * chooser code above (like the commented example) or additional comparisons to
	 * the switch structure below with additional strings & commands.
	 */
	@Override
	public void autonomousInit() {
		int robotPosition = prefs.getInt("robotPosition", 1);
		boolean useVision = prefs.getBoolean("useVision", false);
		boolean deliverCube = prefs.getBoolean("deliverCube", true);

		String gameData = DriverStation.getInstance().getGameSpecificMessage();
		SmartDashboard.putString("Game Data", gameData);
		SmartDashboard.putNumber("Robot Position", robotPosition);

		/* These are various autonomous drive options:
		1/3. Outside positions:
			if (deliverCube && color matches) deliver cube (turn left/right)
			else drive straight

		2. Middle position:
			if (deliverCube) turn left/right and deliver cube
			else delay? turn left OR right and breach line

		|
		|1 -----------|
		|           |---|
		|   |->---->| S |
		|   |       | W |
		|2 -|       | I |
		|   |       | T |
		|   |       | C |
		|   |->---->| H |
		|           |---|
		|3 -----------^
		|

		 */


		switch (robotPosition) {
		case 1: // left position
			if (deliverCube && gameData.charAt(0) == 'L') {
				m_autonomousCommand = new AutonomousOutsideDrive(TURN.RIGHT, true, useVision);
			} else
				m_autonomousCommand = new AutonomousOutsideDrive(TURN.RIGHT, false, useVision);
			break;
		case 2: // middle position
			if (deliverCube && gameData.charAt(0) == 'L')
				m_autonomousCommand = new AutonomousMiddleDrive(TURN.LEFT, true, useVision);
			else if (deliverCube && gameData.charAt(0) == 'R')
				m_autonomousCommand = new AutonomousMiddleDrive(TURN.RIGHT, true, useVision);
			else
				m_autonomousCommand = new AutonomousMiddleDrive(TURN.STRAIGHT, false, useVision);
			break;
		case 3: // right position
			if (deliverCube && gameData.charAt(0) == 'R') {
				m_autonomousCommand = new AutonomousOutsideDrive(TURN.LEFT, true, useVision);
			} else
				m_autonomousCommand = new AutonomousOutsideDrive(TURN.LEFT, false, useVision);
			break;
		}

		// test code, remove
		/*
		 * m_autonomousCommand = m_chooser.getSelected(); m_autonomousCommand.start();
		 */

		// schedule the autonomous command (example)
		if (m_autonomousCommand != null) {
			m_autonomousCommand.start();
		}

	}

	/**
	 * This function is called periodically during autonomous.
	 */
	@Override
	public void autonomousPeriodic() {
		Scheduler.getInstance().run();
	}

	@Override
	public void teleopInit() {
		// This makes sure that the autonomous stops running when
		// teleop starts running. If you want the autonomous to
		// continue until interrupted by another command, remove
		// this line or comment it out.
		if (m_autonomousCommand != null) {
			m_autonomousCommand.cancel();
		}
	}

	/**
	 * This function is called periodically during operator control.
	 */
	@Override
	public void teleopPeriodic() {
		Scheduler.getInstance().run();
		SmartDashboard.putNumber("left trigger", OI.xboxController.getTriggerAxis(Hand.kLeft));
		SmartDashboard.putNumber("right trigger", OI.xboxController.getTriggerAxis(Hand.kRight));
		SmartDashboard.putNumber("left button", OI.xboxController.getY(Hand.kLeft));
		SmartDashboard.putNumber("right button", OI.xboxController.getY(Hand.kRight));
	}

	/**
	 * This function is called periodically during test mode.
	 */
	@Override
	public void testPeriodic() {
	}
}