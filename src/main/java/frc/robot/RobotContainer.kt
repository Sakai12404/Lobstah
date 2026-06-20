package frc.robot

import edu.wpi.first.wpilibj2.command.button.CommandPS4Controller
import frc.robot.subsystems.elevator.ElevatorIOSim
import frc.robot.subsystems.elevator.ElevatorSubsystem
import frc.team449.generated.TunerConstants
import frc.team449.subsystems.drive.DriveIOSim
import frc.team449.subsystems.drive.DriveSubsystem

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
    val bindings: Binding =
        Binding(this)
}
