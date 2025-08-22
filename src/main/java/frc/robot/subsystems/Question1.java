
package frc.robot.subsystems;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.controls.VelocityTorqueCurrentFOC;
//Voltage Out 不受PID影响
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.GravityTypeValue;
import com.ctre.phoenix6.signals.StaticFeedforwardSignValue;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Question1 extends SubsystemBase {

    private final TalonFX m_test_motor = new TalonFX(Constants.MOTORinQ1.MOTOR_1_ID, "rio");
    private final TalonFX m_test_motor2 = new TalonFX(Constants.MOTORinQ1.MOTOR_2_ID, "rio");

    private final CANdleSystem m_CANdleSystem = new CANdleSystem();

    private final VelocityTorqueCurrentFOC m_test_motor_request1 = new VelocityTorqueCurrentFOC(0);
    private final VelocityTorqueCurrentFOC m_test_motor_request2 = new VelocityTorqueCurrentFOC(0);

    public void setmotorVelocity(double Velocity) {
        m_test_motor.setControl(m_test_motor_request1.withVelocity(Velocity));
        m_test_motor2.setControl(m_test_motor_request2.withVelocity(Velocity));
      }


    public Question1() {
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
        // //高级控制才用到下面的参数
        // motorConfigs1.MotionMagic.MotionMagicAcceleration = 100; // Acceleration is around 40 rps/s
        // motorConfigs1.MotionMagic.MotionMagicCruiseVelocity = 200; // Unlimited cruise velocity
        // motorConfigs1.MotionMagic.MotionMagicExpo_kV = 0.12; // kV is around 0.12 V/rps
        // motorConfigs1.MotionMagic.MotionMagicExpo_kA = 0.1; // Use a slower kA of 0.1 V/(rps/s)
        // motorConfigs1.MotionMagic.MotionMagicJerk = 0; // Jerk is around 0'     
        
        //选择这套参数的电机
        m_test_motor.getConfigurator().apply(motorConfigs1);
        m_test_motor2.getConfigurator().apply(motorConfigs1);
    }
      
      public Command Motor_Stop (double Velocity){
        return runOnce(()->{
                          setmotorVelocity(Velocity);
                      });
      }
    

      public static int i = 1;
      public Command motor_switch(){
        return runOnce(()->{
          i*=-1;
          motor_move();
        });
    
      }

      public void motor_move(){
            m_test_motor.setControl(m_test_motor_request1.withVelocity(5*i));
            m_test_motor2.setControl(m_test_motor_request2.withVelocity(10*i));
            if(i==1){
              m_CANdleSystem.MattisGay();
            }else{
              m_CANdleSystem.ZikiisGay();
            }
      }

    // public Command motor_move(double velocity, double velocity2,double velocity3, double velocity4){
    //   return run(() -> {
    //     if(i == 1){
    //       setmotorVelocity(velocity);
    //       setmotorVelocity2(velocity2);
    //       m_CANdleSystem.setFire();
    //     }else if(i == -1){
    //       setmotorVelocity(velocity3);
    //       setmotorVelocity2(velocity4);
    //       m_CANdleSystem.setOff();
    //     }
    //   });
    // }
    
    

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



//标志位取反的方法

//状态的集合： emun{
//             }

//两个状态之间的来回切换，现实世界是 分  真   和   假
//true 和 False
//！



//复杂问题拆分成简单问题

//把一个子系统引入到另一个子系统：两个本来完全独立的部分混到了一起，可读性非常差，逻辑混乱
//非常不规范的写法：
//在一个子系统里引入另一个子系统


//如果说涉及到了多个子系统交互的命令，携程复杂的Command放到Command文件夹下面
//RobotContainer里面完成一些简单交互

//该交互的地方交互，该独立的地方独立
