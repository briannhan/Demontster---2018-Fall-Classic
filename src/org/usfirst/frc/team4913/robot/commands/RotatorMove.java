package org.usfirst.frc.team4913.robot.commands;

import static org.usfirst.frc.team4913.robot.OI.xboxController;
import static org.usfirst.frc.team4913.robot.Robot.rotator;

import org.usfirst.frc.team4913.robot.RobotMap;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class RotatorMove extends Command {
	
	private Encoder enc;
	private static final int DISTANCE_PER_PULSE = 1;
	
	public RotatorMove() {
		// Use requires() here to declare subsystem dependencies
		// eg. requires(chassis);
		super("RotatorMove");
		requires(rotator);
	}

	// Called just before this Command runs the first time
	protected void initialize() {
//		/***** Encoder SetUp *******/
//		enc = new Encoder(RobotMap.ENC_SOURCE_1, RobotMap.ENC_SOURCE_2);
//		enc.setDistancePerPulse(DISTANCE_PER_PULSE);
//		enc.reset();
		//enc.setReverseDirection(true);
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		rotator.move(xboxController.getY(Hand.kRight));
//		System.out.println("count: " + enc.get());
//		System.out.println("distance: " + enc.getDistance());
//		System.out.println("direction: " + enc.getDirection());
//		System.out.println("raw: " + enc.getRaw());
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		return false;
	}

	// Called once after isFinished returns true
	protected void end() {
		rotator.stop();
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
		end();
	}
}
