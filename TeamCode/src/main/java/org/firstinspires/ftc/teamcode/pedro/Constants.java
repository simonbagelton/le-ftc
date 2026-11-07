package org.firstinspires.ftc.teamcode.pedro;

import com.pedropathing.follower.Follower;
import com.pedropathing.revhub.drivetrains.MecanumConfig;
import com.pedropathing.revhub.localizers.TwoWheelConfig;
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.pedropathing.revhub.localizers.Encoder;
import com.pedropathing.revhub.localizers.RevHubIMU;

import com.pedropathing.algorithm.Foresight;
import com.pedropathing.algorithm.ForesightConfig;
import com.pedropathing.controllers.Controller;
import com.pedropathing.follower.Follower;
import com.pedropathing.math.Matrix;
import com.pedropathing.math.Vector2D;
import com.pedropathing.revhub.drivetrains.Mecanum;
import com.pedropathing.revhub.drivetrains.MecanumConfig;
import com.pedropathing.revhub.localizers.PinpointConfig;
import com.pedropathing.revhub.localizers.PinpointLocalizer;
import com.qualcomm.hardware.gobilda.GoBildaPinpointDriver;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.pedropathing.algorithm.Foresight;
import com.pedropathing.algorithm.ForesightConfig;
import com.pedropathing.controllers.Controller;
import com.pedropathing.follower.Follower;
import com.pedropathing.math.Matrix;
import com.pedropathing.math.Vector2D;
import com.pedropathing.revhub.drivetrains.Mecanum;
import com.pedropathing.revhub.drivetrains.MecanumConfig;
import com.pedropathing.revhub.localizers.Encoder;
import com.pedropathing.revhub.localizers.RevHubIMU;
import com.pedropathing.revhub.localizers.TwoWheelConfig;
import com.pedropathing.revhub.localizers.TwoWheelLocalizer;
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

// Encoder and RevHubIMU: use Alt+Enter in Android Studio to add these (see below)

public class Constants {

public static Follower create(HardwareMap h) {
    return new Follower(
           new TwoWheelLocalizer(h, localizerConfig),
          new Mecanum(h, drivetrainConfig),
            new Foresight(foresightConfig)
    );

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
    c.xPodOffset.set(4.281680841386278);
    c.yPodOffset.set(-1.7513239808188638);
    c.forwardTicksToInches.set(0.00155);
    c.strafeTicksToInches.set(0.00155);
    c.xPodDirection.set(Encoder.FORWARD);
    c.yPodDirection.set(Encoder.REVERSE);
    c.imu.set(new RevHubIMU(new RevHubOrientationOnRobot(
            RevHubOrientationOnRobot.LogoFacingDirection.FORWARD,
            RevHubOrientationOnRobot.UsbFacingDirection.RIGHT
    )));

});

// STARTING POINT ONLY: precision-biased, 312 rpm goBILDA / 104 mm mecanum.
// Lines marked [AUTOTUNER] are guesses; overwrite them with the autotuner's output.
// Lines marked [HAND] are the ones to tweak by hand after the autotuner runs.

// TRY #1: estimated from floor tests (312 rpm goBILDA, 104 mm mecanum, ~10 lb), precision-leaning.
// Every value is an estimate good to roughly +/-40%. Report back what the robot does at the end of paths.
// Old values are kept in comments so you can revert any single line.
public static ForesightConfig foresightConfig = new ForesightConfig(c -> {
    Controller primaryTranslationalForward = Controller.proportional(0.03);
    Controller secondaryTranslationalForward = Controller.proportional(0.015);
    Controller primaryTranslationalLateral = Controller.proportional(0.03);
    Controller secondaryTranslationalLateral = Controller.proportional(0.015);

    c.forwardTranslational.set(Controller.piecewise(secondaryTranslationalForward).put(2.0, primaryTranslationalForward));
    c.strafeTranslational.set(Controller.piecewise(secondaryTranslationalLateral).put(2.0, primaryTranslationalLateral));

    c.coast.set(Controller.proportionalFeedforward(0.0145));
    c.brake.set(Controller.proportionalFeedforward(0.0115));

    c.headingFeedback.set(Controller.proportional(1.5));
    c.headingBrakeCoefficients.set(Vector2D.cartesian(0.155, 0.0175));

    c.linearBrakeCoefficients.set(Matrix.diag(0.0165, 0.039));
    c.quadraticBrakeCoefficients.set(Matrix.diag(0.0012, 0.0034));

    c.maxAchievableForwardVelocity.set(55.0);
    c.maxAchievableStrafeVelocity.set(46.0);

    c.naturalForwardDeceleration.set(75.0);
    c.naturalStrafeDeceleration.set(175.0);
});

}