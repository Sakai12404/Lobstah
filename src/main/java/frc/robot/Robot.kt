package frc.robot

import edu.wpi.first.hal.FRCNetComm.tInstances
import edu.wpi.first.hal.FRCNetComm.tResourceType
import edu.wpi.first.hal.HAL
import edu.wpi.first.math.geometry.Pose2d
import edu.wpi.first.math.geometry.Pose3d
import edu.wpi.first.math.geometry.Rotation3d
import edu.wpi.first.math.geometry.Translation3d
import edu.wpi.first.math.util.Units
import edu.wpi.first.wpilibj.TimedRobot
import edu.wpi.first.wpilibj.Timer
import edu.wpi.first.wpilibj.util.WPILibVersion
import edu.wpi.first.wpilibj2.command.Command
import edu.wpi.first.wpilibj2.command.CommandScheduler
import edu.wpi.first.wpilibj2.command.Commands.runOnce
import frc.robot.commands.Autos
import org.littletonrobotics.junction.LoggedRobot
import org.littletonrobotics.junction.Logger
import org.littletonrobotics.junction.networktables.NT4Publisher
import kotlin.math.acos
import kotlin.math.asin
import kotlin.math.cos
import kotlin.math.sin

/**
 * The functions in this object (which basically functions as a singleton class) are called automatically
 * corresponding to each mode, as described in the TimedRobot documentation.
 * This is written as an object rather than a class since there should only ever be a single instance, and
 * it cannot take any constructor arguments. This makes it a natural fit to be an object in Kotlin.
 *
 * If you change the name of this object or its package after creating this project, you must also update
 * the `Main.kt` file in the project. (If you use the IDE's Rename or Move refactorings when renaming the
 * object or package, it will get changed everywhere.)
 */
class Robot : LoggedRobot() {
    /**
     * The autonomous command to run. While a default value is set here,
     * the [autonomousInit] method will set it to the value selected in
     *the  AutoChooser on the dashboard.
     */
    private val robotContainer = RobotContainer
    init {
        // Kotlin initializer block, which effectually serves as the constructor code.
        // https://kotlinlang.org/docs/classes.html#constructors
        // This work can also be done in the inherited `robotInit()` method. But as of the 2025 season the
        // `robotInit` method's Javadoc encourages using the constructor and the official templates
        // moved initialization code out `robotInit` and into the constructor. We follow suit in Kotlin.

        // Report the use of the Kotlin Language for "FRC Usage Report" statistics.
        // Please retain this line so that Kotlin's growing use by teams is seen by FRC/WPI.
        Logger.addDataReceiver(NT4Publisher())
        HAL.report(tResourceType.kResourceType_Language, tInstances.kLanguage_Kotlin, 0, WPILibVersion.Version)
        // Access the RobotContainer object so that it is initialized. This will perform all our
        // button bindings, and put our autonomous chooser on the dashboard.
        RobotContainer
        Logger.start()
    }

    /**
     * This method is called every 20 ms, no matter the mode. Use this for items like
     * diagnostics that you want ran during disabled, autonomous, teleoperated and test.
     * This runs after the mode specific periodic methods, but before LiveWindow and
     * SmartDashboard integrated updating.
     */
    override fun robotPeriodic() {
        // Runs the Scheduler.  This is responsible for polling buttons, adding newly-scheduled
        // commands, running already-scheduled commands, removing finished or interrupted commands,
        // and running subsystem periodic() methods.  This must be called from the robot's periodic
        // block in order for anything in the Command-based framework to work.
        CommandScheduler.getInstance().run()
        logComponents()
    }

    override fun robotInit() {
        robotContainer.bindings.setDefaultCommands()
        robotContainer.bindings.bindControls()

    }

    /** This method is called once each time the robot enters Disabled mode.  */
    override fun disabledInit() {
    }

    override fun disabledPeriodic() {
    }

    /** This autonomous runs the autonomous command selected by your [RobotContainer] class.  */
    override fun autonomousInit() {

    }

    /** This method is called periodically during autonomous.  */
    override fun autonomousPeriodic() {
    }

    override fun teleopInit() {

    }

    /** This method is called periodically during operator control.  */
    override fun teleopPeriodic() {
    }

    override fun testInit() {
        // Cancels all running commands at the start of test mode.
        CommandScheduler.getInstance().cancelAll()
    }

    /** This method is called periodically during test mode.  */
    override fun testPeriodic() {
    }

    /** This method is called once when the robot is first started up.  */
    override fun simulationInit() {
    }

    /** This method is called periodically whilst in simulation.  */
    override fun simulationPeriodic() {
    }

    private fun logComponents() {
        // ts ragebaaait
        val elevatorAngle = 0.0
        Logger.recordOutput(
            "FinalComponentsPoses",
            Pose3d(
                0.3,
                0.0,
                0.25,
                Rotation3d(
                    0.0,
                    elevatorAngle,
                    0.0,
                ),
            ),
            Pose3d(
                -0.06 - 0.545 * acos(elevatorAngle),
                0.0000,
                0.699047 - 0.545 * asin(elevatorAngle),
                Rotation3d(
                    0.0,
                    elevatorAngle,
                    0.0
                )
            ),
            Pose3d(
                -0.355,
                0.0000,
                1.0615186,
                Rotation3d(
                    0.0,
                    elevatorAngle,
                    0.0
                )
            ),
            Pose3d(
                -0.648313,
                0.0,
                1.436871,
                Rotation3d(
                    0.0,
                    elevatorAngle,
                    0.0
                )
            ),
            Pose3d(
                -1.041756,
                0.0,
                1.987108,
                Rotation3d(
                    0.0,
                    elevatorAngle,
                    0.0
                )
            ),
        )
    }
}
