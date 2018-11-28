package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;
import java.util.ArrayList;
import java.util.Collection;
//hang arm +2000 to open -1000 for lift
@Autonomous(name = "autonomous", group = "auto")
public class autonomous extends LinearOpMode {

    // Declare OpMode members.
    private ElapsedTime runtime = new ElapsedTime();
    private DcMotor leftDrive;
    private DcMotor rightDrive;
    private DcMotor leftLiftMotor;
    private DcMotor rightLiftMotor;
    private DcMotor intakeMotor;
    private DcMotor hangLockMotor;
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
    private int leftLiftMotorTarget = 0;
    private int rightLiftMotorTarget = 0;
    private int intakeMotorTarget = 0;
    private int hangLockMotorTarget = 0;
    int[] motorTargets = {leftDriveTarget, rightDriveTarget, leftLiftMotorTarget, rightLiftMotorTarget, intakeMotorTarget, hangLockMotorTarget};
    DcMotor[] motors = {leftDrive, rightDrive, leftLiftMotor, rightLiftMotor, intakeMotor, hangLockMotor};

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
        leftLiftMotor = hardwareMap.get(DcMotor.class, "leftLiftMotor");
        rightLiftMotor = hardwareMap.get(DcMotor.class, "rightLiftMotor");
        intakeMotor = hardwareMap.get(DcMotor.class, "intakeMotor");
        hangLockMotor = hardwareMap.get(DcMotor.class, "hangLockMotor");
        //servos
        armServoLeft = hardwareMap.get(Servo.class, "armServoLeft");
        armServoRight = hardwareMap.get(Servo.class, "armServoRight");
        //Set Motor Direction
        leftDrive.setDirection(DcMotor.Direction.FORWARD);
        rightDrive.setDirection(DcMotor.Direction.REVERSE);
        leftLiftMotor.setDirection(DcMotor.Direction.FORWARD);
        rightLiftMotor.setDirection(DcMotor.Direction.REVERSE);
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
        leftLiftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftLiftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        intakeMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        intakeMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        hangLockMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        hangLockMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//END RESETTING ENCODERS==============================================================END RESETTING ENCODERS
        waitForStart();
        runtime.reset();
        if (opModeIsActive())
        {
            moveMotors(0, 0, -1000, -1000, 0, 0, 3);
            moveMotors(0, 0, 0, 0, 0, 2000, 3);
            moveMotors(2000, 2000, 1000, 1000, 0, -2000, 3);
        }
    }
    private void moveMotors(int leftDriveMovement, int rightDriveMovement, int leftLiftMovement, int rightLiftMovement, int intakeMotorMovement, int hangLockMotorMovement, int time)
    {
        runtime.reset();
        int [] motorMovementArray = {leftDriveMovement, rightDriveMovement, leftLiftMovement, rightLiftMovement, intakeMotorMovement, hangLockMotorMovement, 0};
        for(int i = 0; i<=motors.length; i++)
        {
            motors[i].setTargetPosition(motorMovementArray[i]+motorTargets[i]);
            motorTargets[i] += motorMovementArray[i];
        }
        while(runtime.seconds()<time)
        {
            telemetry.addData("LeftDrivePosition: ", leftDrive.getCurrentPosition());
            telemetry.addData("LeftDriveTarget: ", leftDrive.getTargetPosition());

            telemetry.addData("RightDrivePosition: ", rightDrive.getCurrentPosition());
            telemetry.addData("RightDriveTarget: ", rightDrive.getTargetPosition());

            telemetry.addData("LeftLiftPosition: ", leftLiftMotor.getCurrentPosition());
            telemetry.addData("LeftLiftTarget: ", leftLiftMotor.getTargetPosition());

            telemetry.addData("RightLiftPosition: ", rightLiftMotor.getCurrentPosition());
            telemetry.addData("RightLiftTarget: ", rightLiftMotor.getTargetPosition());

            telemetry.addData("IntakeMotorPosition: ", intakeMotor.getCurrentPosition());
            telemetry.addData("IntakeMotorTarget: ", intakeMotor.getTargetPosition());

            telemetry.addData("HangLockPosition: ", hangLockMotor.getCurrentPosition());
            telemetry.addData("HangLockTarget: ", hangLockMotor.getTargetPosition());

            telemetry.addData("TimeToMove: ", time);
            telemetry.addData("Time: ", getRuntime());

            telemetry.update();
        }
    }
}
