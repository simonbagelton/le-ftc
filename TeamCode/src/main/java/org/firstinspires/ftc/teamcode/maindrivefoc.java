/* Copyright (c) 2017 FIRST. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted (subject to the limitations in the disclaimer below) provided that
 * the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice, this list
 * of conditions and the following disclaimer.
 *
 * Redistributions in binary form must reproduce the above copyright notice, this
 * list of conditions and the following disclaimer in the documentation and/or
 * other materials provided with the distribution.
 *
 * Neither the name of FIRST nor the names of its contributors may be used to endorse or
 * promote products derived from this software without specific prior written permission.
 *
 * NO EXPRESS OR IMPLIED LICENSES TO ANY PARTY'S PATENT RIGHTS ARE GRANTED BY THIS
 * LICENSE. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package org.firstinspires.ftc.teamcode;
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
import com.qualcomm.robotcore.hardware.IMU;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.YawPitchRollAngles;
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;

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
import com.pedropathing.drivetrain.DrivePowers;
import com.pedropathing.follower.ManualDrive;
/*
 * This file contains an example of an iterative (Non-Linear) "OpMode".
 * An OpMode is a 'program' that runs in either the autonomous or the teleop period of an FTC match.
 * The names of OpModes appear on the menu of the FTC Driver Station.
 * When a selection is made from the menu, the corresponding OpMode
 * class is instantiated on the Robot Controller and executed.
 *
 * This particular OpMode just executes a basic Tank Drive Teleop for a two wheeled robot
 * It includes all the skeletal structure that all iterative OpModes contain.
 *
 * Use Android Studio to Copy this Class, and Paste it into your team's code folder with a new name.
 * Remove or comment out the @Disabled line to add this OpMode to the Driver Station OpMode list
 */

@TeleOp(name="main drive foc", group="Iterative OpMode")

public class maindrivefoc extends OpMode {
    // Declare OpMode members.
    private ElapsedTime runtime = new ElapsedTime();

    private DcMotor intake = null;
    private CRServo intakeservo = null;
    private CRServo intakeservo2 = null;
    private RevBlinkinLedDriver leds = null;

    private CRServo flapservo = null;
    private DcMotorEx flywheel = null;
    private DcMotorEx encoder = null;
    private DcMotorEx encoderf = null;
    private NormalizedColorSensor colorSensor;
    public double flywheelspeed = 1350;
    private Gamepad previousGamepad1 = new Gamepad();
    private Gamepad currentGamepad1 = new Gamepad();
    private boolean shootertoggle = true;
    private boolean slowmodetoggle = false;
    private IMU imu = null;
    private double rotationoffset = 0;
    private Follower follower;
    
    /*
     * Code to run ONCE when the driver hits INIT
     */
    @Override
    public void init() {
        telemetry.addData("Status", "Initialized");
        colorSensor = hardwareMap.get(NormalizedColorSensor.class, "color");
        follower = Constants.create(hardwareMap);




        imu = hardwareMap.get(IMU.class, "imu");

    // Adjust these directions based on how your Control Hub is mounted on the robot
    RevHubOrientationOnRobot orientationSettings = new RevHubOrientationOnRobot(
        RevHubOrientationOnRobot.LogoFacingDirection.FORWARD,
        RevHubOrientationOnRobot.UsbFacingDirection.RIGHT
    );

    imu.initialize(new IMU.Parameters(orientationSettings));


        leds = hardwareMap.get(RevBlinkinLedDriver.class, "led");

        // Initialize the hardware variables. Note that the strings used here as parameters
        // to 'get' must correspond to the names assigned during the robot configuration

        encoder = hardwareMap.get(DcMotorEx.class, "es");     
        encoderf = hardwareMap.get(DcMotorEx.class, "ef");  
        
        

        encoder.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        encoder.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        encoderf.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        encoderf.setMode(DcMotor.RunMode.RUN_USING_ENCODER);


        flywheel = hardwareMap.get(DcMotorEx.class, "flywheel");
        flywheel.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        flywheel.setVelocityPIDFCoefficients(40, 0, 0, 12.5);
       
        flapservo = hardwareMap.get(CRServo.class, "flap");
       
        intakeservo = hardwareMap.get(CRServo.class, "i1");    
       
        intake = hardwareMap.get(DcMotor.class, "intake");
       
        intakeservo2 = hardwareMap.get(CRServo.class, "i2");
        // To drive forward, most robots need the motor on one side to be reversed, because the axles point in opposite directions.
        // Pushing the left stick forward MUST make robot go forward. So adjust these two lines based on your first test drive.
        // Note: The settings here assume direct drive on left and right wheels.  Gear Reduction or 90 Deg drives may require direction flips

        // Tell the driver that initialization is complete.
        telemetry.addData("Status", "Initialized");
    }

