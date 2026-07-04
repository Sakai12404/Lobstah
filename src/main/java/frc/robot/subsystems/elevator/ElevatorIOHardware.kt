package frc.robot.subsystems.elevator

import com.ctre.phoenix6.BaseStatusSignal
import com.ctre.phoenix6.configs.Slot0Configs
import com.ctre.phoenix6.configs.TalonFXConfiguration
import com.ctre.phoenix6.controls.PositionVoltage
import com.ctre.phoenix6.hardware.TalonFX
import edu.wpi.first.units.Units
import frc.robot.Constants.ElevatorConstants
import frc.team449.util.PhoenixUtil.tryUntilOk

open class ElevatorIOHardware : ElevatorIO {
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
        get() =
            BaseStatusSignal.isAllGood(
                leftVoltage,
                leftVelocity,
                leftStatorCurrent,
            )
    private val isRightConnected: Boolean
        get() =
            BaseStatusSignal.isAllGood(
                rightVoltage,
                rightVelocity,
                rightStatorCurrent,
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
        inputs.leftAppliedVoltage = leftVoltage.value.`in`(Units.Volts)
        inputs.leftVelocityRadPerSec = leftVelocity.value.`in`(Units.RadiansPerSecond)
        inputs.leftSupplyCurrentAmps = leftSupplyCurrent.value.`in`(Units.Amps)
        inputs.leftStatorCurrentAmps = leftStatorCurrent.value.`in`(Units.Amps)
        inputs.leftTempCelsius = leftTemp.value.`in`(Units.Celsius)

        inputs.rightConnected = isRightConnected
        inputs.rightAppliedVoltage = rightVoltage.value.`in`(Units.Volts)
        inputs.rightVelocityRadPerSec = rightVelocity.value.`in`(Units.RadiansPerSecond)
        inputs.rightSupplyCurrentAmps = rightSupplyCurrent.value.`in`(Units.Amps)
        inputs.rightStatorCurrentAmps = rightStatorCurrent.value.`in`(Units.Amps)
        inputs.rightTempCelsius = rightTemp.value.`in`(Units.Celsius)
    }

    // sets both motors control based of position requests
    override fun setPosition(position: Double) {
        left.setControl(leftPositionRequest.withPosition(position * ElevatorConstants.DRUM_RADIUS * 2 * Math.PI))
        right.setControl(rightPositionRequest.withPosition(position * ElevatorConstants.DRUM_RADIUS * 2 * Math.PI))
    }

    // THE CONFIGURATIONS
    companion object {
        // CONSTANTS MAYBE
        val talonSlot0Config =
            Slot0Configs().apply {
                kP = ElevatorConstants.kP
                kI = ElevatorConstants.kI
                kD = ElevatorConstants.kD
                kG = ElevatorConstants.kG
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
