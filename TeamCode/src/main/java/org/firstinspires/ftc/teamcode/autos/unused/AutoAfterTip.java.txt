package org.firstinspires.ftc.teamcode;

import static com.pedropathing.api.Paths.*;
import com.pedropathing.api.Paths;

import com.pedropathing.api.PoseFactory;
import com.pedropathing.follower.Follower;
import com.pedropathing.math.Pose;
import com.pedropathing.paths.Path;
import com.pedropathing.ivy.Command;
import com.pedropathing.ivy.Scheduler;
import static com.pedropathing.ivy.Scheduler.schedule;
import static com.pedropathing.ivy.commands.Commands.*;
import static com.pedropathing.ivy.groups.Groups.sequential;
import static com.pedropathing.ivy.pedro.PedroCommands.follow;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import static com.pedropathing.api.Paths.*;
import static com.pedropathing.ivy.Scheduler.schedule;
import static com.pedropathing.ivy.commands.Commands.*;
import static com.pedropathing.ivy.groups.Groups.sequential;
import static com.pedropathing.ivy.pedro.PedroCommands.follow;

import com.pedropathing.api.Paths;
import com.pedropathing.api.PoseFactory;
import com.pedropathing.follower.Follower;
import com.pedropathing.ivy.Command;
import com.pedropathing.ivy.Scheduler;
import com.pedropathing.math.Pose;
import com.pedropathing.paths.Path;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;

import org.firstinspires.ftc.teamcode.pedro.Constants;

@Autonomous(name = "Auto after tip", group = "Autonomous")
public class AutoAfterTip extends LinearOpMode {

    private Follower follower;

    private final PoseFactory poseFactory = PoseFactory.degrees();

    private final Pose start = poseFactory.of(8.9755, 132.9329, 90);
    private final Pose point1 = poseFactory.of(56.707, 126.8287, 90);
    private final Pose point1Control1 = poseFactory.of(26.5902, 121.5504, 0);
    private final Pose point2 = poseFactory.of(10.0329, 118.1223, 90);
    private final Pose point2Control1 = poseFactory.of(58.0341, 115.4398, 0);
    private CRServo flapservo = null;
    private DcMotorEx flywheel = null;
    private DcMotor intake = null;

    // Autonomous routine
    public Command autoRoutine() {
        return sequential(
            waitMs(15000),
            instant(() -> flywheel.setVelocity(1350)),
            follow(follower, path1()),
            shoot(),
            follow(follower, path2())
        );
    }
        private Command shoot() {
        return sequential(
            instant(() -> flapservo.setPower(-1)),
            waitMs(6000),
            instant(() -> flapservo.setPower(0))
        );
    }

    @Override
    public void runOpMode() {
        Scheduler.reset();
        follower = Constants.create(hardwareMap);
        follower.setPose(start);
        follower.update();
        flywheel = hardwareMap.get(DcMotorEx.class, "flywheel");
        flywheel.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        flywheel.setVelocityPIDFCoefficients(40, 0, 0, 12.5);

        flapservo = hardwareMap.get(CRServo.class, "flap");

        intake = hardwareMap.get(DcMotor.class, "intake");



        waitForStart();
        schedule(autoRoutine());

        while (opModeIsActive()) {
            follower.update();
            Scheduler.execute();

            telemetry.addData("x", follower.pose().x());
            telemetry.addData("y", follower.pose().y());
            telemetry.addData("heading", follower.pose().heading());

            if (follower.currentPath() != null) {
                telemetry.addData("Current path distance remaining", follower.distanceToEndpoint());
                telemetry.addData("Path number", follower.pathIndex());
            }

            telemetry.update();
        }
    }

    public Path path1() {
        return Paths.curve(start, point1Control1, point1).constant(point1);
    }

    public Path path2() {
        return Paths.curve(point1, point2Control1, point2).constant(point2);
    }
}