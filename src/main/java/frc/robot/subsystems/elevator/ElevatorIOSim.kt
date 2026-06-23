package frc.robot.subsystems.elevator

import edu.wpi.first.math.system.plant.DCMotor
import edu.wpi.first.wpilibj.simulation.ElevatorSim
import frc.robot.Constants
import frc.robot.Constants.ElevatorConstants
import org.littletonrobotics.junction.Logger

class ElevatorIOSim : ElevatorIOHardware() {
    val elevatorSim: ElevatorSim =
        ElevatorSim(
            DCMotor.getKrakenX60(2),
            1 / ElevatorConstants.GEAR_RATIO,
            ElevatorConstants.CARRIAGE_MASS,
            ElevatorConstants.DRUM_RADIUS,
            ElevatorConstants.MIN_ELEVATOR_HEIGHT,
            ElevatorConstants.MAX_ELEVATOR_HEIGHT,
            true,
            0.0,
        )

    private val leftSimState = left.simState
    private val rightSimState = right.simState

    override fun updateInputs(inputs: ElevatorIO.ElevatorIOInputs) {
        super.updateInputs(inputs)

        leftSimState.setSupplyVoltage(12.0)
        rightSimState.setSupplyVoltage(12.0)
        elevatorSim.setInput(leftSimState.motorVoltage)
        elevatorSim.update(Constants.LOOP_TIME)
        val motorPos = elevatorSim.positionMeters * ElevatorConstants.DRUM_RADIUS * 2 * Math.PI
        inputs.elevatorPos = elevatorSim.positionMeters
        leftSimState.setRawRotorPosition(motorPos)
        rightSimState.setRawRotorPosition(motorPos)
        val motorVelocity = elevatorSim.velocityMetersPerSecond * ElevatorConstants.DRUM_RADIUS * 2 * Math.PI
        leftSimState.setRotorVelocity(motorVelocity)
        rightSimState.setRotorVelocity(motorVelocity)
        Logger.recordOutput("Elevator/HasHitUpperLimit", elevatorSim.hasHitUpperLimit())
        Logger.recordOutput("Elevator/HasHitLowerLimit", elevatorSim.hasHitLowerLimit())
    }
}
