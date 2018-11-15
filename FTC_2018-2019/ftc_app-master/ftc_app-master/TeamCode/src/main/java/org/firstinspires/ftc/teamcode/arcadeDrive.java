package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;


@TeleOp(name="arcadeDrive", group="Linear Opmode")
public class arcadeDrive extends LinearOpMode {

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
//END INITIALIZING HARDWARE==========================================================END INITIALIZING HARDWARE

        waitForStart();
        runtime.reset();

        while (opModeIsActive())
        {
            drive();
            lift();
            hangArm();
            intake();
            telemetry.addData("Status", "Run Time: " + runtime.toString());
            telemetry.addData("Arm Servo Left", "Position: " + armServoLeft.getPosition());
            telemetry.addData("Arm Servo Right", "Position: " + armServoRight.getPosition());
            telemetry.addData("hangArmMotor", "Position: " + hangArmMotor.getCurrentPosition());
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
        liftMotor.setPower(gamepad2.left_stick_y);
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
        if(gamepad1.right_bumper)
        {
            intakeMotor.setPower(intakeSpeed);
        }
        else if(gamepad1.left_bumper)
        {
            intakeMotor.setPower(0);
        }
    }
    private void hangArm()
    {
        hangArmMotor.setPower(gamepad2.right_stick_x);
    }
}
