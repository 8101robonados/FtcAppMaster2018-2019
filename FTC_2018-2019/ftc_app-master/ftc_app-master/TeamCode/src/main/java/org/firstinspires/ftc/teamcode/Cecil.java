package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;


@TeleOp(name="Cecil", group="Linear Opmode")
public class Cecil extends LinearOpMode {

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
    private Servo lockingServo;

//VARIABLES===================================================================VARIABLES
    //servos
    private double armServoLeftUp = 0;
    private double armServoLeftDown = 1;
    private double armServoRightUp = armServoLeftDown;
    private double armServoRightDown = armServoLeftUp;

    private double lockingServoLocked = .8;
    private double lockingServoUnlocked = 0;
    //motors
    private double intakeSpeed = 1;
    private double outtakeSpeed = -1;
    private int liftPosition = 0;
    private int liftDown = 0;
    private int liftUp =
            1000;
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
        lockingServo = hardwareMap.get(Servo.class, "lockingServo");
        //Set Motor Direction
        leftDrive.setDirection(DcMotor.Direction.FORWARD);
        rightDrive.setDirection(DcMotor.Direction.REVERSE);
        leftLiftMotor.setDirection(DcMotor.Direction.FORWARD);
        rightLiftMotor.setDirection(DcMotor.Direction.REVERSE);
        intakeMotor.setDirection(DcMotor.Direction.FORWARD);
        //Reset Position
        leftLiftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightLiftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
//END INITIALIZING HARDWARE==========================================================END INITIALIZING HARDWARE

//INITIALIZE POSITIONS===============================================================INITIALIZE POSITIONS
        //servos
        armServoLeft.setPosition(armServoLeftDown);
        armServoRight.setPosition(armServoRightDown);
        lockingServo.setPosition(lockingServoUnlocked);
//END INITIALIZING HARDWARE==========================================================END INITIALIZING HARDWARE

        waitForStart();
        runtime.reset();

        while (opModeIsActive())
        {
            drive();
            lift();
            hangLock();
            intake();
            telemetry.addData("hangLockMotor", "Position: " + hangLockMotor.getCurrentPosition());
            telemetry.addData("leftLiftMotor", "Position: " + leftLiftMotor.getCurrentPosition());
            telemetry.addData("rightLiftMotor", "Position: " + rightLiftMotor.getCurrentPosition());
            telemetry.update();
        }
    }
    private void drive()
    {
        double stickY = -gamepad1.left_stick_y;
        double stickX = gamepad1.right_stick_x;

        double leftPower = stickY+stickX; //stickX to the left is -
        double rightPower = stickY-stickX;

        leftPower    = Range.clip(leftPower, -1.0, 1.0) ;
        rightPower   = Range.clip(rightPower, -1.0, 1.0) ;

        //sets power
        leftDrive.setPower(leftPower);
        rightDrive.setPower(rightPower);
    }
    private void lift()
    {
        if(gamepad2.left_stick_y != 0)
        {
            leftLiftMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            rightLiftMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            leftLiftMotor.setPower(gamepad2.left_stick_y);
            rightLiftMotor.setPower(gamepad2.left_stick_y);
        }
        else if(gamepad2.right_bumper)
        {
            leftLiftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            rightLiftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            leftLiftMotor.setTargetPosition(liftUp);
            rightLiftMotor.setTargetPosition(liftUp);
            leftLiftMotor.setPower(.3);
            rightLiftMotor.setPower(.3);
        }
        else if(gamepad2.left_bumper)
        {
            leftLiftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            rightLiftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            leftLiftMotor.setTargetPosition(liftDown);
            rightLiftMotor.setTargetPosition(liftDown);
            leftLiftMotor.setPower(.3);
            rightLiftMotor.setPower(.3);
        }
        else if(leftLiftMotor.getMode() == DcMotor.RunMode.RUN_WITHOUT_ENCODER)
        {
            leftLiftMotor.setPower(0);
            rightLiftMotor.setPower(0);
        }
        if(gamepad2.a)
        {
            armServoLeft.setPosition(armServoLeftDown);
            armServoRight.setPosition(armServoRightDown);
        }
        else if(gamepad2.x)
        {
            armServoLeft.setPosition(armServoLeftUp);
            armServoRight.setPosition(armServoRightUp);
        }
    }
    private void intake()
    {
        //set state
        if(gamepad1.right_bumper)
        {
            intakeMotor.setPower(outtakeSpeed);
        }
        else if(gamepad1.left_bumper)
        {
            intakeMotor.setPower(intakeSpeed);
        }
        else
        {
            intakeMotor.setPower(0);
        }
    }
    private void hangLock()
    {
        hangLockMotor.setPower(gamepad2.right_stick_x);
        if(gamepad2.b)
        {
            lockingServo.setPosition(lockingServoUnlocked);
        }
        else if(gamepad2.y)
        {
            lockingServo.setPosition(lockingServoLocked);
        }
    }
}
