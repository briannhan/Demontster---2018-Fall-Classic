package org.usfirst.frc.team4913.robot.commands;

import static org.usfirst.frc.team4913.robot.Robot.actuator;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class ActuatorDown extends Command {

	public ActuatorDown() {
		// Use requires() here to declare subsystem dependencies
		// eg. requires(chassis);
		super("ActuatorDown");
		requires(actuator);
	}

	// Called just before this Command runs the first time
	protected void initialize() {
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		actuator.down();
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		return false;
	}

	// Called once after isFinished returns true
	protected void end() {
		actuator.stop();
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
		end();
	}
}
