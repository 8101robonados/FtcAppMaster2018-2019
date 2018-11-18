package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;
import java.util.ArrayList;
import java.util.Collection;

@Autonomous(name = "autonomous", group = "auto")
public class autonomous extends LinearOpMode {

    // Declare OpMode members.
    private ElapsedTime runtime = new ElapsedTime();
    private DcMotor leftDrive;
    private DcMotor rightDrive;
    private DcMotor liftMotor;
    private DcMotor intakeMotor;
    private DcMotor hangArmMotor;
    private Servo armServoLeft;
    private Servo armServoRight;

//VARIABLES===================================================================VARIABLES
    //servos
    private double armServoLeftUp = 0;
    private double armServoLeftDown = 1;
    private double armServoRightUp = armServoLeftDown;
    private double armServoRightDown = armServoLeftUp;
    //motors
    private double intakeSpeed = 1;
    private int liftPosition = 0;
    private int leftDriveTarget = 0;
    private int rightDriveTarget = 0;
    private int liftMotorTarget = 0;
    private int intakeMotorTarget = 0;
    private int hangArmMotorTarget = 0;
//END VARIABLES================================================================END VARIABLES

    @Override
    public void runOpMode()
    {
        telemetry.addData("Status", "Initialized");
        telemetry.update();

//INITIALIZE HARDWARE=================================================================INITIALIZE HARDWARE
        //motors
        leftDrive  = hardwareMap.get(DcMotor.class, "leftDrive");
        rightDrive = hardwareMap.get(DcMotor.class, "rightDrive");
        liftMotor = hardwareMap.get(DcMotor.class, "liftMotor");
        intakeMotor = hardwareMap.get(DcMotor.class, "intakeMotor");
        hangArmMotor = hardwareMap.get(DcMotor.class, "hangArmMotor");
        //servos
        armServoLeft = hardwareMap.get(Servo.class, "armServoLeft");
        armServoRight = hardwareMap.get(Servo.class, "armServoRight");
        //Set Motor Direction
        leftDrive.setDirection(DcMotor.Direction.FORWARD);
        rightDrive.setDirection(DcMotor.Direction.REVERSE);
        liftMotor.setDirection(DcMotor.Direction.FORWARD);
        intakeMotor.setDirection(DcMotor.Direction.FORWARD);
//END INITIALIZING HARDWARE==========================================================END INITIALIZING HARDWARE

//INITIALIZE POSITIONS===============================================================INITIALIZE POSITIONS
        //servos
        armServoLeft.setPosition(armServoLeftDown);
        armServoRight.setPosition(armServoRightDown);
//END INITIALIZING POSITIONS==========================================================END INITIALIZING POSITIONS

//RESET ENCODERS======================================================================RESET ENCODERS
        leftDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        liftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        liftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        intakeMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        intakeMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        hangArmMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        hangArmMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//END RESETTING ENCODERS==============================================================END RESETTING ENCODERS

        waitForStart();
        runtime.reset();
        if (opModeIsActive())
        {
            moveLiftMotor(280, 2);
            moveHangArmMotor(50,1);
            drive(280, 280, 2);
        }
    }
    private void drive(int rightDriveAddedDistance, int leftDriveAddedDistance, double time)
    {
        runtime.reset();
        rightDriveTarget += rightDriveAddedDistance;
        leftDriveTarget +=leftDriveAddedDistance;
        rightDrive.setTargetPosition(rightDriveTarget);
        leftDrive.setTargetPosition(leftDriveTarget);
        while(runtime.seconds()<time && !rightDrive.isBusy() && !leftDrive.isBusy())
        {
            telemetry.addData("Left Drive Target: ", leftDriveTarget);
            telemetry.addData("Left Drive Position: ", leftDrive.getCurrentPosition());
            telemetry.addData("Right Drive Target: ", rightDriveTarget);
            telemetry.addData("Right Drive Position: ", rightDrive.getCurrentPosition());
            telemetry.update();
        }
    }
    private void moveLiftMotor(int addedDistance, double time)
    {
        runtime.reset();
        liftMotorTarget += addedDistance;
        liftMotor.setTargetPosition(liftMotorTarget);
        while(runtime.seconds()<time && !liftMotor.isBusy())
        {
            telemetry.addData("Lift Motor Target: ", liftMotorTarget);
            telemetry.addData("Lift Motor Position: ", liftMotor.getCurrentPosition());
            telemetry.update();
        }
    }
    private void moveHangArmMotor(int addedDistance, double time)
    {
        runtime.reset();
        hangArmMotorTarget += addedDistance;
        hangArmMotor.setTargetPosition(hangArmMotorTarget);
        while(runtime.seconds()<time && !hangArmMotor.isBusy())
        {
            telemetry.addData("Hang Arm Motor Target: ", hangArmMotorTarget);
            telemetry.addData("Hang ARm Motor Position: ", hangArmMotor.getCurrentPosition());
            telemetry.update();
        }
    }
}
