// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import java.util.concurrent.ConcurrentHashMap;

import com.ctre.phoenix6.configs.CANcoderConfiguration;
import com.ctre.phoenix6.configs.MotionMagicConfigs;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.controls.MotionMagicVoltage;
import com.ctre.phoenix6.controls.VelocityTorqueCurrentFOC;
import com.ctre.phoenix6.controls.VoltageOut;
import com.ctre.phoenix6.hardware.CANcoder;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.FeedbackSensorSourceValue;
import com.ctre.phoenix6.signals.GravityTypeValue;
import com.ctre.phoenix6.signals.SensorDirectionValue;
import com.ctre.phoenix6.signals.StaticFeedforwardSignValue;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import frc.robot.Constants;

@SuppressWarnings("unused")
public class drive extends SubsystemBase {
  public final TalonFX test_motor1 = new TalonFX(Constants.Drive.motor1ID, "rio");
  public final TalonFX test_motor2 = new TalonFX(Constants.Drive.motor2ID, "rio");
  public final TalonFX test_motor3 = new TalonFX(Constants.Drive.motor3ID, "rio");
  public final TalonFX test_motor4 = new TalonFX(Constants.Drive.motor4ID, "rio");
  public final TalonFX test_motor5 = new TalonFX(Constants.Drive.motor5ID, "rio");
  public final TalonFX test_motor6 = new TalonFX(Constants.Drive.motor6ID, "rio");
  public final TalonFX test_motor7 = new TalonFX(Constants.Drive.motor7ID, "rio");
  public final TalonFX test_motor8 = new TalonFX(Constants.Drive.motor8ID, "rio");
  //声明电机
  private final VelocityTorqueCurrentFOC velocity_request1 = new VelocityTorqueCurrentFOC(0.0).withSlot(1);
  private final VelocityTorqueCurrentFOC velocity_request2 = new VelocityTorqueCurrentFOC(0.0).withSlot(1);
  private final VelocityTorqueCurrentFOC velocity_request3 = new VelocityTorqueCurrentFOC(0.0).withSlot(1);
  private final VelocityTorqueCurrentFOC velocity_request4 = new VelocityTorqueCurrentFOC(0.0).withSlot(1);


  private final MotionMagicVoltage position_request1 = new MotionMagicVoltage(0.0).withSlot(0);
  private final MotionMagicVoltage position_request2 = new MotionMagicVoltage(0.0).withSlot(0);
  private final MotionMagicVoltage position_request3 = new MotionMagicVoltage(0.0).withSlot(0);
  private final MotionMagicVoltage position_request4 = new MotionMagicVoltage(0.0).withSlot(0);
  //声明请求 1357速度，2468位置
  
  private final CANcoder test_cancoder1 = new CANcoder(Constants.Drive.cancoder1ID, "rio");
  private final CANcoder test_cancoder2 = new CANcoder(Constants.Drive.cancoder2ID, "rio");
  private final CANcoder test_cancoder3 = new CANcoder(Constants.Drive.cancoder3ID, "rio");
  private final CANcoder test_cancoder4 = new CANcoder(Constants.Drive.cancoder4ID, "rio");



  private final int[] expectposition = {0, 10, 20};
  private int positioni = 0;

  public int flag=1;//flag

  // public void setVoltage1(double voltage) {
  //   test_motor1.setControl(drive_request1.withOutput(voltage));
  //   test_motor2.setControl(drive_request2.withOutput(voltage));
  // }

  public void setposition(double position1, double position2) {
    test_motor1.setControl(position_request1.withPosition(position1));
    test_motor2.setControl(position_request2.withPosition(position2));
    // test_motor3.setControl(position_request3.withPosition(position));
    // test_motor4.setControl(position_request4.withPosition(position));
  }
  public void setvelocity(double velocity1, double velocity2) {
    test_motor1.setControl(velocity_request1.withVelocity(velocity1));
    test_motor2.setControl(velocity_request2.withVelocity(velocity2));
    //test_motor3.setControl(velocity_request.withPosition(velocity));
    //test_motor4.setControl(velocity_request.withPosition(velocity));
  }
  
  public void setallvelocity(double velocity1,double velocity2,double velocity3,double velocity4) {
    test_motor2.setControl(velocity_request1.withVelocity(velocity1));
    test_motor4.setControl(velocity_request2.withVelocity(velocity2));
    test_motor6.setControl(velocity_request3.withVelocity(velocity3));
    test_motor8.setControl(velocity_request4.withVelocity(velocity4));
  }
  public void setallposition(double position1,double position2,double position3,double position4) {
    test_motor1.setControl(position_request1.withPosition(position1));
    test_motor3.setControl(position_request2.withPosition(position2));
    test_motor5.setControl(position_request3.withPosition(position3));
    test_motor7.setControl(position_request4.withPosition(position4));
  }

  public void changeposition(){
    positioni++;
    positioni%=3;
  }

