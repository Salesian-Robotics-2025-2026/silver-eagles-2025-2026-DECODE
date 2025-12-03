package teamcode;

import static trclib.timer.TrcTimer.sleep;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp(name = "SpeedServoTest", group = "TeleOp")
public class SpeedServoTest extends OpMode {

    private CRServo servo;
    public void gamepad1Controls(){
        if (gamepad1.a) {
            servo.setPower(-1);
            sleep(60);
            servo.setPower(0);
            sleep(125);
            servo.setPower(1);
            sleep(60);
            servo.setPower(0);
        }
    }

    @Override
    public void init() {
        servo = hardwareMap.get(CRServo.class, "servo");
        servo.setPower(0);
    }

    @Override
    public void loop() {
        gamepad1Controls();
    }

}
