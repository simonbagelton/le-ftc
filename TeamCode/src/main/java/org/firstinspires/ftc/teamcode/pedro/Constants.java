package org.firstinspires.ftc.teamcode.pedro;

import com.pedropathing.follower.Follower;
import com.pedropathing.revhub.drivetrains.MecanumConfig;
import com.pedropathing.revhub.localizers.TwoWheelConfig;
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.pedropathing.revhub.localizers.Encoder;
import com.pedropathing.revhub.localizers.RevHubIMU;
// Encoder and RevHubIMU: use Alt+Enter in Android Studio to add these (see below)

public class Constants {
    public static Follower create(HardwareMap h) {
        // TODO: return new Follower(new Mecanum(h, drivetrainConfig),
        //     new TwoWheelLocalizer(h, localizerConfig), new Foresight(foresightConfig));
        return null;
    }

    public static MecanumConfig drivetrainConfig = new MecanumConfig(c -> {
        c.frontLeftName.set("fl");
        c.frontRightName.set("fr");
        c.backLeftName.set("bl");
        c.backRightName.set("br");
        c.frontLeftDirection.set(DcMotorSimple.Direction.REVERSE);
        c.frontRightDirection.set(DcMotorSimple.Direction.FORWARD);
        c.backLeftDirection.set(DcMotorSimple.Direction.REVERSE);
        c.backRightDirection.set(DcMotorSimple.Direction.FORWARD);
    });

    public static TwoWheelConfig localizerConfig = new TwoWheelConfig(c -> {
        c.xPodName.set("ef");
        c.yPodName.set("es");
        c.imuName.set("imu");
        c.xPodOffset.set(-6.5592797511021175);
        c.yPodOffset.set(-3.806697482621718);
        c.forwardTicksToInches.set(0.05120411331209695);
        c.strafeTicksToInches.set(0.004793946933743194);
        c.xPodDirection.set(Encoder.FORWARD);
        c.yPodDirection.set(Encoder.REVERSE);
        c.imu.set(new RevHubIMU(new RevHubOrientationOnRobot(
                RevHubOrientationOnRobot.LogoFacingDirection.UP,
                RevHubOrientationOnRobot.UsbFacingDirection.BACKWARD)));
    });
}