package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

public class hardware
{
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
//END VARIABLES================================================================END VARIABLES
}
