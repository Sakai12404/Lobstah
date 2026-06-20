package frc.robot

import edu.wpi.first.hal.FRCNetComm.tInstances
import edu.wpi.first.hal.FRCNetComm.tResourceType
import edu.wpi.first.hal.HAL
import edu.wpi.first.math.geometry.Pose3d
import edu.wpi.first.math.geometry.Rotation3d
import edu.wpi.first.math.util.Units
import edu.wpi.first.wpilibj.util.WPILibVersion
import edu.wpi.first.wpilibj2.command.CommandScheduler
import org.littletonrobotics.junction.LoggedRobot
import org.littletonrobotics.junction.Logger
import org.littletonrobotics.junction.networktables.NT4Publisher
import kotlin.math.cos
import kotlin.math.min
import kotlin.math.sin

class Robot : LoggedRobot() {
    private val robotContainer = RobotContainer

    init {
        Logger.addDataReceiver(NT4Publisher())
        HAL.report(tResourceType.kResourceType_Language, tInstances.kLanguage_Kotlin, 0, WPILibVersion.Version)
        Logger.start()
    }

    override fun robotPeriodic() {
        CommandScheduler.getInstance().run()
        logComponents()
    }

    override fun robotInit() {
        robotContainer.bindings.setDefaultCommands()
        robotContainer.bindings.bindControls()
    }

    private fun logComponents() {
        // ts ragebaaait
        val pivotAngle = Units.degreesToRadians(51.314206)
        val elevatorPosition = robotContainer.elevator.elevatorTargetPosition
        val sinPivot = sin(pivotAngle)
        val cosPivot = cos(pivotAngle)
        Logger.recordOutput(
            "FinalComponentsPoses",
            // Base pivot point
            Pose3d(
                0.2,
                0.0,
                0.245,
                Rotation3d(
                    0.0,
                    0.0,
                    0.0,
                ),
            ),
            // First stage limit: 0.6
            Pose3d(
                0.303 - min(0.6 * cosPivot, elevatorPosition * cosPivot),
                0.0, // 0.0000,
                0.245 + min(0.6 * sinPivot, elevatorPosition * sinPivot),
                Rotation3d(),
            ),
            // second stage limit: 0.575 (1.175)
            Pose3d(
                0.303 - min(1.175 * cosPivot, elevatorPosition * cosPivot),
                0.0, // 0.0000,
                0.245 + min(1.175 * sinPivot, elevatorPosition * sinPivot),
                Rotation3d(),
            ),
            // third stage limit: 0.56 (1.735)
            Pose3d(
                0.303 - min(1.735 * cosPivot, elevatorPosition * cosPivot),
                0.0,
                0.245 + min(1.735 * sinPivot, elevatorPosition * sinPivot),
                Rotation3d(),
            ),
            // manip + 0.6737 (2.4087)
            Pose3d(
                0.303 - min(1.735 * cosPivot, elevatorPosition * cosPivot) - 0.6737 * cosPivot,
                0.0,
                0.245 + min(1.735 * sinPivot, elevatorPosition * sinPivot) + 0.6737 * sinPivot,
                Rotation3d(),
            ),
        )
    }
}
