package frc.robot

import edu.wpi.first.math.geometry.Pose2d
import edu.wpi.first.math.geometry.Rotation2d
import edu.wpi.first.math.geometry.Translation2d
import edu.wpi.first.wpilibj2.command.button.CommandPS4Controller
import edu.wpi.first.wpilibj2.command.button.CommandXboxController
import frc.robot.subsystems.elevator.ElevatorIOSim
import frc.robot.subsystems.elevator.ElevatorSubsystem
import frc.team449.generated.TunerConstants
import frc.team449.subsystems.drive.DriveIOSim
import frc.team449.subsystems.drive.DriveSubsystem

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the [Robot]
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and trigger mappings) should be declared here.
 *
 * In Kotlin, it is recommended that all your Subsystems are Kotlin objects. As such, there
 * can only ever be a single instance. This eliminates the need to create reference variables
 * to the various subsystems in this container to pass into to commands. The commands can just
 * directly reference the (single instance of the) object.
 */
object RobotContainer {
    val driveController = CommandPS4Controller(Constants.OperatorConstants.DRIVER_CONTROLLER_PORT) // I have a ps4 controller

    // My Subsystems only deal with sim
    val drive: DriveSubsystem =
        DriveSubsystem(
            DriveIOSim(
                TunerConstants.DrivetrainConstants,
                arrayOf(
                    TunerConstants.FrontLeft,
                    TunerConstants.FrontRight,
                    TunerConstants.BackLeft,
                    TunerConstants.BackRight,
                ),
            ),
        )
    val elevator: ElevatorSubsystem =
        ElevatorSubsystem(
            ElevatorIOSim(),
        )
    /*init
    {
        configureBindings()
        // Reference the Autos object so that it is initialized, placing the chooser on the dashboard
        Autos
    }

    /**
     * Use this method to define your `trigger->command` mappings. Triggers can be created via the
     * [Trigger] constructor that takes a [BooleanSupplier][java.util.function.BooleanSupplier]
     * with an arbitrary predicate, or via the named factories in [GenericHID][edu.wpi.first.wpilibj2.command.button.CommandGenericHID]
     * subclasses such for [Xbox][CommandXboxController]/[PS4][edu.wpi.first.wpilibj2.command.button.CommandPS4Controller]
     * controllers or [Flight joysticks][edu.wpi.first.wpilibj2.command.button.CommandJoystick].
     */
    private fun configureBindings()
    {

    }*/
}
