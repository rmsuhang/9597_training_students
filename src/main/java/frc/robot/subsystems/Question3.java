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
    private final CANcoder cancoder_bl = new CANcoder(Constants.MOTORinQ1.CANCODER_3_ID,"rio");
    private final CANcoder cancoder_br = new CANcoder(Constants.MOTORinQ1.CANCODER_4_ID,"rio");

    private final VelocityTorqueCurrentFOC m_test_motor_request = new VelocityTorqueCurrentFOC(0.0);
    private final VelocityTorqueCurrentFOC m_test_motor_request2 = new VelocityTorqueCurrentFOC(0.0);

    public void setmotorVelocity(double Velocity) {
      m_Steer_1.setControl(m_test_motor_request.withVelocity(Velocity));
      m_Steer_2.setControl(m_test_motor_request.withVelocity(Velocity));
      m_Steer_3.setControl(m_test_motor_request.withVelocity(Velocity));
      m_Steer_4.setControl(m_test_motor_request.withVelocity(Velocity));
    }

    public void setmotorVelocity2(double Velocity) {
      m_Drive_1.setControl(m_test_motor_request2.withVelocity(Velocity));
      m_Drive_2.setControl(m_test_motor_request2.withVelocity(Velocity));
      m_Drive_3.setControl(m_test_motor_request2.withVelocity(Velocity));
      m_Drive_4.setControl(m_test_motor_request2.withVelocity(Velocity));
    }

  public Question3() {
    
        var motorEncoderConfigs = new CANcoderConfiguration();
        motorEncoderConfigs.MagnetSensor.MagnetOffset=0.0;
        motorEncoderConfigs.MagnetSensor.AbsoluteSensorDiscontinuityPoint=0.5;//实际生活中的电机位置映射到什么范围
        motorEncoderConfigs.MagnetSensor.SensorDirection=SensorDirectionValue.Clockwise_Positive;
        cancoder_fl.getConfigurator().apply(motorEncoderConfigs);
    
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
        motorConfigs.MotionMagic.MotionMagicAcceleration = 100; // Acceleration is around 40 rps/s
        motorConfigs.MotionMagic.MotionMagicCruiseVelocity = 200; // Unlimited cruise velocity
        motorConfigs.MotionMagic.MotionMagicExpo_kV = 0.12; // kV is around 0.12 V/rps
        motorConfigs.MotionMagic.MotionMagicExpo_kA = 0.1; // Use a slower kA of 0.1 V/(rps/s)
        motorConfigs.MotionMagic.MotionMagicJerk = 0; // Jerk is around 0'
        
        //选择这套参数的电机
        m_Steer_1.getConfigurator().apply(motorConfigs);
        m_Steer_2.getConfigurator().apply(motorConfigs);
        m_Steer_3.getConfigurator().apply(motorConfigs);
        m_Steer_4.getConfigurator().apply(motorConfigs);

        m_Drive_1.getConfigurator().apply(motorConfigs);
        m_Drive_2.getConfigurator().apply(motorConfigs);
        m_Drive_3.getConfigurator().apply(motorConfigs);
        m_Drive_4.getConfigurator().apply(motorConfigs);
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
