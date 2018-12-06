package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

@TeleOp(name="Jeffery", group="Linear Opmode")

public class Jeffery extends LinearOpMode{
    private ElapsedTime runtime = new ElapsedTime();
    private DcMotor leftDriveMotor;
    private DcMotor rightDriveMotor;
    private DcMotor armBotFrameMotor;
    private DcMotor armArmFrameMotor;
    private DcMotor armIntakeMotor;

    //VARIABLES===================================================================VARIABLES
    //motors
    private int armBotFrameMotorPosition = 0;
    private int armArmFrameMotorPosition = 0;
    private double intakeSpeed = .8;
    private double outtakeSpeed = -.8;
    //servos
//END VARIABLES================================================================END VARIABLES

    @Override
    public void runOpMode()
    {
        telemetry.addData("Status", "Initialized");
        telemetry.update();

//INITIALIZE HARDWARE=================================================================INITIALIZE HARDWARE
        //Motors
        leftDriveMotor = hardwareMap.get(DcMotor.class, "leftDriveMotor");
        rightDriveMotor = hardwareMap.get(DcMotor.class, "rightDriveMotor");
        armBotFrameMotor = hardwareMap.get(DcMotor.class, "armBotFrameMotor");
        armArmFrameMotor = hardwareMap.get(DcMotor.class, "armArmFrameMotor");
        armIntakeMotor = hardwareMap.get(DcMotor.class, "armIntakeMotor");

        //Set Motor Direction
        leftDriveMotor.setDirection(DcMotor.Direction.FORWARD);
        rightDriveMotor.setDirection(DcMotor.Direction.REVERSE);
        armBotFrameMotor.setDirection(DcMotor.Direction.FORWARD);
        armArmFrameMotor.setDirection(DcMotor.Direction.FORWARD);
        armIntakeMotor.setDirection(DcMotor.Direction.FORWARD);
        //Reset Positions
        armBotFrameMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        armArmFrameMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        armIntakeMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
//END INITIALIZING HARDWARE==========================================================END INITIALIZING HARDWARE

        waitForStart();
        runtime.reset();

        while(opModeIsActive())
        {
            drive();
            arm();
            intake();
            telemetry.addData("armBotFramePower", armBotFrameMotor.getPower());
            telemetry.addData("armArmFramePower", armArmFrameMotor.getPower());
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
        leftDriveMotor.setPower(leftPower);
        rightDriveMotor.setPower(rightPower);
    }
    private void arm()
    {
        if(gamepad2.left_stick_y != 0)
        {
            armBotFrameMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            armBotFrameMotorPosition = armBotFrameMotor.getCurrentPosition();
            if(gamepad2.left_stick_y > 0)
            {
                armBotFrameMotor.setPower(Math.pow(gamepad2.left_stick_y, 2));
            }
            else
            {
                armBotFrameMotor.setPower(-Math.pow(gamepad2.left_stick_y, 2));
            }
        }
        else
        {
            armBotFrameMotor.setTargetPosition(armBotFrameMotorPosition);
            armBotFrameMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        }
        if(gamepad2.right_stick_y != 0)
        {
            armArmFrameMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            armArmFrameMotorPosition = armArmFrameMotor.getCurrentPosition();
            if(gamepad2.right_stick_y > 0)
            {
                armArmFrameMotor.setPower(Math.pow(gamepad2.right_stick_y, 2));
            }
            else
            {
                armArmFrameMotor.setPower(-Math.pow(gamepad2.right_stick_y, 2));
            }
        }
        else
        {
            armArmFrameMotor.setTargetPosition(armArmFrameMotorPosition);
            armArmFrameMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        }
    }
    private void intake()
    {
        //set state
        if(gamepad2.right_bumper)
        {
            armIntakeMotor.setPower(outtakeSpeed);
        }
        else if(gamepad2.left_bumper)
        {
            armIntakeMotor.setPower(intakeSpeed);
        }
        else
        {
            armIntakeMotor.setPower(0);
        }
    }
}
