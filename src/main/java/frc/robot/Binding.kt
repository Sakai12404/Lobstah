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

    fun bindControls() {
        driver
            .cross()
            .onTrue(
                robotContainer.elevator.moveElevator(0.534)
            )
        driver
            .circle()
            .onTrue(
                robotContainer.elevator.moveElevatorTwice(0.32,0.56)
            )
        driver
            .triangle()
            .onTrue(
                robotContainer.elevator.returnElevatorBottom()
            )
        driver
            .touchpad()
            .onTrue(robotContainer.drive.seedFieldCentric())
        /*driver // only works w xbox tf
            .start()
            .onTrue(
                robotContainer.drive.seedFieldCentric()
            )
        */
    }
}
