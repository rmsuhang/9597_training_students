package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import frc.robot.subsystems.CANdleSystem;
import frc.robot.subsystems.drive1;

public class drive_Commend{
    private final CANdleSystem m_candle_subsystenm = new CANdleSystem();
    private final drive1 m_drive1_subsystenm = new drive1();
    public Command Motor_Position_command(double  Position,double  Position2){
        return Commands.run(()->{
            m_drive1_subsystenm.setmotorPosition(Position,Position2); // Set the motor to move at 1000 units per second
            if (IsAtPosition == true){

            }
        })
        .until(() -> m_drive1_subsystenm.IsAtPosition(Position))
        .andThen(()->{
            m_drive1_subsystenm.setmotorPosition(m_drive1_subsystenm.getMotorPosition(),m_drive1_subsystenm.getMotor2Position());
            m_candle_subsystenm.setFireWithMotor();
        })
        .finallyDo(() -> {
            m_candle_subsystenm.setOff();
        });
      }
}

