package org.firstinspires.ftc.teamcode;

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

@Autonomous(name = "fancy pantaloons", group = "Autonomous")
public class FancyAuto extends LinearOpMode {

    private Follower follower;

    private final PoseFactory poseFactory = PoseFactory.degrees();

    private final Pose start = poseFactory.of(54.8344, 8.2331, 270);
    private final Pose path1 = poseFactory.of(56.4662, 15.9522, 270);
    private final Pose point2 = poseFactory.of(9.7894, 9.6047, 270);
    private final Pose point2Control1 = poseFactory.of(10.136, 46.9353, 0);
    private final Pose point3 = poseFactory.of(56.8054, 15.1503, 270);
    private final Pose point4Start = poseFactory.of(56.8054, 15.1503, 270);
    private final Pose point4 = poseFactory.of(12.4991, 96.6831, 270);
    private final Pose point4Control1 = poseFactory.of(10.5284, 37.5896, 0);

    private CRServo flapservo = null;
    private DcMotorEx flywheel = null;
    private DcMotor intake = null;

    // Fires: run the flap for 4 seconds, then stop it
    private Command shoot() {
        return sequential(
            instant(() -> flapservo.setPower(-1)),
            waitMs(6000),
            instant(() -> flapservo.setPower(0))
        );
    }

private Command slamWall() {
    return sequential(
        instant(() -> follower.manual(0.3, 0, 0)),   // forward, strafe, turn, robot-centric
        waitMs(2000),
        instant(() -> {
            follower.stop();
            Pose p = follower.pose();
            // The robot is against a known wall. Replace WALL_X and 270 with the real values.
            // follower.setPose(poseFactory.of(WALL_X, p.y(), 270));
        })
    );
}

    // Autonomous routine
    public Command autoRoutine() {
        return sequential(
            instant(() -> flywheel.setVelocity(1350)),   // spin up while driving
            follow(follower, path1()),
            shoot(),
            instant(() -> intake.setPower(1)), 
            follow(follower, path2()),
            slamWall(),
            instant(() -> intake.setPower(0)), 
            follow(follower, path3()),
            shoot(),
            follow(follower, path4())
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
        flywheel.setVelocityPIDFCoefficients(250, 3, 20, 0);

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
        return Paths.line(start, path1).constant(path1);
    }

    public Path path2() {
        return Paths.curve(path1, point2Control1, point2).constant(point2);
    }

    public Path path3() {
        return Paths.line(point2, point3).constant(point3);
    }

    public Path path4() {
        return Paths.curve(point4Start, point4Control1, point4).linear(point4Start, point4);
    }
}