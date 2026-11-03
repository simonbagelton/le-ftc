package org.firstinspires.ftc.teamcode;


import static com.pedropathing.api.Paths.*;
import com.pedropathing.api.Paths;
import org.firstinspires.ftc.teamcode.pedro.Constants;
import org.firstinspires.ftc.teamcode.commands.StartIntake;
import org.firstinspires.ftc.teamcode.commands.StopIntake;
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
import android.graphics.Color;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.hardware.rev.RevBlinkinLedDriver;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.NormalizedColorSensor;
import com.qualcomm.robotcore.hardware.NormalizedRGBA;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.NormalizedColorSensor;
import com.qualcomm.robotcore.hardware.NormalizedRGBA;
import com.qualcomm.robotcore.hardware.Gamepad;


@Autonomous(name = "red double shot !tip", group = "Autonomous")
public class reddoubleshotnotip extends LinearOpMode {

    private Follower follower;

    private final PoseFactory poseFactory = PoseFactory.degrees();
    private DcMotor intake = null;

    private final Pose start = poseFactory.of(57.1343, 9.1343, 90);
    private final Pose pathtoshoot = poseFactory.of(57.1459, 13.8919, 270);
    private final Pose tointakeStart = poseFactory.of(57.1459, 13.8919, 270);
    private final Pose tointake = poseFactory.of(8.6618, 16.0419, 270);
    private final Pose tointakeControl1 = poseFactory.of(58.4133, 35.7651, 0);
    private final Pose intaking = poseFactory.of(8.8021, 8.3558, 270);
    private final Pose tosecondshot = poseFactory.of(57.1092, 13.9688, 270);
    private final Pose tosecondshotControl1 = poseFactory.of(6.6979, 31.2485, 0);
    private final Pose tosecondshotControl2 = poseFactory.of(37.038, 19.4873, 0);
    private final Pose toparkStart = poseFactory.of(57.1092, 13.9688, 270);
    private final Pose topark = poseFactory.of(12.8684, 95.0302, 90);
    private final Pose toparkControl1 = poseFactory.of(13.6238, 48.9357, 0);

    // Autonomous routine
    public Command autoRoutine() {
        return sequential(
            follow(follower, pathtoshoot()),
            follow(follower, tointake()),
            new StartIntake(intake),
            follow(follower, intaking()),
            new StopIntake(intake),
            follow(follower, tosecondshot()),
            follow(follower, topark())
        );
    }

    @Override
    public void runOpMode() {
        Scheduler.reset();
        follower = Constants.create(hardwareMap);
        intake = hardwareMap.get(DcMotor.class, "intake");
        follower.setPose(start);
        follower.update();

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

    public Path pathtoshoot() {
        return Paths.line(start, pathtoshoot).constant(pathtoshoot);
    }

    public Path tointake() {
        return Paths.curve(tointakeStart, tointakeControl1, tointake).linear(tointakeStart, tointake);
    }

    public Path intaking() {
        return Paths.line(tointake, intaking).constant(intaking);
    }

    public Path tosecondshot() {
        return Paths.curve(intaking, tosecondshotControl1, tosecondshotControl2, tosecondshot).constant(tosecondshot);
    }

    public Path topark() {
        return Paths.curve(toparkStart, toparkControl1, topark).linear(toparkStart, topark);
    }
}
