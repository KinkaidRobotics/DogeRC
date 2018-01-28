package org.firstinspires.ftc.teamcode.hardware.sensors;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.hardware.bosch.JustLoggingAccelerationIntegrator;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.teamcode.hardware.subsystems.NavigationHardware;

/**
 * Created by jcm12 on 1/27/2018.
 */

public class MultiIMU extends NavigationHardware {
    public BNO055IMU imu1;
    public BNO055IMU imu2;
    public MultiIMU(HardwareMap map, String name1,  String name2) {
        super(map, name1);

        BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();
        parameters.angleUnit = BNO055IMU.AngleUnit.DEGREES;
        parameters.accelUnit = BNO055IMU.AccelUnit.METERS_PERSEC_PERSEC;
        parameters.loggingEnabled = true;
        parameters.loggingTag = "IMU";
        parameters.accelerationIntegrationAlgorithm = new JustLoggingAccelerationIntegrator();

        imu1 = map.get(BNO055IMU.class, name1);
        imu1.initialize(parameters);

        imu2 = map.get(BNO055IMU.class, name2);
        imu2.initialize(parameters);
    }

    @Override
    public double getHeading() {
        Orientation angles1 = imu1.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);
        Orientation angles2 = imu1.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);
        double angle = (angles1.firstAngle + angles2.firstAngle) / 2;
        return (angle+360)%360;
    }

    @Override
    public double getError(double targetAngle){
        double angleError = 0;

        angleError = (targetAngle - getHeading());
        angleError -= (360*Math.floor(0.5+((angleError)/360.0)));

        return angleError;

    }
}
