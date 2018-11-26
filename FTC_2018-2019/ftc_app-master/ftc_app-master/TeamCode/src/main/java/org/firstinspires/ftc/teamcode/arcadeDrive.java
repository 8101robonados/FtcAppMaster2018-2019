package org.firstinspires.ftc.teamcode;

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
    private double intakeSpeed = .8;
    private double outtakeSpeed = -.8;
    private int liftPosition = 0;
    private enum intake
    {
        out {
            @Override
            public intake back()
            {
                return this;
            }
        },
        stop,
        in {
            @Override
            public intake next()
            {
                return this;
            }
        };
        public intake next()
        {
            return values()[ordinal() + 1];
        }
        public intake back()
        {
            return values()[ordinal() - 1];
        }
    }
    intake intakeState = intake.stop;
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
        hangLockMotor = hardwareMap.get(DcMotor.class, "hangLockMotor");
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
            hangLock();
            intake();
            telemetry.addData("hangLockMotor", "Position: " + hangLockMotor.getCurrentPosition());
            telemetry.addData("liftMotor", "Position: " + liftMotor.getCurrentPosition());
            telemetry.addData("Intake Status", intakeState);
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
            liftMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            liftPosition = liftMotor.getCurrentPosition();
            liftMotor.setPower(gamepad2.left_stick_y);
        }
        else
        {
            liftMotor.setTargetPosition(liftPosition);
            liftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
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
            intakeState.next();
        }
        else if(gamepad1.left_bumper)
        {
            intakeState.back();
        }

        //set power
        if(intakeState == intake.out)
        {
            intakeMotor.setPower(outtakeSpeed);
        }
        else if(intakeState == intake.stop)
        {
            intakeMotor.setPower(0);
        }
        else if(intakeState == intake.in)
        {
            intakeMotor.setPower(intakeSpeed);
        }
    }
    private void hangLock()
    {
        hangLockMotor.setPower(gamepad2.right_stick_x);
    }
}
