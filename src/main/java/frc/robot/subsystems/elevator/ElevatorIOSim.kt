package frc.robot.subsystems.elevator

import edu.wpi.first.math.system.plant.DCMotor
import edu.wpi.first.math.util.Units
import edu.wpi.first.wpilibj.simulation.*
import edu.wpi.first.wpilibj.smartdashboard.Mechanism2d
import edu.wpi.first.wpilibj.smartdashboard.MechanismLigament2d
import edu.wpi.first.wpilibj.smartdashboard.MechanismRoot2d
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard
import edu.wpi.first.wpilibj.util.Color
import edu.wpi.first.wpilibj.util.Color8Bit
import frc.robot.Constants
import frc.robot.Constants.ElevatorConstants

class ElevatorIOSim : ElevatorIOHardware() {
    // SingleJointedArmSim
    val elevatorSim: SingleJointedArmSim =
        SingleJointedArmSim(
            DCMotor.getKrakenX60(2), // GEARBOX
            1 / ElevatorConstants.GEAR_RATIO, // GEAR RATIO
            ElevatorConstants.ELEVATOR_MOI_KG_MM, // MOMENT OF INERTIA
            ElevatorConstants.MAX_ELEVATOR_LENGTH_METERS, // MAX LENGTH
            ElevatorConstants.MIN_ELEVATOR_ANGLE, // MIN ANGLE CAPABLE OF
            ElevatorConstants.MAX_ELEVATOR_ANGLE, // MAX ANGLE CAPABLE OF
            true, // SIM GRAVITY
            ElevatorConstants.MIN_ELEVATOR_ANGLE, // STARTING ANGLE
        )

    private val mech: Mechanism2d = Mechanism2d(3.0, 3.0)
    private val mechRoot: MechanismRoot2d = mech.getRoot("elevatorRoot", 0.25, 0.25)
    private val elevatorMechanism: MechanismLigament2d =
        mechRoot.append(
            MechanismLigament2d(
                "Elevator Ligament",
                0.0, // MAKE A CONSTANT
                ElevatorConstants.MIN_ELEVATOR_ANGLE,
                0.25, // MAKE A CONSTANT
                Color8Bit(Color.kOrange),
            ),
        )

    private val leftSimState = left.simState
    private val rightSimState = right.simState

    init {
        SmartDashboard.putData("Elevator", mech)
        // Maybe config motors
    }

    override fun updateInputs(inputs: ElevatorIO.ElevatorIOInputs) {
        super.updateInputs(inputs)

        leftSimState.setSupplyVoltage(12.0)
        rightSimState.setSupplyVoltage(12.0)
        elevatorSim.setInput(leftSimState.motorVoltage)
        elevatorSim.update(Constants.LOOP_TIME)

        val elevatorPos = Units.radiansToRotations(elevatorSim.angleRads) * ElevatorConstants.GEAR_RATIO
        leftSimState.setRawRotorPosition(elevatorPos)
        rightSimState.setRawRotorPosition(elevatorPos)

        elevatorMechanism.angle = Units.radiansToDegrees(elevatorSim.angleRads)
    }
}
