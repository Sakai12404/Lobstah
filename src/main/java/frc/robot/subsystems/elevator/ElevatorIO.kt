package frc.robot.subsystems.elevator

import org.littletonrobotics.junction.AutoLog
import kotlin.jvm.JvmField

interface ElevatorIO {
    @AutoLog
    open class ElevatorIOInputs {
        @JvmField var leftConnected: Boolean = false

        @JvmField var leftAppliedVoltage: Double = 0.0

        @JvmField var leftVelocityRadPerSec: Double = 0.0

        @JvmField var leftSupplyCurrentAmps: Double = 0.0

        @JvmField var leftStatorCurrentAmps: Double = 0.0

        @JvmField var leftTempCelsius: Double = 0.0

        @JvmField var leftPositionRad: Double = 0.0

        @JvmField var rightConnected: Boolean = false

        @JvmField var rightAppliedVoltage: Double = 0.0

        @JvmField var rightVelocityRadPerSec: Double = 0.0

        @JvmField var rightSupplyCurrentAmps: Double = 0.0

        @JvmField var rightStatorCurrentAmps: Double = 0.0

        @JvmField var rightTempCelsius: Double = 0.0

        @JvmField var rightPositionRad: Double = 0.0
    }

    fun updateInputs(inputs: ElevatorIOInputs) {}

    fun setPosition(position: Double) {}
}
