package frc.robot.commands;

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
import com.fasterxml.jackson.databind.RuntimeJsonMappingException;
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
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.Task1;

import frc.robot.Constants;
import frc.robot.subsystems.drive;
import frc.robot.subsystems.CANdleSystem;

@SuppressWarnings("unused")

public class task3 {
    public static Command moveforward(drive drivesyt){
        return Commands.runOnce(()->{
            drivesyt.setallposition(0, 0, 0, 0);
            drivesyt.setallvelocity(10, 10, 10, 10);
        });
    }
    public static Command stop(drive drivesyt){
        return Commands.runOnce(() -> {
            drivesyt.setallvelocity(0,0,0,0);
            drivesyt.setallposition(drivesyt.test_motor1.getPosition().getValueAsDouble(),drivesyt.test_motor3.getPosition().getValueAsDouble(),drivesyt.test_motor5.getPosition().getValueAsDouble(),drivesyt.test_motor7.getPosition().getValueAsDouble());
        });
        
    }
}
