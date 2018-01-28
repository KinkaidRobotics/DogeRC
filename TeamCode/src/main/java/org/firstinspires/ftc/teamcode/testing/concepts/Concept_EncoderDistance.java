package org.firstinspires.ftc.teamcode.testing.concepts;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.hardware.bots.RAWRXDBot;
import org.firstinspires.ftc.teamcode.hardware.commands.CommandAutoStack;
import org.firstinspires.ftc.teamcode.hardware.commands.CommandGyroDrive;
import org.firstinspires.ftc.teamcode.hardware.commands.CommandGyroTurn;
import org.firstinspires.ftc.teamcode.hardware.commands.CommandUltrasonicDrive;
import org.firstinspires.ftc.teamcode.lib.auto.DogeAutoOpMode;

/**
 * Created by Victo on 1/23/2018.
 */
@Autonomous(name="Conc Encoder", group="RAWRXD_CONC")
public class Concept_EncoderDistance extends DogeAutoOpMode {

    @Override
    public void runOpMode() throws InterruptedException {
        this.bot = new RAWRXDBot(hardwareMap);
        waitForStart();

        if (opModeIsActive()){
            //new CommandUltrasonicDrive(this, 0.5, 0, 50).Run();
            telemetry.addData("TURNING!", "TEAST");
            telemetry.update();
            new CommandGyroTurn(this,0.4,90).Run();
            new CommandGyroDrive(this,0.4,90,24).Run();
            new CommandGyroTurn(this,1.0,0).Run();
        }
    }
}
