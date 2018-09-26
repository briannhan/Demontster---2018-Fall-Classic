package org.usfirst.frc.team4913.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

import static org.usfirst.frc.team4913.robot.Robot.*;

import org.usfirst.frc.team4913.robot.Robot;

/**
 *
 */
public class AutonomousOutsideDrive extends Command {

	Timer timer = new Timer();

	private boolean isFinished = false;

	private TURN direction;
	private boolean deliverCube = false;
	private boolean useVision = false;

	private static final double ACTUATOR_TIME = 1.6;
	private static final double VISION_TIME = 3.0;
	private static final double DELIVER_CUBE = 3.0;
	private static final double GO_STRAIGHT = 3.0; // Measure
	private static final double GO_STRAIGHT_SECOND = 1.5; // Measure
	private static final double APPROCH_TIME = 1.0; // Measure
	private static final double TURN_90_TIME = 0.4;
	private static final double ROTATOR_TIME = 0.45;
	private static final double ACROSS_FIELD_TIME = 1.0;
	private static final double ACROSS_FIELD_APPROACH_TIME = 0.5;
	private static final double ROTATOR_WAIT_TIME = 1.5;

	private double visionSidesTime = GO_STRAIGHT + VISION_TIME;
//	private double actuatorUpTime = ACTUATOR_UP_TIME;=
	private double actuatorTime = ACTUATOR_TIME;
	private double go_straight = actuatorTime + GO_STRAIGHT;
	private double turnTime = go_straight + TURN_90_TIME;
	private double approachTime = turnTime + APPROCH_TIME;
	private double rotatorTime = approachTime + ROTATOR_TIME;
	private double rotatorWaitTime = approachTime + ROTATOR_WAIT_TIME;
	private double deliverTime = rotatorWaitTime + DELIVER_CUBE;
	private double go_straight_sec = go_straight + GO_STRAIGHT_SECOND;
	private double across_field_turn_1 = go_straight_sec + TURN_90_TIME;
	private double across_field_straight = across_field_turn_1 + ACROSS_FIELD_TIME;
	private double across_field_turn_2 = across_field_straight + TURN_90_TIME;
	private double across_field_approachTime = across_field_turn_2 + ACROSS_FIELD_APPROACH_TIME;

	public AutonomousOutsideDrive(TURN direction, boolean deliverCube, boolean useVision) {
		requires(driveSubsystem);
		requires(grabber);
		requires(rotator);
		requires(actuator);
//		requires(elevator);
		this.direction = direction;
		this.deliverCube = deliverCube;
		this.useVision = useVision;
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		timer.reset();
		timer.start();
	}

	protected void execute() {
		double timerVal = timer.get();
		
		if(timerVal < actuatorTime)
			actuator.up();
		else if (timerVal >= actuatorTime && timerVal < go_straight) {
			actuator.stop();
			driveSubsystem.arcadeDrive(-0.7, 0.2);
		}
		else{
			if (deliverCube) {
			if (timerVal >= GO_STRAIGHT && timerVal < turnTime) {
//				elevator.up();
				if (direction == Robot.TURN.LEFT) {
					driveSubsystem.arcadeDrive(0.0, -0.9);// turn left
				} else {
					driveSubsystem.arcadeDrive(0.0, 0.9);// turn right
				}
			} else if (timerVal >= turnTime && timerVal < approachTime) {
				driveSubsystem.arcadeDrive(-0.5, 0.2); // forward
//				intaker.intakeBlock();
//				elevator.up();
			} else if (timerVal >= approachTime && timerVal < rotatorTime) {
				driveSubsystem.stopMotor();
				grabber.open();
				rotator.move(-1);
//				elevator.stop();
			}else if (timerVal >= rotatorTime && timerVal < rotatorWaitTime) {
				rotator.stop();
			}
			else if (timerVal >= rotatorWaitTime && timerVal < deliverTime) {
				grabber.close();
//				elevator.stop();
			}
			else
				isFinished = true;
		} else {
			if (timerVal >= go_straight && timerVal < go_straight_sec)
				driveSubsystem.arcadeDrive(-0.7, 0.2); // forward
			else if(timerVal >= go_straight_sec && timerVal < across_field_turn_1) {
				if (direction == Robot.TURN.LEFT) {
					driveSubsystem.arcadeDrive(0.0, -0.9);// turn left
				} else {
					driveSubsystem.arcadeDrive(0.0, 0.9);// turn right
				}
			}else if(timerVal >= across_field_turn_1 && timerVal < across_field_straight)
				driveSubsystem.arcadeDrive(-0.7, 0.2);
//			else if(timerVal >= across_field_straight && timerVal < across_field_turn_2) {
//				if (direction == Robot.TURN.LEFT) {
//					driveSubsystem.arcadeDrive(0.0, -1.0);// turn left
//				} else {
//					driveSubsystem.arcadeDrive(0.0, 1.0);// turn right
//				}
//			}
			else
				isFinished = true;
		}
	}

	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		return isFinished;
	}

	// Called once after isFinished returns true
	protected void end() {
//		rotator.stop();
//		actuator.stop();
//		elevator.stop();
		grabber.stop();
		driveSubsystem.stopMotor();
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
		end();
	}
}