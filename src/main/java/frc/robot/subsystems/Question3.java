// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix6.configs.CANcoderConfiguration;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.controls.MotionMagicVoltage;
import com.ctre.phoenix6.controls.VelocityTorqueCurrentFOC;
import com.ctre.phoenix6.controls.VoltageOut;
//Voltage Out 不受PID影响
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.FeedbackSensorSourceValue;
import com.ctre.phoenix6.signals.GravityTypeValue;
import com.ctre.phoenix6.signals.SensorDirectionValue;
import com.ctre.phoenix6.signals.StaticFeedforwardSignValue;
import com.ctre.phoenix6.hardware.CANcoder;

import edu.wpi.first.units.measure.Velocity;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;



public class Question3 extends SubsystemBase {
  
    private final TalonFX m_Steer_1 = new TalonFX(Constants.MOTORinQ1.MOTOR_Steer_1_ID, "rio");
    private final TalonFX m_Steer_2 = new TalonFX(Constants.MOTORinQ1.MOTOR_Steer_2_ID, "rio");
    private final TalonFX m_Steer_3 = new TalonFX(Constants.MOTORinQ1.MOTOR_Steer_3_ID, "rio");
    private final TalonFX m_Steer_4 = new TalonFX(Constants.MOTORinQ1.MOTOR_Steer_4_ID, "rio");

    private final TalonFX m_Drive_1 = new TalonFX(Constants.MOTORinQ1.MOTOR_Drive_1_ID, "rio");
    private final TalonFX m_Drive_2 = new TalonFX(Constants.MOTORinQ1.MOTOR_Drive_2_ID, "rio");
    private final TalonFX m_Drive_3 = new TalonFX(Constants.MOTORinQ1.MOTOR_Drive_3_ID, "rio");
    private final TalonFX m_Drive_4 = new TalonFX(Constants.MOTORinQ1.MOTOR_Drive_4_ID, "rio");

    private final CANcoder cancoder_fl = new CANcoder(Constants.MOTORinQ1.CANCODER_1_ID,"rio");
    private final CANcoder cancoder_fr = new CANcoder(Constants.MOTORinQ1.CANCODER_2_ID,"rio");
    private final CANcoder cancoder_br = new CANcoder(Constants.MOTORinQ1.CANCODER_3_ID,"rio");
    private final CANcoder cancoder_bl = new CANcoder(Constants.MOTORinQ1.CANCODER_4_ID,"rio");

    private final MotionMagicVoltage m_test_motor_request = new MotionMagicVoltage(0.0);
    private final VelocityTorqueCurrentFOC m_test_motor_request2 = new VelocityTorqueCurrentFOC(0.0);

    public void setmotorPosition(double Position) {
      m_Steer_1.setControl(m_test_motor_request.withPosition(Position));
      m_Steer_2.setControl(m_test_motor_request.withPosition(Position));
      m_Steer_3.setControl(m_test_motor_request.withPosition(Position));
      m_Steer_4.setControl(m_test_motor_request.withPosition(Position));
    }

    public void setmotorVelocity(double Velocity) {
      m_Drive_1.setControl(m_test_motor_request2.withVelocity(Velocity));
      m_Drive_2.setControl(m_test_motor_request2.withVelocity(Velocity));
      m_Drive_3.setControl(m_test_motor_request2.withVelocity(Velocity));
      m_Drive_4.setControl(m_test_motor_request2.withVelocity(Velocity));
    }

