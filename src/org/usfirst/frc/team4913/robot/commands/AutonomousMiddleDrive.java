package org.usfirst.frc.team4913.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

import static org.usfirst.frc.team4913.robot.Robot.*;

import org.usfirst.frc.team4913.robot.Robot;

/**
 *
 */
public class AutonomousMiddleDrive extends Command {

	Timer timer = new Timer();

	private boolean isFinished = false;

	private TURN direction;
	private boolean deliverCube = false;
	private boolean useVision = false;

	private static final double ACTUATOR_TIME = 1.6;
	private static final double INIT_WAIT_TIME = 2.5;
	private static final double INIT_FWD_TIME = 0.5; // Measure
	private static final double TURN_90_TIME = 0.5;
	private static final double POSITION_TIME = 1.5; // Measure
	private static final double APPROACH_TIME = 1.6; // Measure
	private static final double VISION_TIME = 3.0;
	private static final double DELIVER_CUBE = 3.0;
	private static final double ROTATOR_TIME = 0.55;
	private static final double ROTATOR_WAIT_TIME = 1.5;
	
	private double waitTime = INIT_WAIT_TIME;
	private double actuatorTime = waitTime + ACTUATOR_TIME;
	private double initFwdTime = actuatorTime + INIT_FWD_TIME;
	private double turn1stTime = initFwdTime + TURN_90_TIME;
	private double positionTime = turn1stTime + POSITION_TIME;
	private double turn2ndTime = positionTime + TURN_90_TIME;
	private double approachTime = turn2ndTime + APPROACH_TIME;
	private double visionMiddleTime = approachTime + VISION_TIME;
	private double rotatorTime = approachTime + ROTATOR_TIME;
	private double rotatorWaitTime = approachTime + ROTATOR_WAIT_TIME;
	private double deliverTime = rotatorWaitTime + DELIVER_CUBE;

	public AutonomousMiddleDrive(TURN direction, boolean deliverCube, boolean useVision) {
		requires(driveSubsystem);
//		requires(elevator);
		requires(rotator);
		requires(grabber);
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
		if (timerVal < waitTime)
			driveSubsystem.stopMotor();
		else if(timerVal >= waitTime && timerVal < actuatorTime)
			actuator.up();
		else if (timerVal >= actuatorTime && timerVal < initFwdTime) {
			actuator.stop();
			driveSubsystem.arcadeDrive(-0.7, 0.2);
		}
		else if (timerVal >= actuatorTime && timerVal < initFwdTime)
			driveSubsystem.arcadeDrive(-0.7, 0.2); //  forward
		else if (timerVal >= initFwdTime && timerVal < turn1stTime) { // initial turn
			if (direction == Robot.TURN.LEFT) {
				driveSubsystem.arcadeDrive(0.0, -0.85);// turn left
			} else {
				driveSubsystem.arcadeDrive(0.0, 0.7);// turn right
			}
		} else if (timerVal >= turn1stTime && timerVal < positionTime) {
			driveSubsystem.arcadeDrive(-0.7, 0.2); // 2nd forward
		} else if (timerVal >= positionTime && timerVal < turn2ndTime) { // turn back facing switch

			if (direction == Robot.TURN.LEFT) {
				driveSubsystem.arcadeDrive(0.0, 0.85);// turn right
			} else {
				driveSubsystem.arcadeDrive(0.0, -0.7); // turn left
			}
		} else if (timerVal >= turn2ndTime && timerVal < approachTime) {
//			elevator.up();
//			actuator.up();
			driveSubsystem.arcadeDrive(-0.6, 0.2); // 3rd forward
		}
		 else if (timerVal >= approachTime && timerVal < rotatorTime) {
				driveSubsystem.stopMotor();
				grabber.open();
				rotator.move(-0.95);
//				elevator.stop();
			}else if (timerVal >= rotatorTime && timerVal < rotatorWaitTime) {
				grabber.open();
				rotator.stop();
			}
			else if (timerVal >= rotatorWaitTime && timerVal < deliverTime) {
				grabber.close();
//				elevator.stop();
			}
		else
			isFinished = true;

	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		return isFinished;
	}

	// Called once after isFinished returns true
	protected void end() {
		grabber.stop();
		actuator.stop();
		rotator.stop();
		elevator.stop();
		driveSubsystem.stopMotor();
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
		end();
	}
}