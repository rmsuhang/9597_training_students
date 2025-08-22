
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

import com.ctre.phoenix.led.Animation;
import com.ctre.phoenix.led.CANdle;
import com.ctre.phoenix.led.CANdle.LEDStripType;
import com.ctre.phoenix.led.CANdle.VBatOutputMode;
import com.ctre.phoenix.led.CANdleConfiguration;
import com.ctre.phoenix.led.ColorFlowAnimation;
import com.ctre.phoenix.led.ColorFlowAnimation.Direction;
import com.ctre.phoenix.led.FireAnimation;
import com.ctre.phoenix.led.LarsonAnimation;
import com.ctre.phoenix.led.LarsonAnimation.BounceMode;
import com.ctre.phoenix.led.RainbowAnimation;
import com.ctre.phoenix.led.RgbFadeAnimation;
import com.ctre.phoenix.led.SingleFadeAnimation;
import com.ctre.phoenix.led.StrobeAnimation;
import com.ctre.phoenix.led.TwinkleAnimation;
import com.ctre.phoenix.led.TwinkleAnimation.TwinklePercent;
import com.ctre.phoenix.led.TwinkleOffAnimation;
import com.ctre.phoenix.led.TwinkleOffAnimation.TwinkleOffPercent;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import frc.robot.Constants;
@SuppressWarnings("unused")

public class task1 extends SubsystemBase {
  private final CANdle m_candle = new CANdle(Constants.Task1.CANdleID, "rio");
  private final int LedCount = 300;

  private final TalonFX test_motor1 = new TalonFX(Constants.Task1.motor1ID, "rio");
  private final TalonFX test_motor2 = new TalonFX(Constants.Task1.motor2ID, "rio");

  private final VelocityTorqueCurrentFOC velocity_request1 = new VelocityTorqueCurrentFOC(0.0);
  private final VelocityTorqueCurrentFOC velocity_request2 = new VelocityTorqueCurrentFOC(0.0); 
   
  private double currentv1 = 0.0;
  private double currentv2 = 0.0;

  public enum AnimationTypes {
        Fire,
        SetAll
    }
  private AnimationTypes expectedAnimate = AnimationTypes.SetAll;
  private Animation animationconfigs = null;

  public task1(){
    var motorConfigs = new TalonFXConfiguration();
    motorConfigs.Slot0.kS = 1.6;
    motorConfigs.Slot0.kV = 0.17;
    motorConfigs.Slot0.kA = 0;
    motorConfigs.Slot0.kP = 7;
    motorConfigs.Slot0.kI = 0;
    motorConfigs.Slot0.kD = 0;
    
    motorConfigs.Slot0.GravityType = GravityTypeValue.Arm_Cosine;
    motorConfigs.Slot0.StaticFeedforwardSign = StaticFeedforwardSignValue.UseVelocitySign;

    test_motor1.getConfigurator().apply(motorConfigs);
    test_motor2.getConfigurator().apply(motorConfigs);

    CANdleConfiguration candleconfig = new CANdleConfiguration();
    candleconfig.statusLedOffWhenActive = true;
    candleconfig.disableWhenLOS = false;
    candleconfig.stripType = LEDStripType.GRB;
    candleconfig.brightnessScalar = 0.1;
    candleconfig.vBatOutputMode = VBatOutputMode.Modulated;
    m_candle.configAllSettings(candleconfig, 100);
  }
  public void setvelocity(Double velocity1, double celocity2){
    test_motor1.setControl(velocity_request1.withVelocity(velocity1));
    test_motor2.setControl(velocity_request2.withVelocity(celocity2));
  }
  public void changev(){
    if(currentv1 == 0 && currentv2 == 0){
        currentv1 = Constants.Task1.motor1v;
        currentv2 = Constants.Task1.motor2v;
    }else{
        currentv1 *= -1;
        currentv2 *= -1;
    }
  }
  public void changeAnimation(){
    if(expectedAnimate == AnimationTypes.SetAll){
      expectedAnimate = AnimationTypes.Fire;
      animationconfigs = new FireAnimation(0.5, 0.7, LedCount, 0.7, 0.5);
    }else{
        expectedAnimate = AnimationTypes.SetAll;
        animationconfigs = null;
    }
  }
  public Command changeCommand(){
    return runOnce(()->{
        changev();
        changeAnimation();
    });
  }
  public Command to0(){
    return runOnce(()->{
        currentv1 = 0.0;
        currentv2 = 0.0;
        animationconfigs = null;
    });
  }
    @Override
    public void periodic() {
        m_candle.animate(animationconfigs);
        setvelocity(currentv1,currentv2);
    }
}
