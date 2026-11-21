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
@Autonomous(name = "Double  Other Side", group = "Autonomous")
public class DoubleOtherSide extends LinearOpMode {

    private Follower follower;

    private final PoseFactory poseFactory = PoseFactory.degrees();

    private final Pose start = poseFactory.of(59.2162, 132.6577, 270);
    private final Pose point11 = poseFactory.of(12.1219, 14.7022, 260);
    private final Pose point11Control1 = poseFactory.of(71.799, 101.2123, 0);
    private final Pose point11Control2 = poseFactory.of(0, 141.5, 0);
    private final Pose point11Control3 = poseFactory.of(47.4469, 68.3203, 0);
    private final Pose point2Start = poseFactory.of(12.1219, 14.7022, 260);
    private final Pose point2 = poseFactory.of(59.3172, 132.8224, 90);
    private final Pose point2Control1 = poseFactory.of(46.6659, 67.8219, 0);
    private final Pose point2Control2 = poseFactory.of(0, 141.5, 0);
    private final Pose point2Control3 = poseFactory.of(72.6794, 100.3517, 0);
    private final Pose point3 = poseFactory.of(11.3721, 119.7719, 90);
    private final Pose point3Control1 = poseFactory.of(72.703, 104.2286, 0);

        private CRServo flapservo = null;
    private DcMotorEx flywheel = null;
    private DcMotor intake = null;
    // Autonomous routine
    public Command autoRoutine() {
        return sequential(
            waitMs(10000),
            instant(() -> flywheel.setVelocity(Constants.target_flywheel_speed)),
            waitMs(2000),
            shoot(),
            instant(() -> intake.setPower(1)),
            follow(follower, path11()),
            slamWall(),
            instant(() -> intake.setPower(0)),
            follow(follower, path2()),
            shoot(),
            follow(follower, path3())
        );
    }


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
        return Paths.curve(start, point11Control1, point11Control2, point11Control3, point11).linear(start, point11);
    }

    public Path path2() {
        return Paths.curve(point2Start, point2Control1, point2Control2, point2Control3, point2).linear(point2Start, point2);
    }

    public Path path3() {
        return Paths.curve(point2, point3Control1, point3).constant(point3);
    }
}