  public Question3() {
    
        var motorEncoderConfigs = new CANcoderConfiguration();
        motorEncoderConfigs.MagnetSensor.MagnetOffset=-0.077393;
        motorEncoderConfigs.MagnetSensor.AbsoluteSensorDiscontinuityPoint=0.5;//实际生活中的电机位置映射到什么范围
        motorEncoderConfigs.MagnetSensor.SensorDirection=SensorDirectionValue.Clockwise_Positive;
        cancoder_fl.getConfigurator().apply(motorEncoderConfigs);

        var motorEncoderConfigs2 = new CANcoderConfiguration();
        motorEncoderConfigs2.MagnetSensor.MagnetOffset=0.297607;
        motorEncoderConfigs2.MagnetSensor.AbsoluteSensorDiscontinuityPoint=0.5;//实际生活中的电机位置映射到什么范围
        motorEncoderConfigs2.MagnetSensor.SensorDirection=SensorDirectionValue.Clockwise_Positive;
        cancoder_fr.getConfigurator().apply(motorEncoderConfigs2);
    
        var motorEncoderConfigs3 = new CANcoderConfiguration();
        motorEncoderConfigs3.MagnetSensor.MagnetOffset=0.046630859375;
        motorEncoderConfigs3.MagnetSensor.AbsoluteSensorDiscontinuityPoint=0.5;//实际生活中的电机位置映射到什么范围
        motorEncoderConfigs3.MagnetSensor.SensorDirection=SensorDirectionValue.Clockwise_Positive;
        cancoder_br.getConfigurator().apply(motorEncoderConfigs3);
    
        var motorEncoderConfigs4 = new CANcoderConfiguration();
        motorEncoderConfigs4.MagnetSensor.MagnetOffset=0.392334;
        motorEncoderConfigs4.MagnetSensor.AbsoluteSensorDiscontinuityPoint=0.5;//实际生活中的电机位置映射到什么范围
        motorEncoderConfigs4.MagnetSensor.SensorDirection=SensorDirectionValue.Clockwise_Positive;
    
        cancoder_bl.getConfigurator().apply(motorEncoderConfigs4);

        var motorConfigs1 = new TalonFXConfiguration();
        //每个电机都有的固定参数     
        motorConfigs1.Slot0.kS = 1.85;
        motorConfigs1.Slot0.kV = 0.0;
        motorConfigs1.Slot0.kA = 0;             
        motorConfigs1.Slot0.kP = 6;
        motorConfigs1.Slot0.kI = 0;
        motorConfigs1.Slot0.kD = 0.1;
        motorConfigs1.Slot0.GravityType = GravityTypeValue.Arm_Cosine;
        motorConfigs1.Slot0.StaticFeedforwardSign = StaticFeedforwardSignValue.UseVelocitySign;
        //高级控制才用到下面的参数
        motorConfigs1.MotionMagic.MotionMagicAcceleration = 100; // Acceleration is around 40 rps/s
        motorConfigs1.MotionMagic.MotionMagicCruiseVelocity = 200; // Unlimited cruise velocity
        motorConfigs1.MotionMagic.MotionMagicExpo_kV = 0.12; // kV is around 0.12 V/rps
        motorConfigs1.MotionMagic.MotionMagicExpo_kA = 0.1; // Use a slower kA of 0.1 V/(rps/s)
        motorConfigs1.MotionMagic.MotionMagicJerk = 0; // Jerk is around 0'     
        //选择这套参数的电机
        motorConfigs1.Feedback.FeedbackRemoteSensorID = cancoder_fl.getDeviceID();
        motorConfigs1.Feedback.FeedbackSensorSource = FeedbackSensorSourceValue.FusedCANcoder;
        motorConfigs1.Feedback.RotorToSensorRatio = 13;   
        m_Steer_1.getConfigurator().apply(motorConfigs1);  

        var motorConfigs2 = new TalonFXConfiguration();
        //每个电机都有的固定参数     
        motorConfigs2.Slot0.kS = 1.85;
        motorConfigs2.Slot0.kV = 0.0;
        motorConfigs2.Slot0.kA = 0;             
        motorConfigs2.Slot0.kP = 6;
        motorConfigs2.Slot0.kI = 0;
        motorConfigs2.Slot0.kD = 0.1;
        motorConfigs2.Slot0.GravityType = GravityTypeValue.Arm_Cosine;
        motorConfigs2.Slot0.StaticFeedforwardSign = StaticFeedforwardSignValue.UseVelocitySign;
        //高级控制才用到下面的参数
        motorConfigs2.MotionMagic.MotionMagicAcceleration = 100; // Acceleration is around 40 rps/s
        motorConfigs2.MotionMagic.MotionMagicCruiseVelocity = 200; // Unlimited cruise velocity
        motorConfigs2.MotionMagic.MotionMagicExpo_kV = 0.12; // kV is around 0.12 V/rps
        motorConfigs2.MotionMagic.MotionMagicExpo_kA = 0.1; // Use a slower kA of 0.1 V/(rps/s)
        motorConfigs2.MotionMagic.MotionMagicJerk = 0; // Jerk is around 0'     
        //cancoder与电机建立练习
        motorConfigs2.Feedback.FeedbackRemoteSensorID = cancoder_fr.getDeviceID();
        motorConfigs2.Feedback.FeedbackSensorSource = FeedbackSensorSourceValue.FusedCANcoder;
        motorConfigs2.Feedback.RotorToSensorRatio = 13;  
        m_Steer_2.getConfigurator().apply(motorConfigs2);
        
        var motorConfigs3 = new TalonFXConfiguration();
        //每个电机都有的固定参数     
        motorConfigs3.Slot0.kS = 1.85;
        motorConfigs3.Slot0.kV = 0.0;
        motorConfigs3.Slot0.kA = 0;             
        motorConfigs3.Slot0.kP = 6;
        motorConfigs3.Slot0.kI = 0;
        motorConfigs3.Slot0.kD = 0.1;
        motorConfigs3.Slot0.GravityType = GravityTypeValue.Arm_Cosine;
        motorConfigs3.Slot0.StaticFeedforwardSign = StaticFeedforwardSignValue.UseVelocitySign;
        //高级控制才用到下面的参数
        motorConfigs3.MotionMagic.MotionMagicAcceleration = 100; // Acceleration is around 40 rps/s
        motorConfigs3.MotionMagic.MotionMagicCruiseVelocity = 200; // Unlimited cruise velocity
        motorConfigs3.MotionMagic.MotionMagicExpo_kV = 0.12; // kV is around 0.12 V/rps
        motorConfigs3.MotionMagic.MotionMagicExpo_kA = 0.1; // Use a slower kA of 0.1 V/(rps/s)
        motorConfigs3.MotionMagic.MotionMagicJerk = 0; // Jerk is around 0'    
        //cancoder与电机建立练习
        motorConfigs3.Feedback.FeedbackRemoteSensorID = cancoder_br.getDeviceID();
        motorConfigs3.Feedback.FeedbackSensorSource = FeedbackSensorSourceValue.FusedCANcoder;
        motorConfigs3.Feedback.RotorToSensorRatio = 13;    
        m_Steer_3.getConfigurator().apply(motorConfigs3);
        
        var motorConfigs4 = new TalonFXConfiguration();
        motorConfigs4.Slot0.kS = 1.85;
        motorConfigs4.Slot0.kV = 0.0;
        motorConfigs4.Slot0.kA = 0;             
        motorConfigs4.Slot0.kP = 6;
        motorConfigs4.Slot0.kI = 0;
        motorConfigs4.Slot0.kD = 0.1;
        motorConfigs4.Slot0.GravityType = GravityTypeValue.Arm_Cosine;
        motorConfigs4.Slot0.StaticFeedforwardSign = StaticFeedforwardSignValue.UseVelocitySign;
        //高级控制才用到下面的参数
        motorConfigs4.MotionMagic.MotionMagicAcceleration = 100; // Acceleration is around 40 rps/s
        motorConfigs4.MotionMagic.MotionMagicCruiseVelocity = 200; // Unlimited cruise velocity
        motorConfigs4.MotionMagic.MotionMagicExpo_kV = 0.12; // kV is around 0.12 V/rps
        motorConfigs4.MotionMagic.MotionMagicExpo_kA = 0.1; // Use a slower kA of 0.1 V/(rps/s)
        motorConfigs4.MotionMagic.MotionMagicJerk = 0; // Jerk is around 0'     
        //cancoder与电机建立练习
        motorConfigs4.Feedback.FeedbackRemoteSensorID = cancoder_bl.getDeviceID();
        motorConfigs4.Feedback.FeedbackSensorSource = FeedbackSensorSourceValue.FusedCANcoder;
        motorConfigs4.Feedback.RotorToSensorRatio = 13;    
        m_Steer_4.getConfigurator().apply(motorConfigs4);

        var motorConfigs = new TalonFXConfiguration();


        //每个电机都有的固定参数     
        motorConfigs.Slot0.kS = 0.14;
        motorConfigs.Slot0.kV = 0.0;            //直接控制
        motorConfigs.Slot0.kA = 0;
        motorConfigs.Slot0.kP = 10;
        motorConfigs.Slot0.kI = 0;
        motorConfigs.Slot0.kD = 0;
        motorConfigs.Slot0.GravityType = GravityTypeValue.Arm_Cosine;
        motorConfigs.Slot0.StaticFeedforwardSign = StaticFeedforwardSignValue.UseClosedLoopSign;
        //高级控制才用到下面的参数
        // motorConfigs.MotionMagic.MotionMagicAcceleration = 100; // Acceleration is around 40 rps/s
        // motorConfigs.MotionMagic.MotionMagicCruiseVelocity = 200; // Unlimited cruise velocity
        // motorConfigs.MotionMagic.MotionMagicExpo_kV = 0.12; // kV is around 0.12 V/rps
        // motorConfigs.MotionMagic.MotionMagicExpo_kA = 0.1; // Use a slower kA of 0.1 V/(rps/s)
        // motorConfigs.MotionMagic.MotionMagicJerk = 0; // Jerk is around 0'
        //选择这套参数的电机
        m_Drive_1.getConfigurator().apply(motorConfigs);
        m_Drive_2.getConfigurator().apply(motorConfigs);
        m_Drive_3.getConfigurator().apply(motorConfigs);
        m_Drive_4.getConfigurator().apply(motorConfigs);
    }
  

      //电机停止Command
  public Command Motor_RunasFront (){
    return runOnce(()->{
                      setmotorVelocity(10);
                      setmotorPosition(0);
                  });
  }


  /**
   * Example command factory method.
   *
   * @return a command
   */


  /**
   * An example method querying a boolean state of the subsystem (for example, a digital sensor).
   *
   * @return value of some boolean subsystem state, such as a digital sensor.
   */
  public boolean exampleCondition() {
    // Query some boolean state, such as a digital sensor.
    return false;
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  @Override
  public void simulationPeriodic() {
    // This method will be called once per scheduler run during simulation
  }
}
