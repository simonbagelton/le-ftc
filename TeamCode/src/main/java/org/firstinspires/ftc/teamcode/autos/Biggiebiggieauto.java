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
@Autonomous(name = "Biggie biggie auto", group = "Autonomous")
public class Biggiebiggieauto extends LinearOpMode {

    private Follower follower;

    private final PoseFactory poseFactory = PoseFactory.degrees();


    private final Pose start = poseFactory.of(55.1207, 8.8744, 270);
    private final Pose point11 = poseFactory.of(11.0836, 12.3711, 260);
    private final Pose point11Control1 = poseFactory.of(12.9528, 36.6538, 0);
    private final Pose point2Start = poseFactory.of(11.0836, 12.3711, 270);
    private final Pose point2 = poseFactory.of(60.1188, 132.6945, 90);
    private final Pose point2Control1 = poseFactory.of(34.2677, 59.8877, 0);
    private final Pose point2Control2 = poseFactory.of(13.8701, 134.64, 0);
    private final Pose point2Control3 = poseFactory.of(66.5675, 106.839, 0);
    private final Pose point3 = poseFactory.of(11.8051, 117.9161, 90);
    private final Pose point3Control1 = poseFactory.of(65.8544, 101.8274, 0);



        private CRServo flapservo = null;
    private DcMotorEx flywheel = null;
    private DcMotor intake = null;

    // Fires: run the flap for 4 seconds, then stop it
    private Command shoot() {
        return sequential(
            instant(() -> flapservo.setPower(-1)),
            waitMs(300),
            instant(() -> flapservo.setPower(0)),
            waitMs(300),
            instant(() -> flapservo.setPower(-1)),
            waitMs(300),
            instant(() -> flapservo.setPower(0)),
            waitMs(300),
            instant(() -> flapservo.setPower(-1)),
            waitMs(300),
            instant(() -> flapservo.setPower(0)),
            waitMs(300),
            instant(() -> flapservo.setPower(-1)),
            waitMs(300),
            instant(() -> flapservo.setPower(0)),
            waitMs(300),
            instant(() -> flapservo.setPower(-1)),
            waitMs(300),
            instant(() -> flapservo.setPower(0)),
            waitMs(300),
            instant(() -> flapservo.setPower(-1)),
            waitMs(300),
            instant(() -> flapservo.setPower(0)),
            waitMs(300),
            instant(() -> flapservo.setPower(-1)),
            waitMs(300),
            instant(() -> flapservo.setPower(0)),
            waitMs(300),
            instant(() -> flapservo.setPower(-1)),
            waitMs(300),
            instant(() -> flapservo.setPower(0)),
            waitMs(300),
            instant(() -> flapservo.setPower(-1)),
            waitMs(300),
            instant(() -> flapservo.setPower(0)),
            waitMs(300),
            instant(() -> flapservo.setPower(-1)),
            waitMs(300),
            instant(() -> flapservo.setPower(0)),
            waitMs(300),
            instant(() -> flapservo.setPower(-1)),
            waitMs(300),
            instant(() -> flapservo.setPower(0)),
            waitMs(300),
            instant(() -> flapservo.setPower(-1)),
            waitMs(300),
            instant(() -> flapservo.setPower(0)),
            waitMs(300),            
            instant(() -> flapservo.setPower(-1)),
            waitMs(300),
            instant(() -> flapservo.setPower(0)),
            waitMs(300),
            instant(() -> flapservo.setPower(-1)),
            waitMs(300),
            instant(() -> flapservo.setPower(0)),
            waitMs(300)

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
            instant(() -> flywheel.setVelocity(Constants.target_flywheel_speed)),
            shoot(),
            follow(follower, path11()),
            instant(() -> intake.setPower(1)),
            slamWall(),
            follow(follower, path2()),
            shoot(),
            follow(follower, path3())

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

    public Path path11() {
        return Paths.curve(start, point11Control1, point11).linear(start, point11);
    }

    public Path path2() {
        return Paths.curve(point2Start, point2Control1, point2Control2, point2Control3, point2).linear(point2Start, point2);
    }

    public Path path3() {
        return Paths.curve(point2, point3Control1, point3).constant(point3);
    }
}