  public drive() {
    var motorEncoderConfigs = new CANcoderConfiguration();
    motorEncoderConfigs.MagnetSensor.AbsoluteSensorDiscontinuityPoint=0.5;//电机180°对应范围
    motorEncoderConfigs.MagnetSensor.SensorDirection=SensorDirectionValue.Clockwise_Positive;
    motorEncoderConfigs.MagnetSensor.MagnetOffset=0.077393;//FL
    test_cancoder1.getConfigurator().apply(motorEncoderConfigs);
    motorEncoderConfigs.MagnetSensor.MagnetOffset=0.297607;//FR
    test_cancoder2.getConfigurator().apply(motorEncoderConfigs);
    motorEncoderConfigs.MagnetSensor.MagnetOffset=0.046630859375;//BR
    test_cancoder3.getConfigurator().apply(motorEncoderConfigs);
    motorEncoderConfigs.MagnetSensor.MagnetOffset=0.392334;//BL
    test_cancoder4.getConfigurator().apply(motorEncoderConfigs);

      
    var motorConfigs = new TalonFXConfiguration();

    motorConfigs.Slot0.kS = 0.14;
    motorConfigs.Slot0.kV = 0.0;
    motorConfigs.Slot0.kA = 0;
    motorConfigs.Slot0.kP = 5;
    motorConfigs.Slot0.kI = 0;
    motorConfigs.Slot0.kD = 0;

    motorConfigs.MotionMagic.MotionMagicAcceleration = 100; // Acceleration is around 40 rps/s
    motorConfigs.MotionMagic.MotionMagicCruiseVelocity = 200; // Unlimited cruise velocity
    motorConfigs.MotionMagic.MotionMagicExpo_kV = 0.12; // kV is around 0.12 V/rps
    motorConfigs.MotionMagic.MotionMagicExpo_kA = 0.1; // Use a slower kA of 0.1 V/(rps/s)
    motorConfigs.MotionMagic.MotionMagicJerk = 0; // Jerk is around 0

    motorConfigs.Slot0.GravityType = GravityTypeValue.Arm_Cosine;
    motorConfigs.Slot0.StaticFeedforwardSign = StaticFeedforwardSignValue.UseClosedLoopSign;


    var motorConfigs2 = new TalonFXConfiguration();
    motorConfigs.Slot1.kS = 1.6;
    motorConfigs.Slot1.kV = 0.17;
    motorConfigs.Slot1.kA = 0;
    motorConfigs.Slot1.kP = 7;
    motorConfigs.Slot1.kI = 0;
    motorConfigs.Slot1.kD = 0;
    
    motorConfigs.Slot1.GravityType = GravityTypeValue.Arm_Cosine;
    motorConfigs.Slot1.StaticFeedforwardSign = StaticFeedforwardSignValue.UseVelocitySign;
    
    test_motor2.getConfigurator().apply(motorConfigs);
    test_motor4.getConfigurator().apply(motorConfigs);
    test_motor6.getConfigurator().apply(motorConfigs);
    test_motor8.getConfigurator().apply(motorConfigs);

    motorConfigs.Feedback.FeedbackSensorSource = FeedbackSensorSourceValue.FusedCANcoder;
    motorConfigs.Feedback.RotorToSensorRatio = 13;
    motorConfigs.Feedback.FeedbackRemoteSensorID = test_cancoder1.getDeviceID();
    test_motor1.getConfigurator().apply(motorConfigs);
    motorConfigs.Feedback.FeedbackRemoteSensorID = test_cancoder2.getDeviceID();
    test_motor3.getConfigurator().apply(motorConfigs);
    motorConfigs.Feedback.FeedbackRemoteSensorID = test_cancoder3.getDeviceID();
    test_motor5.getConfigurator().apply(motorConfigs);
    motorConfigs.Feedback.FeedbackRemoteSensorID = test_cancoder4.getDeviceID();
    test_motor7.getConfigurator().apply(motorConfigs);
  }
  public boolean is1reach(double position, double accept_error) {
    return Math.abs(test_motor1.getPosition().getValueAsDouble() - position) < accept_error;
  }
  public boolean is2reach(double position, double accept_error) {
    return Math.abs(test_motor2.getPosition().getValueAsDouble() - position) < accept_error;
  }

  public Command setpositionCommand(double position1, double position2) {
    return runOnce(() -> {
      setposition(position1,position2);
    });
  }
  public Command setvelocityCommand(double velocity1, double velocity2) {
    return runOnce(() -> {
      setvelocity(velocity1,velocity2);
    });
  }

  
  public Command changeCommand() {
    return runOnce(() -> {
      setvelocity(Constants.Task1.motor1v * flag, Constants.Task1.motor2v * flag);
      flag *= -1;
    });
  }
}
/*电机参数调试：
Kg - output to overcome gravity (output)

Ks - Velocity Sign: unused; Closed-Loop Sign: output to overcome static friction (output)

Kv - unused, as there is no target velocity

Ka - unused, as there is no target acceleration

Kp - output per unit of error in position (output/rotation)
  决定了电机的力量大小，并在力量增加过程中开始震动并增大
Ki - output per unit of integrated error in position (output/(rotation*s))
  一般不用
Kd - output per unit of error derivative in position (output/rps)


1. Set all gains to zero.

2. Determine Kg if using an elevator or arm.
  克服重力的参数，kg从0开始，逐渐增加，直到松手时电机不动。
3. Select the appropriate Static Feedforward Sign for your closed-loop type.
  如果是速度控制选usevelositysign,如果是位置控制选closedloopsign
4. Increase ks until just before the motor moves.
  逐步增加ks，直到电机微微有反应。
5. If using velocity setpoints, increase kv until the output velocity closely matches the velocity setpoints.
  若使用速度控制，且需要在以特定速度，逐步增加kv，直到输出速度与设定速度接近。
  （kv是一个放大系数，用于提高速度到目标速度）
6. Increase kp until the output starts to oscillate around the setpoint.
  逐步增加kp，直到当前位置/速度开始在设定点附近振荡。
7. Increase kd as much as possible without introducing jittering to the response.
  逐步增加kd，直到引入新的震动。














 */