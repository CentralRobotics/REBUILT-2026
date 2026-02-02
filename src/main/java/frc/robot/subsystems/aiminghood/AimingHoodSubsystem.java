// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.aiminghood;

import static edu.wpi.first.units.Units.Meter;

import com.pathplanner.lib.auto.AutoBuilder;
import com.pathplanner.lib.commands.PathPlannerAuto;
import com.pathplanner.lib.commands.PathfindingCommand;
import com.pathplanner.lib.config.PIDConstants;
import com.pathplanner.lib.config.RobotConfig;
import com.pathplanner.lib.controllers.PPHolonomicDriveController;
import com.pathplanner.lib.path.PathConstraints;
import com.pathplanner.lib.path.PathPlannerPath;
import com.pathplanner.lib.util.DriveFeedforwards;
import com.pathplanner.lib.util.swerve.SwerveSetpoint;
import com.pathplanner.lib.util.swerve.SwerveSetpointGenerator;
import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.kinematics.SwerveDriveKinematics;
import edu.wpi.first.math.trajectory.Trajectory;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj2.command.button.RobotModeTriggers;
import edu.wpi.first.wpilibj2.command.sysid.SysIdRoutine.Config;
import frc.robot.Constants;
import frc.robot.subsystems.swervedrive.Vision;

import java.util.Optional;
import java.util.function.Supplier;

import org.photonvision.targeting.PhotonTrackedTarget;

public class AimingHoodSubsystem extends SubsystemBase
{
    private final Supplier<Pose2d> poseSupplier;

    private static final Translation2d HUB_POSITION = 
        new Translation2d(Constants.AimingHoodConstants.HUB_POS_X, Constants.AimingHoodConstants.HUB_POS_Y);

    public AimingHoodSubsystem(Supplier<Pose2d> poseSupplier){
        this.poseSupplier = poseSupplier;
    }

    public Optional<Rotation2d> getVisionYaw() {
    var resultOpt = Vision.Cameras.CENTER_CAM.getLatestResult();

    if (resultOpt.isEmpty()) return Optional.empty();
    var result = resultOpt.get();

    if (!result.hasTargets()) return Optional.empty();

    PhotonTrackedTarget target = result.getBestTarget();
    return Optional.of(Rotation2d.fromDegrees(target.getYaw()));
}


    public Rotation2d getDesiredAngle() {
        return getVisionYaw().orElseGet(() -> {
            Pose2d robotPose = poseSupplier.get();
            Translation2d toTarget = HUB_POSITION.minus(robotPose.getTranslation());
            return toTarget.getAngle().minus(robotPose.getRotation());
        });
    }
    

    @Override
    public void periodic(){
        Rotation2d desiredAngle = getDesiredAngle();

        setHoodAngle(desiredAngle);
    }

    private void setHoodAngle(Rotation2d angle){

    }
}
