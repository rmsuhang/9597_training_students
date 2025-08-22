// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import frc.robot.Constants.Candle;
import frc.robot.Constants.OperatorConstants;
import frc.robot.subsystems.CANdleSystem;
import frc.robot.subsystems.drive;
import frc.robot.subsystems.task1;
import frc.robot.commands.task2command;
import frc.robot.commands.task3;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and trigger mappings) should be declared here.
 */

public class RobotContainer {

  drive test_drive = new drive();
  CANdleSystem test_candle = new CANdleSystem();

  //task1 test_task1 = new task1();

  private final CommandXboxController m_driverController = new CommandXboxController(OperatorConstants.kDriverControllerPort);

  public RobotContainer() {
    configureBindings();
  }

  private void configureBindings() {
    m_driverController.a().onTrue(test_drive.changeCommand().andThen(test_candle.change()));
    m_driverController.b().onTrue(test_drive.setvelocityCommand(0,0));
    m_driverController.x().onTrue(task2command.changeCommand(test_drive,test_candle));
    m_driverController.y().onTrue(task2command.setto0(test_drive,test_candle));
    m_driverController.leftBumper().onTrue(task3.moveforward(test_drive));
    m_driverController.rightBumper().onTrue(task3.stop(test_drive));
    
  }
}