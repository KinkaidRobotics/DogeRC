package org.firstinspires.ftc.teamcode.hardware.commands;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.teamcode.lib.auto.CommandBase;
import org.firstinspires.ftc.teamcode.lib.auto.DogeAutoOpMode;
import org.firstinspires.ftc.teamcode.lib.control.PIDController;
import org.firstinspires.ftc.teamcode.lib.logging.UniLogger;

/**
 * Created by Victo on 1/22/2018.
 */

public class CommandUltrasonicDrive extends CommandBase {
    private double angle = 0;
    private double speed = 0.5;
    private double distance = 12;
    private PIDController pidController;

    int startingPosition = 0;
    int saftey = 0;
    int moveCountsAvg;
    double max;
    double error;
    double steer;
    double leftSpeed;
    double rightSpeed;



    public CommandUltrasonicDrive(DogeAutoOpMode opMode, double speed, double angle, double distance, double saftey) {
        super(opMode);

        this.speed = speed;
        this.angle = angle;
        this.saftey = bot.convertEncoder(saftey);
        this.distance = distance;
        pidController = new PIDController(bot.P, bot.I, bot.D);

        if (bot.driveFrame == null) {
            UniLogger.Log("DOGE-AUTO", "Command 'CommandGyroDrive' requires DriveTrain, but it is null");
        }

        bot.driveFrame.setMotorModes(DcMotor.RunMode.STOP_AND_RESET_ENCODER);


    }


    @Override
    public void Start() {

        startingPosition = bot.driveFrame.allMotors[0].getCurrentPosition();
        bot.driveFrame.setMotorModes(DcMotor.RunMode.RUN_USING_ENCODER);


        // start motion.
        speed = Range.clip(Math.abs(speed), 0.0, 1.0);
        bot.driveFrame.setLeftPower(-speed);
        bot.driveFrame.setRightPower(-speed);



    }

    @Override
    public void Loop() {
        opMode.telemetry.addData("DISTAMCCE",bot.distanceDetection.getDistance());
        opMode.telemetry.update();

        steer = Range.clip(0.04 * bot.navigationHardware.getError(angle), -1, 1);
        steer = 0;
        rightSpeed = speed - steer;
        leftSpeed = speed + steer;

        // Normalize speeds if either one exceeds +/- 1.0;
        max = Math.max(Math.abs(leftSpeed), Math.abs(rightSpeed));
        if (max > 1.0) {
            leftSpeed /= max;
            rightSpeed /= max;
        }

        bot.driveFrame.setLeftPower(-leftSpeed);
        bot.driveFrame.setRightPower(-rightSpeed);

        if (opMode != null) {


            opMode.telemetry.update();
        }
        moveCountsAvg =bot.driveFrame.allMotors[0].getCurrentPosition();

        if(Math.abs(saftey - moveCountsAvg) < 50){
            Stop();
        }

    }

    @Override
    public void Stop() {
        bot.driveFrame.setLeftPower(0);
        bot.driveFrame.setRightPower(0);

        bot.driveFrame.setMotorModes(DcMotor.RunMode.RUN_USING_ENCODER);
        opMode.telemetry.addData("Stopped At",bot.distanceDetection.getDistance());
        opMode.telemetry.update();

    }

    @Override
    public boolean IsTaskRunning() {
        return (bot.distanceDetection.getDistance() < distance);
    }
}
