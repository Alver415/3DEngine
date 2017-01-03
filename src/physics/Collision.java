package physics;

import game.Object;
import geometry.Vector;

public class Collision{
		private Object a;
		private Object b;
		
		public Collision(Object a, Object b){
			this.a = a;
			this.b = b;
		}
		
		//Algorithm from http://stackoverflow.com/questions/345838/ball-to-ball-collision-detection-and-handling
		public void resolveCollision() {
			int r = 1;

			Vector ap = a.getPosition();
			Vector bp = b.getPosition();
			Vector av = a.getVelocity();
			Vector bv = b.getVelocity();
			Vector delta = (ap.subtract(bp));
			double distance = delta.magnitude();
			if (distance <= 0) return; // Aborts if about to divide by zero
			

			// minimum translation distance to push bs apart after intersecting
			Vector mtd = delta.scalar(((r + r) - distance) / distance);

			// resolve intersection --
			// inverse mass quantities
			double aim = 1 / a.rigidBody.getMass();
			double bim = 1 / b.rigidBody.getMass();

			// push-pull them apart based off their mass
			a.setPosition(ap.add(mtd.scalar(aim / (aim + bim))));
			b.setPosition(bp.subtract(mtd.scalar(bim / (aim + bim))));

			// impact speed
			Vector v = av.subtract(bv);
			double vn = v.dot(mtd.normal());

			// sphere intersecting but moving away from each other already
			if (vn > 0.0f)
				return;

			// collision impulse
			Vector impulse = mtd.normal().scalar((double) (-(1.0 + Physics.damping) * vn) / (aim + bim));

			// change in momentum
			a.setVelocity(av.add(impulse.scalar(aim)));
			b.setVelocity(bv.subtract(impulse.scalar(bim)));
		}
	}