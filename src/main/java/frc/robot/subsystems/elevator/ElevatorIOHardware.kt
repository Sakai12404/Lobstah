package frc.robot.subsystems.elevator

import com.ctre.phoenix6.BaseStatusSignal
import com.ctre.phoenix6.hardware.TalonFX
import edu.wpi.first.units.Units
import frc.team449.Constants.ElevatorConstants

class ElevatorIOHardware {
    val left = TalonFX(ElevatorConstants.LEFT_ID)
    val right = TalonFX(ElevatorConstants.RIGHT_ID)

    private val leftVoltage = left.motorVoltage
    private val leftVelocity = left.velocity
    private val leftSupplyCurrent = left.supplyCurrent
    private val leftStatorCurrent = left.statorCurrent
    private val leftTemp = left.deviceTemp

    private val rightVoltage = right.motorVoltage
    private val rightVelocity = right.velocity
    private val rightSupplyCurrent = right.supplyCurrent
    private val rightStatorCurrent = right.statorCurrent
    private val rightTemp = right.deviceTemp

    private val elevatorSignals =
        arrayOf(
        leftVoltage,
            leftVelocity,
            leftSupplyCurrent,
            leftStatorCurrent,
            leftTemp,
            rightVoltage,
            rightVelocity,
            rightSupplyCurrent,
            rightStatorCurrent,
            rightTemp,
    )

    private val isLeftConnected: Boolean
        get() = BaseStatusSignal.isAllGood(
            leftVoltage,
            leftVelocity,
            leftStatorCurrent,
        )
    private val isRightConnected: Boolean
        get() = BaseStatusSignal.isAllGood(
            leftVoltage,
            leftVelocity,
            leftStatorCurrent,
        )

    init {
        //tryUntilOk() {left.configurator.apply()}
    }
    override fun updateInputs(inputs: ElevatorIO.ElevatorIOInputs) {
        BaseStatusSignal.refreshAll(*elevatorSignals)

        inputs.leftConnected = isLeftConnected
        inputs.leftAppliedVoltage = leftVoltage.value.'in'(Units.Volts)
        inputs.leftVelocityRadPerSec = leftVelocity.value.'in'(Units.RadiansPerSecond)
        inputs.leftSupplyCurrentAmps = leftSupplyCurrent.value.'in'(Units.Amps)
        inputs.leftStatorCurrentAmps = leftStatorCurrent.value.'in'(Units.Amps)
        inputs.leftTempCelsius = leftTemp.'in'(Units.Celsius)

        inputs.rightConnected = isRightConnected
        inputs.rightAppliedVoltage = rightVoltage.value.'in'(Units.Volts)
        inputs.rightVelocityRadPerSec = rightVelocity.value.'in'(Units.RadiansPerSecond)
        inputs.rightSupplyCurrentAmps = rightSupplyCurrent.value.'in'(Units.Amps)
        inputs.rightStatorCurrentAmps = rightStatorCurrent.value.'in'(Units.Amps)
        inputs.rightTempCelsius = rightTemp.value.'in'(Units.Celsius)
    }
}