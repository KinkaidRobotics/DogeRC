package org.firstinspires.ftc.teamcode.auto;

import com.disnodeteam.dogecv.CameraViewDisplay;
import com.disnodeteam.dogecv.detectors.JewelDetector;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.hardware.commands.CommandEncoderDrive;
import org.firstinspires.ftc.teamcode.hardware.commands.CommandWait;
import org.firstinspires.ftc.teamcode.lib.auto.DogeAuto;
import org.firstinspires.ftc.teamcode.lib.auto.DogeAutoOpMode;

/**
 * Created by Victo on 1/4/2018.
 */
@Autonomous(name="Martin run this", group="AUTO")
public class JustShutupAlready extends DogeAutoOpMode {

    JewelDetector jewelDetector = new JewelDetector();
    @Override
    public void runOpMode() throws InterruptedException {
        super.runOpMode();
        jewelDetector = new JewelDetector();
        jewelDetector.downScaleFactor = 0.4;
        jewelDetector.ratioWeight = 30;
        jewelDetector.perfectRatio = 1.0;
        jewelDetector.areaWeight = 0.003;
        jewelDetector.maxDiffrence = 200;
        jewelDetector.debugContours = true;
        jewelDetector.detectionMode = JewelDetector.JewelDetectionMode.PERFECT_AREA;
        jewelDetector.perfectArea = 5550;
        jewelDetector.init(hardwareMap.appContext, CameraViewDisplay.getInstance());
        jewelDetector.enable();
        waitForStart();

        if(opModeIsActive()){
            telemetry.addData("Jewel",jewelDetector.getLastOrder().toString());
            telemetry.update();
            if(jewelDetector.getLastOrder() == JewelDetector.JewelOrder.RED_BLUE){
                bot.jewelArm.Down();
                new CommandWait(this, 2.0);
                new CommandEncoderDrive(this,0.5, 2).Run();
                bot.jewelArm.Up();
            }
            new CommandEncoderDrive(this,0.5, 24).Run();
        }
        jewelDetector.disable();


    }
}
