package org.firstinspires.ftc.teamcode.libs.pedro;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.canvas.Canvas;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.pedropathing.math.Pose;

public final class DashboardDrawer {
    private static final double ROBOT_RADIUS = 9.0;

    public static void drawRobot(Pose pose) {
        TelemetryPacket packet = new TelemetryPacket();
        Canvas canvas = packet.fieldOverlay();
        canvas.drawImage("/images/biobuzz.png", 0, 0, 144, 144);

        double x = 72.0 - pose.y();
        double y = pose.x() - 72.0;
        double heading = pose.heading() + Math.PI / 2.0;

        canvas.setStroke("#4CAF50");
        canvas.setStrokeWidth(1);

        canvas.strokeCircle(x, y, ROBOT_RADIUS);

        double endX = x + ROBOT_RADIUS * Math.cos(heading);
        double endY = y + ROBOT_RADIUS * Math.sin(heading);

        canvas.strokeLine(x, y, endX, endY);

        FtcDashboard.getInstance().sendTelemetryPacket(packet);
    }
}