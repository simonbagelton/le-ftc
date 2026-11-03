package org.firstinspires.ftc.teamcode.commands;
import com.pedropathing.ivy.Command;
import com.qualcomm.robotcore.hardware.DcMotor;
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
import com.pedropathing.ivy.Command.*;
import com.pedropathing.ivy.Command;
import com.pedropathing.ivy.behaviors.*;
import java.util.Set;
public class Shoot implements Command {
    private final DcMotor flywheel;
    private final CRServo flap;
    

    public Shoot(DcMotor flywheel, CRServo flap) {
        this.flywheel = flywheel;
        this.flap = flap;
        
    }

    @Override
    public void start() {
        flywheel.setPower(-1);
    }

    @Override
    public void execute() {
        // Optional continuous logic
    }

    @Override
    public boolean done() {
    
        return true;
    }

    @Override
    public void end(EndCondition endCondition) {
        
    }
    @Override
public BlockedBehavior blockedBehavior() {
    return BlockedBehavior.CANCEL;
}

@Override
public ConflictBehavior conflictBehavior() {
    return ConflictBehavior.OVERRIDE;
}

@Override
public InterruptedBehavior interruptedBehavior() {
    return InterruptedBehavior.END;
}
@Override
public int priority() {
    return 0;
}
@Override
public Set<Object> requirements() {
    return Set.of(flywheel);
} 
}
