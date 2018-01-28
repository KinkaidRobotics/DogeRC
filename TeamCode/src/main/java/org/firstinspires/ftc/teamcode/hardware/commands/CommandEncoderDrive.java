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

public class CommandEncoderDrive extends CommandBase {

    private double speed = 0.5;
    private double distance = 1;


    int     newLeftTarget1, newLeftTarget2;
    int     newRightTarget1, newRightTarget2;
    int     moveCounts;

    double  leftSpeed;
    double  rightSpeed;

    public CommandEncoderDrive(DogeAutoOpMode opMode, double speed, double distance) {
        super(opMode);

        this.speed = speed;

        this.distance = bot.convertEncoder(distance);


        if(bot.driveFrame == null){
            UniLogger.Log("DOGE-AUTO","Command 'CommandEncoderDrive' requires DriveTrain, but it is null");
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

        bot.driveFrame.setLeftPower(speed);
        bot.driveFrame.setRightPower(speed);

        if(opMode != null){

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
