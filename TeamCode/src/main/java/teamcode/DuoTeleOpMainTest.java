package teamcode;

import static trclib.timer.TrcTimer.sleep;

import  com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.ColorSensor;
import org.opencv.core.Core;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
@TeleOp(name = "DuoTeleOpMainTest", group = "TeleOp")
public class DuoTeleOpMainTest extends OpMode {

    //--------- MOTORS ------------

    /**
     *  Place the motors that you will be using here!
     */

    private DcMotor leftFront, rightFront, leftRear, rightRear;
    private DcMotor leftIntake, rightIntake, outtake;

    //--------- SERVOS ------------

    /**
     *  Place the servos that you will be using here!
     */

    private Servo flipper;

    //--------- VARIABLES ------------

    /**
     *  Place the variables that you will be using here!
     */

    // Adrian was in the code
    private double flipperFlipPosition = 90;

    //-------- METHODS ---------------
    //-- Place your methods here!

    /**
     * this method gets the power for the motors and limits it to 1 or -1.
     * <powerLevel></double> : The power of the motor it is getting.
     * <excludeNegatives></boolean> : Whether or not the method should return a negative power.
     */
    public double getPower(double powerLevel, boolean excludeNegatives) {

        if (powerLevel > 1) {
            return 1; //-- maximum is 1
        }

        else if (powerLevel < -1 && !excludeNegatives){
            return -1; //-- minimum is -1
        }

        return powerLevel;  //-- in-between 1 and -1.
    }
    /**
     * This method controls gamepad1.
     */
    public void gamepad1Controls(){

    }


    /**
     * This method controls gamepad2.
     * gamepad2 is mainly being used to make adjustments to the dampening values mid-teleOp.
     */
    public void gamepad2Controls(){

        //-- OUTTAKE SHOOTING

        if (gamepad2.x) {
            outtake.setPower(1);
        }
        else
        {
            outtake.setPower(0);
        }

        //-- INTAKE

        if (gamepad2.b){

            leftIntake.setPower(1);
            rightIntake.setPower(1);
        }
        else {
            leftIntake.setPower(0);
            rightIntake.setPower(0);
        }

        if (gamepad2.a) {
            flipper.setPosition(flipperFlipPosition);
        }
        else {
            flipper.setPosition(0);
        }
    }

    /**
     * This method handles the movement of the robot.
     */
    public void driveTrain(){
        double leftStickY = gamepad1.left_stick_x; // Y axis for forward/backwards
        double leftStickX = gamepad1.left_stick_y; // Left stick X for strafe
        double rightStickX = gamepad1.right_stick_x; // Right stick X for rotation

        double forwardPower = -leftStickY * 0.8; // Forward/backward movement
        double rotationPower = rightStickX * 0.5; // Rotation

        // Calculate power for each motor

        double powerLF = forwardPower + leftStickX + rotationPower;  // Left Front
        double powerRF = forwardPower - leftStickX + rotationPower;  // Right Front
        double powerLR = forwardPower - leftStickX - rotationPower;  // Left Rear
        double powerRR = forwardPower + leftStickX - rotationPower;  // Right Rear

        // Set motor powers
        leftFront.setPower(-powerLF);
        rightFront.setPower(-powerRF);
        leftRear.setPower(powerLR);
        rightRear.setPower(powerRR);
    }

    /**
     * This method initializes the motors
     */
    public void initMotors(){

        //-- MOTORS

        leftIntake =  hardwareMap.dcMotor.get("leftIntake");
        rightIntake =  hardwareMap.dcMotor.get("rightIntake");
        outtake =  hardwareMap.dcMotor.get("outtake");

        leftFront = hardwareMap.dcMotor.get("leftFront");
        rightFront = hardwareMap.dcMotor.get("rightFront");
        leftRear = hardwareMap.dcMotor.get("leftRear");
        rightRear = hardwareMap.dcMotor.get("rightRear");

        //--- BEHAVIOUR

        leftFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        leftRear.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightRear.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        rightIntake.setDirection(DcMotor.Direction.REVERSE);
    }

    /**
     * This method initializes the servos.
     */

    public void initServos(){
        flipper = hardwareMap.get(Servo.class, "flipper");
        flipper.setDirection(Servo.Direction.REVERSE);
        flipper.setPosition(0);
    }

    /**
     * This method resets & sets up motor encoders.
     */
    public void setEncoders(){
        rightFront.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        leftFront.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rightRear.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        leftRear.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        outtake.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        leftIntake.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rightIntake.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }

    /**
     * This method initializes the robot, controlled by the init button-
     * -in the driver station.
     */

    @Override
    public void init() {
        telemetry.addLine("Initalizing bebo V2.0...");

        initMotors();
        initServos();
        setEncoders();
    }

    /**
     * This method runs every frame; essential for gamepad controls and such.
     */

    @Override
    public void loop() {

        driveTrain();
        gamepad2Controls();
        gamepad1Controls();

        telemetry.update();
    }


}
