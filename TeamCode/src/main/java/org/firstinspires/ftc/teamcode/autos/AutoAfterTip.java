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
@Autonomous(name = "AutoAfterTip", group = "Autonomous")
public class AutoAfterTip extends LinearOpMode {

    private Follower follower;

    private final PoseFactory poseFactory = PoseFactory.degrees();

    private final Pose start = poseFactory.of(59.2162, 132.4246, 90);
    private final Pose point11 = poseFactory.of(10.4901, 116.1067, 90);
    private final Pose point11Control1 = poseFactory.of(79.8497, 107.9496, 0);



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
            follow(follower, path11())
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
        return Paths.curve(start, point11Control1, point11).constant(point11);
    }
}

