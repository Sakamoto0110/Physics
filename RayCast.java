package com.sakamoto.physics;

import java.awt.Shape;
import java.util.ArrayList;

public class RayCast {
	
	private static float baseAngle = 0;
	public static boolean showLimit = true;

	public static void setAngleTo(float n) { baseAngle = n; }
	public static void addAngle(float n) { baseAngle += n; }

	public static ArrayList<Vector2D> castRaysAt(Vector2D position, ArrayList<Shape> obstacles, int nRays, int rayRange, float FOV) {
		ArrayList<Vector2D> points = new ArrayList<Vector2D>();
		for (int c = 0; c < nRays; c++) {
			float angle = baseAngle + map(c, 0, nRays - 1, 0, (float) FOV);
			boolean limitReach = true;
			for (int c1 = 1; c1 <= rayRange; c1++) {
				float xxx = position.x + c1 * (float) Math.cos(angle);
				float yyy = position.y + c1 * (float) Math.sin(angle);
				for (int c2 = 0; c2 < obstacles.size(); c2++) {
					Shape o = obstacles.get(c2);
					if (o.contains(xxx, yyy)) {
						Vector2D collision = new Vector2D(xxx, yyy);
						points.add(collision);
						c1 = (int) rayRange;
						c2 = (int) obstacles.size();
						limitReach = false;
					}
				}
			}
			if (limitReach && showLimit) {
				float xxx = position.x + rayRange * (float) Math.cos(angle);
				float yyy = position.y + rayRange * (float) Math.sin(angle);
				points.add(new Vector2D(xxx, yyy));
			}
		}
		return points;
	}

	private static float map(float X, float A, float B, float C, float D) {
		return (X - A) / (B - A) * (D - C) + C;
	}
}
