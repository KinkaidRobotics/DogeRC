package org.firstinspires.ftc.teamcode.teleop;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.lib.control.Controller;
import org.firstinspires.ftc.teamcode.lib.auto.DogeAutoOpMode;

/**
 * Created by jcm12 on 1/19/2018.
 */
@TeleOp(name="Main Teleop", group="COMP")
public class MainTeleop extends DogeAutoOpMode {

    private double speed = 1.0;
    private Controller controller1;
    private Controller controller2;
    @Override
    public void runOpMode() throws InterruptedException {
        super.runOpMode();

        controller1 = new Controller(gamepad1);
        controller2 = new Controller(gamepad2);
        bot.lift.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        waitForStart();
        while(opModeIsActive()){
            controller1.Update();
            controller2.Update();

            telemetry.addData("Distance", bot.distanceDetection.getDistance());
            telemetry.update();

            if(gamepad1.left_trigger > 0.1 || gamepad1.left_bumper){
                speed = 0.2;
            } else if (gamepad1.right_bumper || gamepad1.right_trigger > 0.1) {
                speed = 0.5;
            }else{
                speed = 1.0;
            }


            bot.driveFrame.setLeftPower(gamepad1.left_stick_y * speed);
            bot.driveFrame.setRightPower(gamepad1.right_stick_y  * speed);

            if(controller2.YState == Controller.ButtonState.JUST_PRESSED){
                bot.jewelArm.Up();
            }

            if(controller2.AState == Controller.ButtonState.JUST_PRESSED){
                bot.grabbers.closeGrabbers();
            }

            if(controller2.BState == Controller.ButtonState.JUST_PRESSED){
                bot.grabbers.openGrabbers();
            }

            if(controller2.XState == Controller.ButtonState.JUST_PRESSED){
                bot.grabbers.softOpenGrabbers();
            }


            bot.lift.liftInput(-gamepad2.left_stick_y , true);

            if(controller2.DPadUp == Controller.ButtonState.JUST_PRESSED){
                bot.lift.resetLift();
            }


        }

    }
}
