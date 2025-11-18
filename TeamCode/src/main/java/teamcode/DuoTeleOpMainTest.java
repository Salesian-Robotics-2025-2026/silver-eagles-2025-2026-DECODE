package teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
@TeleOp(name = "DuoTeleOpMainTest", group = "TeleOp")
public class DuoTeleOpMainTest extends OpMode {

    //--------- MOTORS ------------

    /**
     *  Place the motors that you will be using here!
     */
    private DcMotor leftFront;
    private DcMotor rightFront;
    private DcMotor leftRear;
    private DcMotor rightRear;
    private DcMotor intake;
    private DcMotor leftOuttake;
    private DcMotor rightOuttake;

    //--------- SERVOS ------------

    /**
     *  Place the servos that you will be using here!
     */

    private CRServo rightFrontMidtake;
    private CRServo leftFrontMidtake;
    private CRServo rightRearMidtake;
    private CRServo leftRearMidtake;



    //--------- EDITING VARIABLES ------------

    /**
     * The values given below are their defaults.
     * These variables are designed to be configured by the driver in teleOp.
     */
    private double outtakeDampening = 0.5;

    //--------------------------------------------------------

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

        //-- OUTTAKE SHOOTING

        if (gamepad1.a) {
            leftOuttake.setPower(-1 * outtakeDampening);
            rightOuttake.setPower(1 * outtakeDampening);
        }
        else
        {
            leftOuttake.setPower(0);
            rightOuttake.setPower(0);
        }

        //-- INTAKE

        if (gamepad1.b){
            //intake.setPower(-1 * outtakeDampening);
            intake.setPower(-1);
            rightFrontMidtake.setPower(1);
           /// rightRearMidtake.setPower(1);
            leftFrontMidtake.setPower(-1);
            leftRearMidtake.setPower(-1);





        }
        else {
            intake.setPower(0);
            rightFrontMidtake.setPower(0);
            //rightRearMidtake.setPower(0);
            leftFrontMidtake.setPower(0);
            leftRearMidtake.setPower(0);




        }
    }


    //-- INTAKE CONTROLS

    /**
     * This method controls gamepad2.
     * gamepad2 is mainly being used to make adjustments to the dampening values mid-teleOp.
     */
    public void gamepad2Controls(){

        //-- OUTTAKE MOTOR ADJUSTMENT

        if (gamepad2.dpad_up){

            outtakeDampening += 0.01;

            if (outtakeDampening > 1 ){
                outtakeDampening = 1;
            }

        }
        else if (gamepad2.dpad_down){
            outtakeDampening -= 0.01;

            if (outtakeDampening < 0 ){
                outtakeDampening = 0;
            }
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
        double powerLF = getPower(forwardPower + leftStickX - rotationPower, false);  // Left Front
        double powerRF = getPower(forwardPower - leftStickX - rotationPower, false);  // Right Front
        double powerLR = getPower(forwardPower - leftStickX + rotationPower, false);  // Left Rear
        double powerRR = getPower(forwardPower + leftStickX + rotationPower, false);  // Right Rear

        // Set motor powers
        leftFront.setPower(powerLF);
        rightFront.setPower(powerRF);
        leftRear.setPower(-powerLR);
        rightRear.setPower(-powerRR);
    }

    /**
     * This method initializes the motors
     */
    public void initMotors(){
        intake = hardwareMap.dcMotor.get("intake");
        leftOuttake =  hardwareMap.dcMotor.get("leftOuttake");
        rightOuttake =  hardwareMap.dcMotor.get("rightOuttake");
    }

    /**
     * This method initializes the servos.
     */

    public void initServos(){
        leftFrontMidtake = hardwareMap.get(CRServo.class, "leftFrontMidtake");
        leftRearMidtake = hardwareMap.get(CRServo.class, "leftRearMidtake");
        rightFrontMidtake = hardwareMap.get(CRServo.class, "rightFrontMidtake");
        ///rightRearMidtake = hardwareMap.get(CRServo.class, "rightRearMidtake");
    }

    /**
     * This method sets the zero power behavior for the wheels.
     */

    public void initWheels(){

        //-- MOTORS

        leftFront = hardwareMap.dcMotor.get("leftFront");
        rightFront = hardwareMap.dcMotor.get("rightFront");
        leftRear = hardwareMap.dcMotor.get("leftRear");
        rightRear = hardwareMap.dcMotor.get("rightRear");

        //--- BEHAVIOUR

        leftFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        leftRear.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightRear.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }

    /**
     * This method resets & sets up motor encoders.
     */
    public void setEncoders(){

        //-- Resets the encoders

        rightFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightRear.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftRear.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        intake.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftOuttake.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightOuttake.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        //-- sets up encoders after their reset

        rightFront.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        leftFront.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rightRear.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        leftRear.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        intake.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        leftOuttake.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rightOuttake.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }

    /**
     * This method initializes the robot, controlled by the init button-
     * -in the driver station.
     */

    @Override
    public void init() {
        telemetry.addLine("Initalizing bebo V2.0...");
        initMotors();
        initWheels();
        initServos();
        setEncoders();
    }

    /**
     * This method runs every frame, essential for gamepad controls and such.
     */

    @Override
    public void loop() {

        driveTrain();
        gamepad2Controls();
        gamepad1Controls();

        telemetry.update();
    }


}
