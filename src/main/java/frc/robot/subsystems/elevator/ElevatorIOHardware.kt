package frc.robot.subsystems.elevator

import com.ctre.phoenix6.BaseStatusSignal
import com.ctre.phoenix6.configs.Slot0Configs
import com.ctre.phoenix6.configs.TalonFXConfiguration
import com.ctre.phoenix6.controls.PositionVoltage
import com.ctre.phoenix6.hardware.TalonFX
import frc.robot.util.Constants.ElevatorConstants
import frc.team449.util.PhoenixUtil.tryUntilOk

class ElevatorIOHardware : ElevatorIO {
    // SETTING UP VARIABLES
    val left = TalonFX(ElevatorConstants.LEFT_ID)
    val right = TalonFX(ElevatorConstants.RIGHT_ID)

    private val leftPositionRequest = PositionVoltage(0.0)
    private val rightPositionRequest = PositionVoltage(0.0)

    private val leftVoltage = left.motorVoltage
    private val leftVelocity = left.velocity
    private val leftSupplyCurrent = left.supplyCurrent
    private val leftStatorCurrent = left.statorCurrent
    private val leftTemp = left.deviceTemp
    private val leftPosition = left.position

    private val rightVoltage = right.motorVoltage
    private val rightVelocity = right.velocity
    private val rightSupplyCurrent = right.supplyCurrent
    private val rightStatorCurrent = right.statorCurrent
    private val rightTemp = right.deviceTemp
    private val rightPosition = right.position

    private val elevatorSignals =
        arrayOf(
            leftVoltage,
            leftVelocity,
            leftSupplyCurrent,
            leftStatorCurrent,
            leftTemp,
            leftPosition,
            rightVoltage,
            rightVelocity,
            rightSupplyCurrent,
            rightStatorCurrent,
            rightTemp,
            rightPosition,
        )

    private val isLeftConnected: Boolean
        get() =
            BaseStatusSignal.isAllGood(
                leftVoltage,
                leftVelocity,
                leftStatorCurrent,
            )
    private val isRightConnected: Boolean
        get() =
            BaseStatusSignal.isAllGood(
                leftVoltage,
                leftVelocity,
                leftStatorCurrent,
            )

    init {
        // TRY TO CONFIGURE THE TALONS
        tryUntilOk(5) { left.configurator.apply(leftConfig, 0.25) }
        tryUntilOk(5) { right.configurator.apply(rightConfig, 0.25) }
    }

    // PROVIDE INPUTS TO USE
    override fun updateInputs(inputs: ElevatorIO.ElevatorIOInputs) {
        BaseStatusSignal.refreshAll(*elevatorSignals)

        inputs.leftConnected = isLeftConnected
        inputs.leftAppliedVoltage = leftVoltage.valueAsDouble // .value.'in'(Units.Volts)
        inputs.leftVelocityRadPerSec = leftVelocity.valueAsDouble // .'in'(Units.RadiansPerSecond)
        inputs.leftSupplyCurrentAmps = leftSupplyCurrent.valueAsDouble // .'in'(Units.Amps)
        inputs.leftStatorCurrentAmps = leftStatorCurrent.valueAsDouble // .'in'(Units.Amps)
        inputs.leftTempCelsius = leftTemp.valueAsDouble // .'in'(Units.Celsius)
        inputs.leftPositionRad = leftPosition.valueAsDouble

        inputs.rightConnected = isRightConnected
        inputs.rightAppliedVoltage = rightVoltage.valueAsDouble // .'in'(Units.Volts)
        inputs.rightVelocityRadPerSec = rightVelocity.valueAsDouble // .'in'(Units.RadiansPerSecond)
        inputs.rightSupplyCurrentAmps = rightSupplyCurrent.valueAsDouble // .'in'(Units.Amps)
        inputs.rightStatorCurrentAmps = rightStatorCurrent.valueAsDouble // .'in'(Units.Amps)
        inputs.rightTempCelsius = rightTemp.valueAsDouble // .'in'(Units.Celsius)
        inputs.rightPositionRad = rightPosition.valueAsDouble
    }

    // Sets the elevator to move at voltage
    override fun setPosition(position: Double) {
        left.setControl(leftPositionRequest.withPosition(position))
        right.setControl(rightPositionRequest.withPosition(position))
    }

    // THE CONFIGURATIONS
    companion object {
        // CONSTANTS MAYBE
        val talonSlot0Config =
            Slot0Configs().apply {
                kP = 100.0
                kI = 0.0
                kD = 0.1
                kS = 0.2
            }
        val leftConfig =
            TalonFXConfiguration().apply {
                CurrentLimits.apply {
                    SupplyCurrentLimit = ElevatorConstants.TALON_SUPPLY_LIMIT
                    StatorCurrentLimit = ElevatorConstants.TALON_STATOR_LIMIT
                }

                MotorOutput.apply {
                    NeutralMode = ElevatorConstants.LEFT_NEUTRAL_MODE
                    Inverted = ElevatorConstants.LEFT_INVERSION
                }

                Slot0 = talonSlot0Config
            }
        val rightConfig =
            TalonFXConfiguration().apply {
                CurrentLimits.apply {
                    SupplyCurrentLimit = ElevatorConstants.TALON_SUPPLY_LIMIT
                    StatorCurrentLimit = ElevatorConstants.TALON_STATOR_LIMIT
                }

                MotorOutput.apply {
                    NeutralMode = ElevatorConstants.RIGHT_NEUTRAL_MODE
                    Inverted = ElevatorConstants.RIGHT_INVERSION
                }

                Slot0 = talonSlot0Config
            }
    }
}
