package org.firstinspires.ftc.teamcode.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp(name="DOG", group="Linear OpMode")
public class SPARK extends LinearOpMode {

    private Servo hip1;
    private Servo hip2;
    private Servo hip3;
    private Servo hip4;
    private Servo ankle1;
    private Servo ankle2;
    private Servo ankle3;
    private Servo ankle4;
    private Servo ears;
    private Servo mouth;
    private Servo tail;

    @Override
    public void runOpMode() {
        telemetry.addData("Status", "Initialized");
        telemetry.update();

        // Initialize servos
        ears = hardwareMap.get(Servo.class, "ear");
        mouth = hardwareMap.get(Servo.class, "mouth");

        hip1 = hardwareMap.get(Servo.class, "leftFrontHip");
        hip2 = hardwareMap.get(Servo.class, "rightFrontHip");
        hip3 = hardwareMap.get(Servo.class, "leftBackHip");
        hip4 = hardwareMap.get(Servo.class, "rightBackHip");

        ankle1 = hardwareMap.get(Servo.class, "leftFrontAnkle");
        ankle2 = hardwareMap.get(Servo.class, "rightFrontAnkle");
        ankle3 = hardwareMap.get(Servo.class, "leftBackAnkle");
        ankle4 = hardwareMap.get(Servo.class, "rightBackAnkle");

        tail = hardwareMap.get(Servo.class, "tail");


        hip1.setPosition(0.5); //0.6 move back
        hip2.setPosition(0.5); // 0.4 back
        hip3.setPosition(0.5); // 0.6 back
        hip4.setPosition(0.5); // 0.4 back
        ankle1.setPosition(0.5); // 0.4 back
        ankle2.setPosition(0.5); // 0.4 back
        ankle3.setPosition(0.5); // 0.6 back
        ankle4.setPosition(0.5); // 0.6 back
        tail.setPosition(0.2);

        waitForStart();


        boolean isSitting = false;
        boolean earMouthAnimating = false;
        long earMouthStartTime = 0;
        int earMouthStep = 0;
        int step = 0;
        long lastStepTime = System.currentTimeMillis();
        boolean tailDirection = true;  // true = right, false = left
        long lastTailMoveTime = System.currentTimeMillis();
        long tailDelay = 300;  // Adjust speed of wag

        while (opModeIsActive()) {
            double forward = gamepad1.left_stick_y;
            long now = System.currentTimeMillis();

            boolean walkFwd = forward < -0.2;
            boolean isWalking = walkFwd;


            if (isWalking && now - lastStepTime > 300) {
                if (walkFwd) {
                    walkForwardStep(step);
                }

                step = (step + 1) % 4;
                lastStepTime = now;
            } else if (!isSitting && !isWalking) {
                setStandingPose();
            }


            // Reset pose
            if (gamepad1.y) {
                isSitting = false;
                 hip1.setPosition(0.5); //0.6 move back
                hip2.setPosition(0.5); // 0.4 back
        hip3.setPosition(0.5); // 0.6 back
        hip4.setPosition(0.5); // 0.4 back
        ankle1.setPosition(0.5); // 0.4 back
        ankle2.setPosition(0.5); // 0.4 back
        ankle3.setPosition(0.5); // 0.6 back
        ankle4.setPosition(0.5); // 0.6 back

            }

            long currentTime = System.currentTimeMillis();
            if (currentTime - lastTailMoveTime > tailDelay) {
                lastTailMoveTime = currentTime;

                if (tailDirection) {
                    tail.setPosition(0.1);
                    mouth.setPosition(0.2);
                    ears.setPosition(0.75);// Right
                } else {
                    tail.setPosition(0.4);
                    mouth.setPosition(0.4);
                   ears.setPosition(0.6);// Left
                }

                tailDirection = !tailDirection;  // Flip direction
            }
            if (gamepad1.dpad_up && !earMouthAnimating) {
                earMouthAnimating = true;
                earMouthStartTime = System.currentTimeMillis();
                earMouthStep = 0;
            }


            // Sit
            if (gamepad1.a) {isSitting = true;
                hip1.setPosition(0.5);
                hip2.setPosition(0.5);
                hip3.setPosition(0.6);
                hip4.setPosition(0.4);
                ankle1.setPosition(0.5);
                ankle2.setPosition(0.5);
                ankle3.setPosition(0.3);
                ankle4.setPosition(0.7);


            }


            // Ears and Mouth
            if (earMouthAnimating) {
                long timeElapsed = System.currentTimeMillis() - earMouthStartTime;

                switch (earMouthStep) {
                    case 0:
                        if (timeElapsed >= 0) {
                            mouth.setPosition(0.6);
                            ears.setPosition(0.0);
                            earMouthStep++;
                        }
                        break;
                    case 1:
                        if (timeElapsed >= 300) {
                            mouth.setPosition(0.5);
                            ears.setPosition(0.2);
                            earMouthStep++;
                        }
                        break;
                    case 2:
                        // Animation done
                        earMouthAnimating = false;
                        break;
                }
            }
        }
    }




