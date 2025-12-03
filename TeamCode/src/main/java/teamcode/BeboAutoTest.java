/*
 * Copyright (c) 2025 Titan Robotics Club (http://www.titanrobotics.com)
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package teamcode;

import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.PathChain;
import com.pedropathing.util.Timer;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

/**
 * This class contains the Autonomous Mode program.
 * god bless pratt dude
 */
@Autonomous(name="BeboAutoTest", group="FtcTeam")
public class BeboAutoTest extends OpMode
{
    private Follower follower;
    private Timer pathTimer, opModeTimer;

    public enum pathState{
        // START POSITION_END POSITION
        // DRIVE > MOVEMENT STATE
        // SHOOT > ATTEMPT TO SCORE ARTIFACT

        DRIVE_STARTPOS_SHOOT_POS,
        SHOOT_PRELOAD
    }

    pathState pathState;

    public PathChain goToAprilTag;
    public PathChain turnToAprilTag;
    public PathChain goToCollector;

    public void buildPaths(Follower follower) {
        goToAprilTag = follower
                .pathBuilder()
                .addPath(
                        new BezierLine(new Pose(108.000, 8.000), new Pose(108.197, 123.729))
                )
                .setLinearHeadingInterpolation(Math.toRadians(90), Math.toRadians(180))
                .build();

        turnToAprilTag = follower
                .pathBuilder()
                .addPath(
                        new BezierLine(new Pose(108.197, 123.729), new Pose(108.197, 123.729))
                )
                .setLinearHeadingInterpolation(
                        Math.toRadians(30),
                        Math.toRadians(0)
                )
                .build();

        goToCollector = follower
                .pathBuilder()
                .addPath(
                        new BezierLine(new Pose(108.197, 123.729), new Pose(123.993, 72.395))
                )
                .setLinearHeadingInterpolation(
                        Math.toRadians(0),
                        Math.toRadians(0)
                ).build();
    }

    public void statePathUpdate(){
        switch(pathState){
            case DRIVE_STARTPOS_SHOOT_POS:
                break;
        }
    }

    @Override
    public void init() {

    }

    @Override
    public void loop() {

    }
}