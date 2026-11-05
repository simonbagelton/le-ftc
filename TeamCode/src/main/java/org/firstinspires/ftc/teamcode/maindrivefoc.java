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
    private DcMotor fl = null;
    private DcMotor fr = null;
    private DcMotor bl = null;
    private DcMotor br = null;
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
    
    
    /*
     * Code to run ONCE when the driver hits INIT
     */
    @Override
    public void init() {
        telemetry.addData("Status", "Initialized");
        colorSensor = hardwareMap.get(NormalizedColorSensor.class, "color");




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
        // step (using the FTC Robot Controller app on the phone).
        fl  = hardwareMap.get(DcMotor.class, "fl");
        fr = hardwareMap.get(DcMotor.class, "fr");
        bl  = hardwareMap.get(DcMotor.class, "bl");
        br = hardwareMap.get(DcMotor.class, "br");




        fl.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        fr.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        bl.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        br.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        encoder = hardwareMap.get(DcMotorEx.class, "es");     
        encoderf = hardwareMap.get(DcMotorEx.class, "ef");  
        
        

        encoder.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        encoder.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        encoderf.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        encoderf.setMode(DcMotor.RunMode.RUN_USING_ENCODER);


        flywheel = hardwareMap.get(DcMotorEx.class, "flywheel");
        flywheel.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        flywheel.setVelocityPIDFCoefficients(250, 3, 20, 0);
       
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
        YawPitchRollAngles robotAngles = imu.getRobotYawPitchRollAngles();
        double zRotation = robotAngles.getYaw(AngleUnit.DEGREES); // Returns -180 to 180 degrees

        
        previousGamepad1.copy(currentGamepad1);
        currentGamepad1.copy(gamepad1);
        
        // Setup a variable for each drive wheel to save power level for telemetry



        // Choose to drive using either Tank Mode, or POV Mode
        // Comment out the method that's not used.  The default below is POV.

        // POV Mode uses left stick to go forward, and right stick to turn.
        // - This uses basic math to combine motions and is easier to drive straight.
        double drive = 0.0;
        double turn  =  0.0;
        double sideways = 0.0;
        if (!slowmodetoggle) {
        drive = gamepad1.left_stick_y;
        turn  =  gamepad1.right_stick_x;
        sideways = gamepad1.left_stick_x;
        } else {
        drive = gamepad1.left_stick_y * 0.2;
        turn  =  gamepad1.right_stick_x * 0.2;
        sideways = gamepad1.left_stick_x * 0.2;

        }
        drive = drive * Math.cos(Math.toRadians(zRotation));
        sideways = sideways * Math.sin(Math.toRadians(zRotation));


        double devisor = Math.max(Math.abs(drive) + Math.abs(turn) + Math.abs(sideways), 1);
        fl.setPower((drive + -turn + -sideways) / devisor);
        fr.setPower((-drive + -turn + -sideways) / devisor);
        bl.setPower((drive + -turn + sideways) / devisor);
        br.setPower((-drive + -turn + sideways) / devisor);
       
       
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
            flapservo.setPower(-1);
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

        // Show the elapsed game time and wheel power.
        telemetry.addData("Status", "Run Time: " + runtime.toString());
        NormalizedRGBA colors = colorSensor.getNormalizedColors();
        telemetry.addData("Red", colors.red);
        telemetry.addData("Green", colors.green);
        telemetry.addData("Blue", colors.blue);
        telemetry.addData("Alpha (Proximity/Light)", colors.alpha);
        telemetry.addData("Current Velocity", flywheel.getVelocity());
        telemetry.addData("target velocity", flywheelspeed);
        telemetry.addData("encocer pos: ", encoder.getCurrentPosition());
        telemetry.addData("encoderf pos: ", encoderf.getCurrentPosition());
        telemetry.update();

    }

    /*
     * Code to run ONCE after the driver hits STOP
     */
    @Override
    public void stop() {
    }

}