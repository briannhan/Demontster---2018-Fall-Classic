package org.usfirst.frc.team4913.robot.commands;

import static org.usfirst.frc.team4913.robot.OI.xboxController;
import static org.usfirst.frc.team4913.robot.OI.joystick;
import static org.usfirst.frc.team4913.robot.Robot.grabber;

import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class GrabberClose extends Command {

	private static final double TRIGGER_THRESHOLD = 0.1;

	public GrabberClose() {

		// Use requires() here to declare subsystem dependencies
		// eg. requires(chassis);
		super("BlockRelease");
		requires(grabber);
	}

	// Called just before this Command runs the first time
	protected void initialize() {
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		grabber.close();
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		return false;
	}

	// Called once after isFinished returns true
	protected void end() {
		grabber.stop();
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
		end();
	}
}
