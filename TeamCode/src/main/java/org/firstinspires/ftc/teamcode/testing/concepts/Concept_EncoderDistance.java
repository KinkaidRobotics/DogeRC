package org.firstinspires.ftc.teamcode.testing.concepts;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.hardware.commands.CommandAutoStack;
import org.firstinspires.ftc.teamcode.hardware.commands.CommandGyroDrive;
import org.firstinspires.ftc.teamcode.lib.auto.DogeAutoOpMode;

/**
 * Created by Victo on 1/23/2018.
 */
@Autonomous(name="Conc Encoder", group="RAWRXD_CONC")
public class Concept_EncoderDistance extends DogeAutoOpMode {

    @Override
    public void runOpMode() throws InterruptedException {

        waitForStart();

        if (opModeIsActive()){
            new CommandGyroDrive(this,0.4,0,12).Run();
        }
    }
}