    private void walkForwardStep(int step) {
        switch (step) {

            case 0:
                hip1.setPosition(0.2); ankle1.setPosition(0.4);
                hip4.setPosition(0.75); ankle4.setPosition(0.6);
                break;
            case 1:
                hip1.setPosition(0.35);
        hip2.setPosition(0.6);

        hip3.setPosition(0.4);
        hip4.setPosition(0.6);

        ankle1.setPosition(0.4);
        ankle2.setPosition(0.6);

        ankle3.setPosition(0.4);
        ankle4.setPosition(0.6);
                break;
            case 2:
                hip3.setPosition(0.2);
                ankle3.setPosition(0.4);

                hip2.setPosition(0.75);
                ankle2.setPosition(0.6);
                break;
            case 3:
                hip1.setPosition(0.35);
                hip2.setPosition(0.6);

                hip3.setPosition(0.4);
                hip4.setPosition(0.6);

                ankle1.setPosition(0.4);
                ankle2.setPosition(0.6);

                ankle3.setPosition(0.4);
                ankle4.setPosition(0.6);
                break;
        }
    }
//turn
//    private void walkForwardStep(int step) {
//        switch (step) {
//            case 0:
//                hip1.setPosition(0.35);
//                hip2.setPosition(0.6);
//                hip3.setPosition(0.4);
//                hip4.setPosition(0.6);
//                ankle1.setPosition(0.4);//25
//                ankle2.setPosition(0.6);
//                ankle3.setPosition(0.4);//35
//                ankle4.setPosition(0.6);
//
//            case 1:
//                hip1.setPosition(0.2); ankle1.setPosition(0.4);
//                hip4.setPosition(0.75); ankle4.setPosition(0.6);// front-left forward
//                break;
//            case 2:
//                hip1.setPosition(0.35);
//                hip2.setPosition(0.6);
//
//                hip3.setPosition(0.4);
//                hip4.setPosition(0.6);
//
//                ankle1.setPosition(0.4);
//                ankle2.setPosition(0.6);
//
//                ankle3.setPosition(0.4);
//                ankle4.setPosition(0.6);// front-right forward
//                break;
//            case 3:
//                hip3.setPosition(0.2);
//                ankle3.setPosition(0.4);
//
//                hip2.setPosition(0.75);
//                ankle2.setPosition(0.6); // back-left forward
//                break;
//            case 4:
//                hip1.setPosition(0.35);
//                hip2.setPosition(0.6);
//
//                hip3.setPosition(0.4);
//                hip4.setPosition(0.6);
//
//                ankle1.setPosition(0.4);
//                ankle2.setPosition(0.6);
//
//                ankle3.setPosition(0.4);
//                ankle4.setPosition(0.6);// front-right forward
//                break;
//        }
//    }


    private void setStandingPose() {

        hip1.setPosition(0.5);
        hip2.setPosition(0.5);
        hip3.setPosition(0.5);
        hip4.setPosition(0.5);
        ankle1.setPosition(0.5);
        ankle2.setPosition(0.5);
        ankle3.setPosition(0.5);
        ankle4.setPosition(0.5);

    }
}







