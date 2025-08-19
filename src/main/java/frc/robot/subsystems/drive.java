// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.controls.VoltageOut;
import com.ctre.phoenix6.hardware.TalonFX;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;



// 1. 比例参数（\(K_p\)）：快速响应偏差物理意义：根据偏差的大小直接产生控制作用，偏差越大，控制作用越强。
//作用：比例项是控制系统的 “即时响应” 部分，能快速抵消偏差，提高系统的响应速度。
//特点：优点：响应快，能快速缩小偏差；
//缺点：单独使用时，系统可能存在稳态误差（即偏差无法完全消除，例如控制水温时，设定 50℃，实际稳定在 48℃）；
//若\(K_p\)过大，会导致系统震荡（输出在设定值附近大幅波动）。
//2. 积分参数（\(T_i\)，或积分系数\(K_i = K_p/T_i\)）：消除稳态误差物理意义：
//根据偏差的累积量（积分）产生控制作用，偏差持续时间越长，累积的控制作用越强。
//作用：积分项是控制系统的 “纠偏补偿” 部分，用于消除比例控制留下的稳态误差 —— 只要存在偏差，积分就会持续累积，直到偏差为零
//（此时积分停止累积）。公式中的积分项：\(K_p \cdot \frac{1}{T_i} \int_0^t e(\tau)d\tau = K_i \int_0^t e(\tau)d\tau\)（\(K_i\)为积分系数）。
//特点：优点：彻底消除稳态误差；缺点：积分作用有 “滞后性”（需要时间累积），
//若\(T_i\)过小（\(K_i\)过大），会导致系统超调量增大、震荡加剧（例如水温超过 50℃后大幅波动）。
//3. 微分参数（\(T_d\)，或微分系数\(K_d = K_p \cdot T_d\)）：抑制震荡，预判趋势物理意义：根据偏差的变化率（斜率）产生控制作用，
//偏差变化越快，控制作用越强，提前 “预判” 偏差的趋势并抑制。作用：微分项是控制系统的 “阻尼与预判” 部分，
//通过偏差的变化速度提前调整控制量，抑制系统因比例或积分作用导致的震荡，提高稳定性。公式中的微分项：
//特点：优点：减少超调量，抑制震荡，加快系统稳定；缺点：对噪声敏感（偏差的微小波动会被放大），
//若\(T_d\)过大（\(K_d\)过大），会导致系统响应迟缓，甚至引入新的震荡。
//基类：基础的类










//colne完之后：远程仓库的东西 = 本地仓库的东西

//本地 落后于 远程

//推送代码的时候，需要注意一个点:保证本地一定    不落后    于远程

//远程仓库有一个我本地完全没有    出现过     的更改


//远程把字母a改成b了，但是本地没有同步  a  →  b
//然后在本地，你把a改成c了   a  →  c
//推送  ❌  提示你本地落后于远程
//先再同步远程仓库的代码
//
//随心所欲的提交   提交不上去    要么不该提交的提交了， 你要去回退代码版本 ，比较麻烦


//远程把字母a改成b了，但是本地同步了  a  →  b
//然后我又把字母改成c     a  →  b    →  c
//本地不落后于远程
//推送      成功








//第一天开发，一开始大家的代码是一样的 ，远程已经有创建好的仓库，和本地已经建立联系了

//然后，你负责任务a的开发，队友负责任务b的开发

//第一天结束了，假设你们都开发了一半 ，你们怎么操作

//你们的功能都没经过完整的测试，能不能推送到main？

//假设我推送到main     

//你的提交被覆盖了























public class drive extends SubsystemBase {

  //声明电机
  private final TalonFX m_test_motor = new TalonFX(2, "rio");
  //特性：请求制，需要一个request
  private final VoltageOut m_test_motor_request = new VoltageOut(0.0);

  //实际控制
  //封装出来的方法
  //控制电机 1.控制电机的位置
  //        2.控制电机的速度
  //高级的控制方法   本质就是优化速度控制和位置控制

  //withPosition()能够将高级的控制请求和底层的位置控制建立联系
  //withVelocity()能够将高级的控制请求和底层的速度控制建立联系

  public void setmotorVoltage(double vol) {
    m_test_motor.setControl(m_test_motor_request.withOutput(vol));
  }

  //控制电压的command
  public Command Motor_Voltage_command(double voltage) {
    return run(()->{
      setmotorVoltage(voltage); // Set the motor to move at 1000 units per second
    });
  }

  //构造函数：初始化子系统，读取电机的一些固定参数
  public drive() {

      var motorConfigs = new TalonFXConfiguration();

      motorConfigs.Slot0.kS = 0.2;
      motorConfigs.Slot0.kV = 0.0;
      motorConfigs.Slot0.kA = 0;
      motorConfigs.Slot0.kP = 3;
      motorConfigs.Slot0.kI = 0;
      motorConfigs.Slot0.kD = 0;
      motorConfigs.MotionMagic.MotionMagicAcceleration = 100; // Acceleration is around 40 rps/s
      motorConfigs.MotionMagic.MotionMagicCruiseVelocity = 200; // Unlimited cruise velocity
      motorConfigs.MotionMagic.MotionMagicExpo_kV = 0.12; // kV is around 0.12 V/rps
      motorConfigs.MotionMagic.MotionMagicExpo_kA = 0.1; // Use a slower kA of 0.1 V/(rps/s)
      motorConfigs.MotionMagic.MotionMagicJerk = 0; // Jerk is around 0

      m_test_motor.getConfigurator().apply(motorConfigs);
  }

}


//拆分，把复杂的问题简单化

//subsystem：
//控制机器人

//把所有部分的控制全部写在一起，全部写在一一起没法调试某一部分的功能

//假设我的底盘是正常的，底盘的控制就不需要改，只需要改其他的部分

//什么叫做子系统：主系统（整个机器人）→ 子系统（机器人的某个部分）

//底盘，抬升机构，发射机构

//子系统1：底盘
//子系统2：抬升机构
//子系统3：发射机构

//把小的部分联合起来，就能实现整个机器人的控制    


//command：
//命令，指令：告诉机器人执行什么动作
//能够把我们的代码转换成机器人的实际动作

//封装好了一些自己的方法
//command里面调用我们自己写好的方法，实现机器人的控制

//简单的command的写法完全类似于一个方法的封装

//复杂的command：多个简单的command组合起来

//frc编程不同其他编程的一些特点
//1.其他情况下的编程，for while
//但是在frc里面，没有显式的for 和 while，每一次运行的时间不一定完全一样，
//受限于电脑当时的情况或者配置
//periodic():每隔20ms轮询一次
//periodic()：检查仪一下的视觉检测结果，那我就可以在periodic里面写一个for或者while循环
//来遍历我的检测结果

//每时每刻都在运行，能不能运行一次就结束 ？ ❌
//保持机器人时刻都在运动，所以periodic()


