// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.turret;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.TurretConstants;


import com.revrobotics.REVLibError;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.spark.ClosedLoopSlot;
import com.revrobotics.spark.SparkBase.ControlType;
import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkBase.ResetMode;
import com.revrobotics.spark.SparkClosedLoopController;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.SparkMax; 
import com.revrobotics.spark.config.ClosedLoopConfig.FeedbackSensor;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;
import com.revrobotics.spark.config.SparkMaxConfig;
import edu.wpi.first.wpilibj.Encoder;

@SuppressWarnings("unused")
public class TurretSubsystem extends SubsystemBase {
  
  private final SparkMax panMotor = new SparkMax(TurretConstants.PAN_MOTOR, MotorType.kBrushless);
  private final RelativeEncoder panMotorInternalEncoder = panMotor.getEncoder(); 
  private final Encoder throughboreEncoder = new Encoder(TurretConstants.PAN_THROUGHBORE_ENCODER_CHANNELA, TurretConstants.PAN_THROUGHBORE_ENCODER_CHANNELB); 


  /** Creates a new TurretSubsystem. */
  public TurretSubsystem() {}

  public void sanityCheckInternalEncoder(){ 
    final int throughborePosition = throughboreEncoder.get(); 
    panMotorInternalEncoder.setPosition(throughborePosition);
    System.out.println("Turret Encoder sanity checked! Sane?: " + throughborePosition);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
