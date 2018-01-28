package org.firstinspires.ftc.teamcode.hardware.bots;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.Hardware;

import org.firstinspires.ftc.teamcode.hardware.sensors.IMU;
import org.firstinspires.ftc.teamcode.hardware.sensors.MultiIMU;
import org.firstinspires.ftc.teamcode.hardware.subsystems.DistanceDetection;
import org.firstinspires.ftc.teamcode.hardware.subsystems.DriveFrame;
import org.firstinspires.ftc.teamcode.hardware.subsystems.Grabbers;
import org.firstinspires.ftc.teamcode.hardware.subsystems.JewelArm;
import org.firstinspires.ftc.teamcode.hardware.subsystems.Lift;
import org.firstinspires.ftc.teamcode.hardware.subsystems.PhoneServo;

/**
 * Created by Victo on 1/17/2018.
 */

public class RAWRXDBot extends DogeBot {

    private String leftDriveFront  = "dl1";
    private String leftDriveRear   = "dl2";
    private String rightDriveFront = "dr1";
    private String rightDriveRear  = "dr2";
    private String driveMotorNames[] = new String[]{leftDriveFront, leftDriveRear, rightDriveFront, rightDriveRear};



    private String grabberTopLeft    = "gl1";
    private String grabberBottomLeft = "gl2";
    private String grabberTopRight   = "gr1";
    private String grabberBottomRight= "gr2";
    private String grabberNames[] = new String[]{grabberTopLeft, grabberBottomLeft, grabberTopRight, grabberBottomRight};

    private double GRAB_TOP_LEFT_OPEN = 0.7;
    private double GRAB_BOTTOM_LEFT_OPEN = 0.3;
    private double GRAB_TOP_RIGHT_OPEN = 0.7;
    private double GRAB_BOTTOM_RIGHT_OPEN = 0.3;
    private double grabberOpenPos[] = new double[]{GRAB_TOP_LEFT_OPEN, GRAB_BOTTOM_LEFT_OPEN, GRAB_TOP_RIGHT_OPEN, GRAB_BOTTOM_RIGHT_OPEN};

    private double GRAB_TOP_LEFT_CLOSE = 1;
    private double GRAB_BOTTOM_LEFT_CLOSE = 0;
    private double GRAB_TOP_RIGHT_CLOSE = 1;
    private double GRAB_BOTTOM_RIGHT_CLOSE = 0;
    private double grabberClosePos[] = new double[]{GRAB_TOP_LEFT_CLOSE, GRAB_BOTTOM_LEFT_CLOSE, GRAB_TOP_RIGHT_CLOSE, GRAB_BOTTOM_RIGHT_CLOSE};

    private double GRAB_TOP_LEFT_SOFTOPEN = 0.85;
    private double GRAB_BOTTOM_LEFT_SOFTOPEN = 0.15;
    private double GRAB_TOP_RIGHT_SOFTOPEN = 0.85;
    private double GRAB_BOTTOM_RIGHT_SOFTOPEN = 0.15;
    private double grabberSoftPos[] = new double[]{GRAB_TOP_LEFT_SOFTOPEN, GRAB_BOTTOM_LEFT_SOFTOPEN, GRAB_TOP_RIGHT_SOFTOPEN, GRAB_BOTTOM_RIGHT_SOFTOPEN};


    private String jewelArmName    = "jewel";
    private double JEWEL_UP = 1.0;
    private double JEWEL_DOWN = 0;


    public RAWRXDBot(HardwareMap hwd) {
        super(hwd);
        P = 0.03;
        I = 0;
        D = 0;
        PID_THRESH =1 ;
       // DRIVE_GEAR_REDUCTION = 0.5;
        COUNTS_PER_MOTOR_REV =  560;

        driveFrame         = new DriveFrame(hardwareMap, null, driveMotorNames,true);
        grabbers           = new Grabbers(hardwareMap, grabberNames,grabberOpenPos, grabberSoftPos, grabberClosePos);
        navigationHardware = new MultiIMU(hardwareMap, "imu1", "imu2");
        jewelArm           = new JewelArm(hardwareMap, jewelArmName, JEWEL_UP,JEWEL_DOWN);
        distanceDetection  = new DistanceDetection(hardwareMap, "ultra");
        lift               = new Lift(hardwareMap, new String[]{"lift1","lift2"}, 0,2500);
        lift.setReverseMotor(1);
        phoneServo         = new PhoneServo(hardwareMap, "phone", new double[]{0.4, 0.9, 1.0});
    }

    @Override
    public void Init() {

    }


}
