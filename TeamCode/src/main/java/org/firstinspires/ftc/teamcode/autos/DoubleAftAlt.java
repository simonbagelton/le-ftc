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
@Autonomous(name = "DoubleAftAlt", group = "Autonomous")
public class DoubleAftAlt extends LinearOpMode {

    private Follower follower;

    private final PoseFactory poseFactory = PoseFactory.degrees();

    private final Pose start = poseFactory.of(59.2162, 132.4246, 90);
    private final Pose point11 = poseFactory.of(20.7471, 71.582, 90);
    private final Pose point11Control1 = poseFactory.of(73.7887, 107.7165, 0);
    private final Pose point11Control2 = poseFactory.of(6.0678, 120.3944, 0);
    private final Pose point2Start = poseFactory.of(20.7471, 71.582, 90);
    private final Pose point2 = poseFactory.of(11.1175, 12.806, 260);
    private final Pose point2Control1 = poseFactory.of(32.3713, 34.0343, 0);
    private final Pose point3 = poseFactory.of(55.7473, 8.9458, 270);
    private final Pose point4 = poseFactory.of(9.8684, 98.4377, 270);
    private final Pose point4Control1 = poseFactory.of(18.1841, 40.9489, 0);





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
        instant(() -> follower.manual(0.5, 0, 0)),   // forward, strafe, turn, robot-centric
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
            waitMs(2000),
            shoot(),
            follow(follower, path11()),
            instant(() -> intake.setPower(1)),
            follow(follower, path2()),
            slamWall(),
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
        return Paths.curve(start, point11Control1, point11Control2, point11).constant(point11);
    }

    public Path path2() {
        return Paths.curve(point2Start, point2Control1, point2).linear(point2Start, point2);
    }

    public Path path3() {
        return Paths.line(point2, point3).constant(point3);
    }

    public Path path4() {
        return Paths.curve(point3, point4Control1, point4).constant(point4);
    }
}

