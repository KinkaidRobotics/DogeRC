package org.firstinspires.ftc.teamcode.auto;

import com.disnodeteam.dogecv.CameraViewDisplay;
import com.disnodeteam.dogecv.detectors.JewelDetector;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.RelicRecoveryVuMark;
import org.firstinspires.ftc.teamcode.hardware.commands.CommandGyroDrive;
import org.firstinspires.ftc.teamcode.hardware.commands.CommandGyroTurn;
import org.firstinspires.ftc.teamcode.hardware.commands.CommandLiftToPosition;
import org.firstinspires.ftc.teamcode.hardware.commands.CommandUltrasonicDrive;
import org.firstinspires.ftc.teamcode.hardware.commands.CommandWait;
import org.firstinspires.ftc.teamcode.hardware.sensors.VuforiaHardware;
import org.firstinspires.ftc.teamcode.lib.auto.DogeAuto;
import org.firstinspires.ftc.teamcode.lib.auto.DogeAutoOpMode;


/**
 * Created by Victo on 1/20/2018.
 */

public class AutoRedTop extends DogeAuto {
    public AutoRedTop(DogeAutoOpMode parent) {
        super(parent);
    }

    public JewelDetector jewelDetector;
    public VuforiaHardware vuforiaHardware;

    private JewelDetector.JewelOrder jewelOrder;

    @Override
    public void Init() {

        bot.phoneServo.setJewelPos();

        jewelDetector = new JewelDetector();
        jewelDetector.downScaleFactor = 0.4;
        jewelDetector.ratioWeight = 30;
        jewelDetector.perfectRatio = 1.0;
        jewelDetector.areaWeight = 0.003;
        jewelDetector.maxDiffrence = 200;
        jewelDetector.debugContours = true;
        jewelDetector.detectionMode = JewelDetector.JewelDetectionMode.PERFECT_AREA;
        jewelDetector.perfectArea = 5550;
        jewelDetector.init(opMode.hardwareMap.appContext, CameraViewDisplay.getInstance());

        vuforiaHardware = new VuforiaHardware();


        jewelDetector.enable();

        while(!opMode.isStarted()){
            jewelOrder = jewelDetector.getLastOrder();
        }
    }

    @Override
    public void Run() {
        bot.phoneServo.setPictoPos();
        bot.jewelArm.Down();
        bot.grabbers.closeGrabbers();
        jewelDetector.disable();
        vuforiaHardware.Init(opMode.hardwareMap);

        new CommandLiftToPosition(opMode,800).Run();

        RelicRecoveryVuMark vuMark = RelicRecoveryVuMark.UNKNOWN;
        ElapsedTime timer = new ElapsedTime();
        while(vuMark == RelicRecoveryVuMark.UNKNOWN && timer.seconds() <0.5){
            vuMark = vuforiaHardware.getVuMark();
        }
        vuforiaHardware.Stop();

        if(jewelOrder == JewelDetector.JewelOrder.RED_BLUE){
            new CommandGyroTurn(opMode,0.3,10).Run();
            bot.jewelArm.Up();
            new CommandGyroTurn(opMode,0.3,0).Run();
        }
        if(jewelOrder == JewelDetector.JewelOrder.UNKNOWN){

            bot.jewelArm.Up();

        }

        new CommandGyroDrive(opMode,0.2,0,12).Run();
        bot.jewelArm.Up();
        new CommandGyroTurn(opMode,0.2,270).Run();

        new CommandWait(opMode, 0.3);

        switch(vuMark){
            case RIGHT:
                new CommandUltrasonicDrive(opMode,0.2,0,38).Run();
                break;
            case CENTER:
                new CommandUltrasonicDrive(opMode,0.2,0,44).Run();
                break;
            case LEFT:
                new CommandUltrasonicDrive(opMode,0.2,0,50).Run();
                break;
            case UNKNOWN:
                new CommandUltrasonicDrive(opMode,0.2,0,44).Run();
                break;
        }


        new CommandGyroTurn(opMode, 0.3,0).Run();
        new CommandGyroDrive(opMode,0.4,0,5).Run();
        bot.grabbers.openGrabbers();
        new CommandGyroDrive(opMode, 0.3,0, -6).Run();





    }

    @Override
    public void Stop() {


    }
}
