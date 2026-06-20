package frc.robot

import frc.team449.commands.SwerveRequestCommand

class Binding(
    val robotContainer: RobotContainer,
) {
    val driver = robotContainer.driveController

    fun setDefaultCommands() {
        robotContainer.drive.defaultCommand =
            SwerveRequestCommand(
                robotContainer.drive,
                { -driver.leftY },
                { -driver.leftX },
                { -driver.rightX },
            )
    }

    // simple control binds with arbitrary values
    fun bindControls() {
        driver
            .cross()
            .onTrue(
                robotContainer.elevator.moveElevator(1.2),
            )
        driver
            .triangle()
            .onTrue(
                robotContainer.elevator.returnElevatorBottom(),
            )
        driver
            .circle()
            .onTrue(
                robotContainer.elevator.moveElevatorTwice(0.9, 1.735),
            )

        driver
            .touchpad()
            .onTrue(robotContainer.drive.seedFieldCentric())
    }
}
