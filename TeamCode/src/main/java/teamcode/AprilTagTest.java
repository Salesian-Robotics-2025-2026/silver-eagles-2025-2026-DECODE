package teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import teamcode.mechanisms.AprilTagWebcam;

@TeleOp(name = "AprilTagTest", group = "TeleOp")
public class AprilTagTest extends OpMode {

    AprilTagWebcam aprilTagWebcam = new AprilTagWebcam();

    @Override
    public void init() {

    }

    @Override
    public void loop() {
    }

}
