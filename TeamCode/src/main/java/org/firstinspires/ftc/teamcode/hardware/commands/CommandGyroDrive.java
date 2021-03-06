package org.firstinspires.ftc.teamcode.hardware.commands;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.teamcode.lib.auto.CommandBase;
import org.firstinspires.ftc.teamcode.lib.auto.DogeAutoOpMode;
import org.firstinspires.ftc.teamcode.lib.control.PIDController;
import org.firstinspires.ftc.teamcode.lib.logging.UniLogger;

/**
 * Created by Victo on 1/17/2018.
 */

public class CommandGyroDrive extends CommandBase {
    private double angle = 0;
    private double speed = 0.5;
    private double distance = 1000;
    private PIDController pidController;


    int     newLeftTarget1, newLeftTarget2;
    int     newRightTarget1, newRightTarget2;
    int     moveCounts;
    double  max;
    double  error;
    double  steer;
    double  leftSpeed;
    double  rightSpeed;

    public CommandGyroDrive(DogeAutoOpMode opMode, double speed, double angle, double distance) {
        super(opMode);

        this.speed = speed;
        this.angle = angle;
        this.distance = bot.convertEncoder(distance);
        pidController = new PIDController(bot.P, bot.I, bot.D);

        if(bot.driveFrame == null){
            UniLogger.Log("DOGE-AUTO","Command 'CommandGyroDrive' requires DriveTrain, but it is null");
        }

        bot.driveFrame.setMotorModes(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

    }


    @Override
    public void Start() {
        moveCounts = (int) -distance;

        bot.driveFrame.setMotorModes(DcMotor.RunMode.RUN_TO_POSITION);
        newLeftTarget1 = bot.driveFrame.allMotors[0].getCurrentPosition() + moveCounts;
        newLeftTarget2 = bot.driveFrame.allMotors[1].getCurrentPosition() + moveCounts;
        newRightTarget1 = bot.driveFrame.allMotors[2].getCurrentPosition() + moveCounts;
        newRightTarget2 = bot.driveFrame.allMotors[3].getCurrentPosition() + moveCounts;

        // Set Target and Turn On RUN_TO_POSITION
        bot.driveFrame.allMotors[0].setTargetPosition(newLeftTarget1);
        bot.driveFrame.allMotors[1].setTargetPosition(newLeftTarget2);
        bot.driveFrame.allMotors[2].setTargetPosition(newRightTarget1);
        bot.driveFrame.allMotors[3].setTargetPosition(newRightTarget2);


        // start motion.
        speed = Range.clip(Math.abs(speed), 0.0, 1.0);
        bot.driveFrame.setLeftPower(speed);
        bot.driveFrame.setRightPower(speed);
    }

    @Override
    public void Loop() {

        double error = 0.4* bot.navigationHardware.getError(angle);
        steer = Range.clip(error, -1, 1);
        if(bot.navigationHardware.getError(angle) < bot.PID_THRESH){
            steer = 0;
        }
        // if driving in reverse, the motor correction also needs to be reversed
        if (distance < 0)
            steer *= -1.0;

        leftSpeed = speed - steer;
        rightSpeed = speed + steer;

        // Normalize speeds if either one exceeds +/- 1.0;
        max = Math.max(Math.abs(leftSpeed), Math.abs(rightSpeed));
        if (max > 1.0)
        {
            leftSpeed /= max;
            rightSpeed /= max;
        }
        if(leftSpeed < 0){
            leftSpeed = Range.clip(leftSpeed, -1.0, -0.1);
        }
        if(leftSpeed > 0){
            leftSpeed = Range.clip(leftSpeed, 0.1, 1.0);
        }
        if(rightSpeed < 0){
            rightSpeed = Range.clip(rightSpeed, -1.0, -0.1);
        }
        if(rightSpeed > 0){
            rightSpeed = Range.clip(rightSpeed, 0.1, 1.0);
        }
        bot.driveFrame.setLeftPower(leftSpeed);
        bot.driveFrame.setRightPower(rightSpeed);

        if(opMode != null){
            opMode.telemetry.addData("ERROR", bot.P * bot.navigationHardware.getError(angle));
            opMode.telemetry.addData("Raw Target" ,angle);

            opMode.telemetry.addData("STEER", steer);
            opMode.telemetry.addData("Powers", leftSpeed + "/"+rightSpeed);
            opMode.telemetry.addData("Motor1" , bot.driveFrame.allMotors[0].getCurrentPosition() + " / " + bot.driveFrame.allMotors[0].getTargetPosition());
            opMode.telemetry.addData("Motor2" ,bot.driveFrame.allMotors[1].getCurrentPosition() + " / " + bot.driveFrame.allMotors[1].getTargetPosition());
            opMode.telemetry.addData("Motor3" ,bot.driveFrame.allMotors[2].getCurrentPosition() + " / " + bot.driveFrame.allMotors[2].getTargetPosition());
            opMode.telemetry.addData("Motor4" ,bot.driveFrame.allMotors[3].getCurrentPosition() + " / " + bot.driveFrame.allMotors[3].getTargetPosition());
            opMode.telemetry.addData("MotorBusy1", bot.driveFrame.allMotors[0].isBusy());
            opMode.telemetry.addData("MotorBusy2", bot.driveFrame.allMotors[1].isBusy());
            opMode.telemetry.addData("MotorBusy3", bot.driveFrame.allMotors[2].isBusy());
            opMode.telemetry.addData("MotorBusy4", bot.driveFrame.allMotors[3].isBusy());
            opMode.telemetry.update();
        }
    }

    @Override
    public void Stop() {
        bot.driveFrame.setLeftPower(0);
        bot.driveFrame.setRightPower(0);

        bot.driveFrame.setMotorModes(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    @Override
    public boolean IsTaskRunning() {
        return bot.driveFrame.isMotorBusy();
    }


}
