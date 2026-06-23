package frc.robot.subsystems.elevator

import edu.wpi.first.wpilibj.Alert
import edu.wpi.first.wpilibj2.command.Command
import edu.wpi.first.wpilibj2.command.Commands
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup
import edu.wpi.first.wpilibj2.command.SubsystemBase
import edu.wpi.first.wpilibj2.command.WaitCommand
import frc.robot.Constants.ElevatorConstants
import org.littletonrobotics.junction.Logger


class ElevatorSubsystem(
    private val io: ElevatorIO,
) : SubsystemBase() {
    private val inputs: ElevatorIOInputsAutoLogged = ElevatorIOInputsAutoLogged()

    var elevatorTargetPosition: Double = 0.0
        private set
    val elevatorPos: Double
        get() = inputs.elevatorPos

    private val leftDisconnectAlert =
        Alert("LEFT ELEVATOR MOTOR DISCONNECTED ID(${ElevatorConstants.LEFT_ID}).", Alert.AlertType.kError)
    private val rightDisconnectAlert =
        Alert("RIGHT ELEVATOR MOTOR DISCONNECTED ID(${ElevatorConstants.RIGHT_ID}).", Alert.AlertType.kError)

    override fun periodic() {
        io.updateInputs(inputs)
        Logger.processInputs("Elevator", inputs)

        leftDisconnectAlert.set(!inputs.leftConnected)
        rightDisconnectAlert.set(!inputs.rightConnected)

        Logger.recordOutput("Elevator/ActiveCommand", currentCommand?.name ?: "None")
        Logger.recordOutput("Elevator/elevatorTargetPosition", elevatorTargetPosition)

        Commands.runOnce( { moveElevator(1.00) } )
    }

    // MOVES ELEVATOR BY X METERS ABOVE HARDSTOP
    fun moveElevator(x: Double): Command =
        runOnce {
            elevatorTargetPosition = x
            io.setPosition(x)
        }

    // MOVES ELEVATOR BY X METERS ABOVE HARDSTOP THEN WAIT 1 SEC THEN MOVE Y METERS ABOVE HARDSTOP
    fun moveElevatorTwice(
        x: Double,
        y: Double,
    ): Command =
        SequentialCommandGroup(
            moveElevator(x),
            WaitCommand(2.0),
            moveElevator(y)
        )

    // RETURNS THE ELEVATOR TO THE BOTTOM
    fun returnElevatorBottom(): Command = moveElevator(0.0)

}
