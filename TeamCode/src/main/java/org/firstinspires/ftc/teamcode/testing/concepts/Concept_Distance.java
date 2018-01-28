package org.firstinspires.ftc.teamcode.testing.concepts;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.UltrasonicSensor;

import org.firstinspires.ftc.teamcode.hardware.bots.RAWRXDBot;
import org.firstinspires.ftc.teamcode.hardware.commands.CommandAutoStack;
import org.firstinspires.ftc.teamcode.hardware.commands.CommandUltrasonicDrive;
import org.firstinspires.ftc.teamcode.hardware.subsystems.DistanceDetection;
import org.firstinspires.ftc.teamcode.lib.auto.DogeAutoOpMode;

/**
 * Created by Victo on 1/23/2018.
 */
@Autonomous(name="Conc Distance", group="RAWRXD_CONC")

public class Concept_Distance extends DogeAutoOpMode {

    @Override
    public void runOpMode() throws InterruptedException {
       // DistanceDetection distanceDetection = new DistanceDetection(hardwareMap, "ultra");
        this.bot = new RAWRXDBot(hardwareMap);
        waitForStart();

        if (opModeIsActive()){

            //new CommandUltrasonicDrive(this, 0.4, 0, 80).Run();
        }
    }
}
