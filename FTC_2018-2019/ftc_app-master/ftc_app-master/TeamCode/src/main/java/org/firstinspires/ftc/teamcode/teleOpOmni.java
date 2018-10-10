package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

import static java.lang.Math.abs;
import static java.lang.Math.atan;
import static java.lang.Math.cos;
import static java.lang.Math.pow;
import static java.lang.Math.sin;
import static java.lang.Math.tanh;
import static java.lang.Math.toDegrees;
import static java.lang.Math.toRadians;
import static java.lang.StrictMath.sqrt;

/**
 * Created by Luke on 9/26/2017.
 * basic Tank Drive TeleOp
 */

@TeleOp(name = "TeleOp Omni", group = "Practice")
public class teleOpOmni extends LinearOpMode
{
    private DcMotor motorLeftFront;
    private DcMotor motorRightFront;
    private DcMotor motorLeftBack;
    private DcMotor motorRightBack;


    double xVector;
    double yVector;
    double vector;
    double angle;
    double turn;
    @Override
    public void runOpMode() throws InterruptedException
    {
        motorLeftFront = hardwareMap.dcMotor.get("motorLeftFront");
        motorRightFront = hardwareMap.dcMotor.get("motorRightFront");
        motorLeftBack = hardwareMap.dcMotor.get("motorLeftBack");
        motorRightBack = hardwareMap.dcMotor.get("motorRightBack");

        motorLeftFront.setDirection(DcMotor.Direction.REVERSE);
        motorLeftBack.setDirection(DcMotor.Direction.REVERSE);

        waitForStart();

        while(opModeIsActive())
        {
            if(gamepad1.left_stick_x==0)
            {
                drive();
            }
            else
            {
                turn();
            }
            idle();
        }
    }
    private void drive()
    {
        yVector=-gamepad1.right_stick_y;
        xVector=gamepad1.right_stick_x;
        if ((yVector != 0) || (xVector != 0))
        {
            vector = sqrt(pow(xVector, 2) + pow(yVector, 2));
            if ((xVector >= 0) && (yVector >= 0))
            {
                if(xVector==0)
                {
                    angle = 135; //90+45
                }
                else
                {
                    angle = toDegrees(tanh(abs(yVector) / abs(xVector))) + 45;
                }
                yVector = vector * sin(toRadians(angle));
                xVector = vector * cos(toRadians(angle));
            }
            else if ((xVector <= 0) && (yVector >= 0))
            {
                if(yVector==0)
                {
                    angle=180+45;
                }
                else
                {
                    angle = toDegrees(tanh(abs(xVector) / abs(yVector)) + 135);
                }
                yVector = vector * sin(toRadians(angle));
                xVector = vector * cos(toRadians(angle));
            }
            else if ((xVector <= 0) && (yVector <= 0))
            {
                if(xVector==0)
                {
                    angle=270+45;
                }
                else
                {
                    angle = toDegrees(tanh(abs(yVector) / abs(xVector)) + 225);
                }
                yVector = vector * sin(toRadians(angle));
                xVector = vector * cos(toRadians(angle));
            }
            else if ((xVector >= 0) && (yVector <= 0))
            {
                if(yVector==0)
                {
                    angle=360+45;
                }
                else
                {
                    angle = toDegrees(tanh(abs(xVector) / abs(yVector)) + 315);
                }
                if(angle>=360)
                {
                    angle=angle-360;
                }
                xVector = vector * sin(toRadians(angle));
                yVector = vector * cos(toRadians(angle));
            }
            if ((xVector <= 1) && (yVector <= 1))
            {
                if(gamepad1.left_bumper)
                {
                    motorLeftFront.setPower(.27*xVector);
                    motorRightFront.setPower(.27*yVector);
                    motorLeftBack.setPower(.27*yVector);
                    motorRightBack.setPower(.27*xVector);
                }
                else
                {
                    motorLeftFront.setPower(xVector);
                    motorRightFront.setPower(yVector);
                    motorLeftBack.setPower(yVector);
                    motorRightBack.setPower(xVector);
                }

            }
        }
        else
        {
            motorLeftFront.setPower(0);
            motorRightFront.setPower(0);
            motorLeftBack.setPower(0);
            motorRightBack.setPower(0);
        }
    }
    private void turn()
    {
        if(gamepad1.left_bumper)
        {
            turn = .25*gamepad1.left_stick_x;
        }
        else
        {
            turn = gamepad1.left_stick_x;
        }
        motorLeftFront.setPower(turn);
        motorRightFront.setPower(-turn);
        motorLeftBack.setPower(turn);
        motorRightBack.setPower(-turn);
    }
}
