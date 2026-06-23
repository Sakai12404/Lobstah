package frc.robot

import com.ctre.phoenix6.signals.InvertedValue
import com.ctre.phoenix6.signals.NeutralModeValue
import edu.wpi.first.wpilibj.RobotBase
import kotlin.math.PI

object Constants {
    // --- OPERATIONAL MODES ---
    enum class Mode {
        REAL,
        SIM,
        REPLAY,
    }

    val CURRENT_MODE: Mode = if (RobotBase.isReal()) Mode.REAL else Mode.SIM
    const val TUNING_MODE: Boolean = false

    // --- SYSTEM TIMING ---
    const val LOOP_TIME = 0.02

    // --- PHYSICAL SPECS ---
    const val ROBOT_MASS_KG = 59.8
    const val ROBOT_WIDTH_INCHES = 35.0 // including bumpers (front to rear)
    const val ROBOT_LENGTH_INCHES = 34.125 // including bumpers (left to right)

    object DriveConstants {
        // --- LOOP TIMING ---
        const val ODOMETRY_LOOP_HZ = 100.0

        // --- PHYSICAL SPECS ---
        const val TRACKWIDTH_INCHES = 21.75 // front to rear
        const val WHEELBASE_INCHES = 21.75 // left to right
        const val WHEEL_COF = 1.4

        // --- SPEED LIMITS (STANDARD) ---
        const val MAX_LINEAR_SPEED_METERS_PER_SEC = 4.7244
        const val MAX_ANGULAR_SPEED_RADS_PER_SEC = 2 * PI

        // --- SPEED LIMITS (SLOW) ---
        const val SLOW_LINEAR_SPEED_METERS_PER_SEC = 1.5
        const val SLOW_ANGULAR_SPEED_RADS_PER_SEC = 0.5804

        // --- DEADBANDS & TOLERANCE ---
        const val TRANSLATION_DEADBAND = 0.1
        const val ANGULAR_DEADBAND = 0.1
        const val INTERRUPT_DEADBAND = 0.25
        const val MODULE_ALIGN_TOLERANCE_DEG = 5.0
    }

    object OperatorConstants {
        const val DRIVER_CONTROLLER_PORT = 0
    }

    object ElevatorConstants {
        // --- TALON IDS ---
        const val LEFT_ID = 0 // nah
        const val RIGHT_ID = 1 // nah

        // ---  TALON SETTINGS ---
        const val TALON_SUPPLY_LIMIT = 60.0 // Limits the current drawn from the battery
        const val TALON_STATOR_LIMIT = 90.0 // Limits the current inside the motor

        const val kP = 30.0
        const val kI = 0.75
        const val kD = 0.20
        const val kG = 0.4

        // --- LEFT TALON ---
        val LEFT_NEUTRAL_MODE = NeutralModeValue.Brake
        val LEFT_INVERSION = InvertedValue.CounterClockwise_Positive

        // --- RIGHT TALON ---
        val RIGHT_NEUTRAL_MODE = NeutralModeValue.Brake
        val RIGHT_INVERSION = InvertedValue.Clockwise_Positive

        // --- PHYSICAL SPEC & GEARING ---
        const val CARRIAGE_MASS = 4.0
        const val GEAR_RATIO = 2.0 / 3.0
        const val DRUM_RADIUS = 0.02
        const val MIN_ELEVATOR_HEIGHT = 0.0
        const val MAX_ELEVATOR_HEIGHT = 1.735
    }
}
