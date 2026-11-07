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
import com.pedropathing.paths.interpolator.Interpolator;
import org.firstinspires.ftc.teamcode.pedro.Constants;

@Autonomous(name = "rotcalib", group = "Autonomous")
public class rotcalib extends LinearOpMode {

    private Follower follower;

    private final PoseFactory poseFactory = PoseFactory.degrees();

    private final Pose start = poseFactory.of(22.7368, 22.6767, 90);
    private final Pose calibration = poseFactory.of(119.2294, 22.376, 90);
    private final Pose calibrationSegment1Start = poseFactory.of(119.2294, 22.376, 90);
    private final Pose calibrationSegment1End = poseFactory.of(119.2294, 22.376, 270);
    private final Pose calibrationSegment2Start = poseFactory.of(119.2294, 22.376, 270);
    private final Pose calibrationSegment2End = poseFactory.of(119.2294, 22.376, 450);

    // Autonomous routine
    public Command autoRoutine() {
        return sequential(
            follow(follower, calibration())
        );
    }

    @Override
    public void runOpMode() {
        Scheduler.reset();
        follower = Constants.create(hardwareMap);
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

    public Path calibration() {
        return Paths.line(start, calibration).heading(Interpolator.piecewise().until(0.4986, Interpolator.linear(calibrationSegment1Start, calibrationSegment1End)).until(1, Interpolator.linear(calibrationSegment2Start, calibrationSegment2End).reverse()));
    }
}
