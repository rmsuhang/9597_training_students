package frc.robot.commands;

import com.ctre.phoenix.led.CANdle;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import frc.robot.Constants;
import frc.robot.subsystems.CANdleSystem;
import frc.robot.subsystems.Question2;

public class Q2 {
    public static Command Q2_Motor(Question2 Position, CANdleSystem m_CANdleSystem){
        return Commands.run(()->{
            Position.setmotorPosition(Constants.MOTORinQ1.MOTOR_POSITION_1, Constants.MOTORinQ1.MOTOR_POSITION_2); 
            if (Position.isAtPosition(Constants.MOTORinQ1.MOTOR_POSITION_1)){
                System.out.println("motor1 at position");
                                            m_CANdleSystem.FirewithMotor();
                                            }
            
        }).until(()-> Position.isAtPosition(Constants.MOTORinQ1.MOTOR_POSITION_1) && Position.isAtPosition2(Constants.MOTORinQ1.MOTOR_POSITION_2))
        .finallyDo(()->{
            m_CANdleSystem.setOff();
        });
    }
    
    public static Command SettoZero(Question2 Position){
        return Commands.runOnce(()->{
            Position.setmotorPosition(0.0,0.0); 
        });
}
}