package org.firstinspires.ftc.teamcode.commands;
import com.pedropathing.ivy.Command;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.pedropathing.ivy.Command;
import com.pedropathing.ivy.behaviors.*;
import java.util.Set;
public class StopIntake implements Command {
    private final DcMotor intake;
    

    public StopIntake(DcMotor intake) {
        this.intake = intake;
        
    }

    @Override
    public void start() {
        intake.setPower(0);
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
    return Set.of(intake);
}
}
