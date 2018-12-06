package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;
import java.util.ArrayList;
import java.util.Collection;
import java.lang.InterruptedException;
//hang arm +2000 to open -1000 for lift
@Autonomous(name = "autonomous", group = "auto")
public class CecilRedSilverFull extends LinearOpMode {

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
        motors[0]  = hardwareMap.get(DcMotor.class, "leftDrive");
        motors[1] = hardwareMap.get(DcMotor.class, "rightDrive");
        motors[2] = hardwareMap.get(DcMotor.class, "leftLiftMotor");
        motors[3] = hardwareMap.get(DcMotor.class, "rightLiftMotor");
        motors[4] = hardwareMap.get(DcMotor.class, "intakeMotor");
        motors[5] = hardwareMap.get(DcMotor.class, "hangLockMotor");
        //servos
        armServoLeft = hardwareMap.get(Servo.class, "armServoLeft");
        armServoRight = hardwareMap.get(Servo.class, "armServoRight");
        lockingServo = hardwareMap.get(Servo.class, "lockingServo");
        //Set Motor Direction
        motors[0].setDirection(DcMotor.Direction.FORWARD);
        motors[1].setDirection(DcMotor.Direction.REVERSE);
        motors[2].setDirection(DcMotor.Direction.FORWARD);
        motors[3].setDirection(DcMotor.Direction.REVERSE);
        motors[4].setDirection(DcMotor.Direction.FORWARD);
        motors[5].setDirection(DcMotor.Direction.FORWARD);
//END INITIALIZING HARDWARE==========================================================END INITIALIZING HARDWARE

//INITIALIZE POSITIONS===============================================================INITIALIZE POSITIONS
        //servos
        armServoLeft.setPosition(armServoLeftDown);
        armServoRight.setPosition(armServoRightDown);
        lockingServo.setPosition(lockingServoLocked);
//END INITIALIZING POSITIONS==========================================================END INITIALIZING POSITIONS

//RESET ENCODERS======================================================================RESET ENCODERS
        motors[0].setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motors[0].setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motors[1].setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motors[1].setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motors[2].setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motors[2].setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motors[3].setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motors[3].setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motors[4].setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motors[4].setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motors[5].setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motors[5].setMode(DcMotor.RunMode.RUN_USING_ENCODER);
//END RESETTING ENCODERS==============================================================END RESETTING ENCODERS

        waitForStart();
        runtime.reset();
        if (opModeIsActive())
        {
            lockingServo.setPosition(lockingServoUnlocked);
            sleep(1000);
            moveMotors(0, 0, 1000, 0, 0, 0, 0,0,-1, 0, 0, 0,5);
            moveMotors(0, 0, 0, 0, 0, 1700, 0,0,0,0,0,1,5);
            moveMotors(2000, 2000, 1000, 0, 0, -1700, 1,1,0,1,0, 1,5);
        }
    }
    private void moveMotors(int leftDriveMovement, int rightDriveMovement, int leftLiftMovement, int rightLiftMovement, int intakeMotorMovement, int hangLockMotorMovement, double leftDrivePower, double rightDrivePower, double leftLiftPower, double rightLiftPower, double intakeMotorPower, double hangLockPower, double time)
    {
        runtime.reset();
        int [] motorMovementArray = {leftDriveMovement, rightDriveMovement, leftLiftMovement, rightLiftMovement, intakeMotorMovement, hangLockMotorMovement};
        double [] motorPowerArray = {leftDrivePower, rightDrivePower, leftLiftPower, rightLiftPower, intakeMotorPower, hangLockPower};
        for(int i = 0; i<motors.length; i++)
        {
            motors[i].setTargetPosition(motorMovementArray[i]+motorTargets[i]);
            motors[i].setPower(motorPowerArray[i]);
            motorTargets[i] += motorMovementArray[i];
        }
        while(runtime.seconds()<time)
        {
            for(int i = 0; i<motors.length; i++)
            {
                if( i!=3 )
                {
                    if(motorMovementArray[i] != 0)
                    {
                        motors[i].setPower(motorPowerArray[i]*(((double)(motorTargets[i])-(double)(motors[i].getCurrentPosition()))/((double)(motorMovementArray[i]))));
                    }
                    else
                    {
                        motors[i].setPower(0);
                    }
                }
                else
                {
                    if(motorMovementArray[i] != 0)
                    {
                        motors[i].setPower(motorPowerArray[i]*((motorTargets[2]-motors[2].getCurrentPosition())/(motorMovementArray[2])));
                    }
                    else
                    {
                        motors[i].setPower(0);
                    }
                }
            }
            /*for(int i = 0; i<motors.length; i++)
            {
                if(i!=3)
                {
                    if(motors[i].getTargetPosition()-motors[i].getCurrentPosition() > 0)
                    {
                        motors[i].setPower(motorPowerArray[i]);
                    }
                    else if(motors[i].getTargetPosition()-motors[i].getCurrentPosition() < 0)
                    {
                        motors[i].setPower(-motorPowerArray[i]);
                    }
                    else
                    {
                        motors[i].setPower(0);
                    }
                }
                else
                {
                    if(motors[2].getTargetPosition()-motors[2].getCurrentPosition() > 0)
                    {
                        motors[3].setPower(motorPowerArray[3]);
                    }
                    else if(motors[2].getTargetPosition()-motors[2].getCurrentPosition() < 0)
                    {
                        motors[3].setPower(-motorPowerArray[3]);
                    }
                    else
                    {
                        motors[3].setPower(0);
                    }
                }
            }*/
            telemetry.addData("LeftDrivePosition: ", motors[0].getCurrentPosition());
            telemetry.addData("LeftDriveTarget: ", motors[0].getTargetPosition());

            telemetry.addData("RightDrivePosition: ", motors[1].getCurrentPosition());
            telemetry.addData("RightDriveTarget: ", motors[1].getTargetPosition());

            telemetry.addData("LeftLiftPosition: ", motors[2].getCurrentPosition());
            telemetry.addData("LeftLiftTarget: ", motors[2].getTargetPosition());

            telemetry.addData("RightLiftPosition: ", motors[3].getCurrentPosition());
            telemetry.addData("RightLiftTarget: ", motors[3].getTargetPosition());

            telemetry.addData("IntakeMotorPosition: ", motors[4].getCurrentPosition());
            telemetry.addData("IntakeMotorTarget: ", motors[4].getTargetPosition());

            telemetry.addData("HangLockPosition: ", motors[5].getCurrentPosition());
            telemetry.addData("HangLockTarget: ", motors[5].getTargetPosition());

            telemetry.addData("TimeToMove: ", time);
            telemetry.addData("Time: ", getRuntime());

            telemetry.update();
        }
        for(int i = 0; i<motors.length; i++)
        {
            motors[i].setPower(0);
        }
    }
}