    /*
     * Code to run REPEATEDLY after the driver hits INIT, but before they hit START
     */
    @Override
    public void init_loop() {
    }

    /*
     * Code to run ONCE when the driver hits START
     */
    @Override
    public void start() {
        runtime.reset();
    }

    /*
     * Code to run REPEATEDLY after the driver hits START but before they hit STOP
     */
@Override
public void loop() {
    DrivePowers powers = ManualDrive.fieldCentric(
            -gamepad1.left_stick_y,
            -gamepad1.left_stick_x,
            -gamepad1.right_stick_x,
            follower.pose().heading()
    );
    follower.manual(powers);

    // relocalise button
    if (gamepad1.y) {
        Pose cornerPose = new Pose(10.5, 10.5, Math.toRadians(90));
        // On the fly Pose creation, we dont recommend this for Autonomous. Only accepts radians for heading
        follower.setPose(cornerPose); // overrides our pose  
    }


        if (gamepad1.left_trigger > 0.5){
            intake.setPower(1);
            intakeservo.setPower(-1.0);
            intakeservo2.setPower(-1);
           
           
           
        } else {
            intake.setPower(0);
            intakeservo.setPower(0);
            intakeservo2.setPower(0);
           
        }
       
       
        if (gamepad1.right_trigger > 0.5){
            if (shootertoggle == true){
            flapservo.setPower(-0.3);
           } else {
            flapservo.setPower(0);
           }
           
        } else {

            flapservo.setPower(0);
           
        }
       
       
       
       
       if (shootertoggle == true){
        flywheel.setVelocity(flywheelspeed);
       }
       
        if (currentGamepad1.a && !previousGamepad1.a){
            if (shootertoggle == false){
                shootertoggle = true;
                flywheel.setVelocity(flywheelspeed);

            } else {
                shootertoggle = false;
                flywheel.setVelocity(0);

            }
            
           
           
        } 


        if (currentGamepad1.x && !previousGamepad1.x){
            if (slowmodetoggle == false){
                slowmodetoggle = true;
                

            } else {
                slowmodetoggle = false;
                

            }
            
           
           
        } 




        if (Math.abs(flywheel.getVelocity() - flywheelspeed) > 40){

            if (shootertoggle == true){
               leds.setPattern(RevBlinkinLedDriver.BlinkinPattern.RED); // check the exact name via autocomplete
            } else {
                leds.setPattern(RevBlinkinLedDriver.BlinkinPattern.RED); // check the exact name via autocomplete
            }

        } else {
            leds.setPattern(RevBlinkinLedDriver.BlinkinPattern.GREEN);

        }

        if (gamepad1.b){
            flywheel.setVelocity(-flywheelspeed);

            intake.setPower(-1);
            intakeservo.setPower(1.0);
            intakeservo2.setPower(1);

        } else if (shootertoggle == false){
            flywheel.setVelocity(0);
        }
            
        



        if (currentGamepad1.dpad_up && !previousGamepad1.dpad_up){
            flywheelspeed += 5.0;
        }
        if (currentGamepad1.dpad_down && !previousGamepad1.dpad_down){
            flywheelspeed -= 5.0;
        }






    follower.update();
    Pose robotPose = follower.pose(); // returns a Pose object
    telemetry.addData("Robot X", robotPose.x());
    telemetry.addData("Robot Y", robotPose.y());
    telemetry.addData("Robot Heading", Math.toDegrees(robotPose.heading())); 
    // Math.toDegrees() is a built-in java method
}
    /*
     * Code to run ONCE after the driver hits STOP
     */
    @Override
    public void stop() {
    }

}