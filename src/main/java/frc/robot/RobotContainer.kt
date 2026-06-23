package frc.robot

import edu.wpi.first.wpilibj2.command.button.CommandPS4Controller
import frc.robot.Constants.Mode
import frc.robot.subsystems.elevator.ElevatorIO
import frc.robot.subsystems.elevator.ElevatorIOHardware
import frc.robot.subsystems.elevator.ElevatorIOSim
import frc.robot.subsystems.elevator.ElevatorSubsystem
import frc.team449.generated.TunerConstants
import frc.team449.subsystems.drive.DriveIO
import frc.team449.subsystems.drive.DriveIOHardware
import frc.team449.subsystems.drive.DriveIOSim
import frc.team449.subsystems.drive.DriveSubsystem

object RobotContainer {
    val driveController = CommandPS4Controller(Constants.OperatorConstants.DRIVER_CONTROLLER_PORT) // I have a ps4 controller

    // My Subsystems only deal with sim
    val drive: DriveSubsystem =
        DriveSubsystem(
            when (Constants.CURRENT_MODE) {
                Mode.REAL -> {
                    DriveIOHardware(
                        TunerConstants.DrivetrainConstants,
                        arrayOf(
                            TunerConstants.FrontLeft,
                            TunerConstants.FrontRight,
                            TunerConstants.BackLeft,
                            TunerConstants.BackRight,
                        ),
                    )
                }

                Mode.SIM -> {
                    DriveIOSim(
                        TunerConstants.DrivetrainConstants,
                        arrayOf(
                            TunerConstants.FrontLeft,
                            TunerConstants.FrontRight,
                            TunerConstants.BackLeft,
                            TunerConstants.BackRight,
                        ),
                    )
                }

                Mode.REPLAY -> {
                    object : DriveIO {}
                }
            },
        )
    val elevator: ElevatorSubsystem =
        ElevatorSubsystem(
            when (Constants.CURRENT_MODE) {
                Mode.REAL -> {
                    ElevatorIOHardware()
                }

                Mode.SIM -> {
                    ElevatorIOSim()
                }

                Mode.REPLAY -> {
                    object : ElevatorIO {}
                }
            },
        )
    val bindings: Binding =
        Binding(this)
}
