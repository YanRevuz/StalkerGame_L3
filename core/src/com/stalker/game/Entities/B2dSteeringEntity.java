package com.stalker.game.Entities;

import com.badlogic.gdx.ai.steer.Steerable;
import com.badlogic.gdx.ai.steer.SteeringAcceleration;
import com.badlogic.gdx.ai.steer.SteeringBehavior;
import com.badlogic.gdx.ai.utils.Location;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.stalker.game.Tools.SteeringUtils;


public class B2dSteeringEntity implements Steerable<Vector2> {
    private Body body;
    private float boundingRadius;
    private boolean tagged;
    private float maxLinearSpeed, maxLinearAcceleration;
    private float maxAngularSpeed, maxAngularAcceleration;

    SteeringBehavior<Vector2> behavior;
    SteeringAcceleration<Vector2> steeringOutput;

    public B2dSteeringEntity(Body body, float boundingRadius) {
        this.body = body;
        this.boundingRadius = boundingRadius;

        this.maxLinearSpeed = 500;
        this.maxLinearAcceleration = 500;
        this.maxAngularSpeed = 30;
        this.maxAngularAcceleration = 5;
        this.tagged = false;
        this.steeringOutput = new SteeringAcceleration<Vector2>(new Vector2());
        this.body.setUserData(this);
    }

    @Override
    public float getAngularVelocity() {
        return this.body.getAngularVelocity();
    }

    @Override
    public float getBoundingRadius() {
        return 0;
    }

    @Override
    public Vector2 getLinearVelocity() {
        return this.body.getLinearVelocity();
    }

    @Override
    public boolean isTagged() {
        return this.tagged;
    }

    @Override
    public void setTagged(boolean tagged) {
        this.tagged = tagged;
    }

    @Override
    public float getMaxAngularAcceleration() {
        return this.maxAngularAcceleration;
    }

    @Override
    public float getMaxAngularSpeed() {
        return this.maxAngularSpeed;
    }

    @Override
    public float getMaxLinearAcceleration() {
        return this.maxLinearAcceleration;
    }

    @Override
    public float getMaxLinearSpeed() {
        return this.maxLinearSpeed;
    }

    @Override
    public float getZeroLinearSpeedThreshold() {
        return 0;
    }

    @Override
    public void setMaxAngularAcceleration(float maxAngularAcceleration) {
        this.maxAngularAcceleration = maxAngularAcceleration;
    }

    @Override
    public void setMaxAngularSpeed(float maxAngularSpeed) {
        this.maxAngularSpeed = maxAngularSpeed;
    }

    @Override
    public void setMaxLinearAcceleration(float maxLinearAcceleration) {
        this.maxLinearAcceleration = maxLinearAcceleration;
    }

    @Override
    public void setMaxLinearSpeed(float maxLinearSpeed) {
        this.maxLinearSpeed = maxLinearSpeed;
    }

    @Override
    public void setZeroLinearSpeedThreshold(float value) {

    }

    @Override
    public Vector2 angleToVector(Vector2 outVector, float angle) {
        return SteeringUtils.angleToVector(outVector, angle);
    }

    @Override
    public float getOrientation() {
        return this.body.getAngle();
    }

    @Override
    public Vector2 getPosition() {
        return this.body.getPosition();
    }

    @Override
    public Location<Vector2> newLocation() {
        return new Location<Vector2>() {
            @Override
            public Vector2 getPosition() {
                return null;
            }

            @Override
            public float getOrientation() {
                return 0;
            }

            @Override
            public void setOrientation(float orientation) {

            }

            @Override
            public float vectorToAngle(Vector2 vector) {
                return 0;
            }

            @Override
            public Vector2 angleToVector(Vector2 outVector, float angle) {
                return null;
            }

            @Override
            public Location<Vector2> newLocation() {
                return null;
            }
        };
    }

    @Override
    public void setOrientation(float orientation) {

    }

    @Override
    public float vectorToAngle(Vector2 vector) {
        return SteeringUtils.vectorToAngle(vector);
    }

    public Body getBody() {
        return this.body;
    }

    public void setBehavior(SteeringBehavior<Vector2> behavior) {
        this.behavior = behavior;
    }

    public SteeringBehavior<Vector2> getBehavior() {
        return this.behavior;
    }

    public void update(float dt) {
        if (this.behavior != null) {
            this.behavior.calculateSteering(steeringOutput);
            applySteering(dt);
        }
    }

    public void applySteering(float dt) {
        boolean anyAcceleration = false;
        if (!steeringOutput.linear.isZero()) {
            Vector2 force = steeringOutput.linear.scl(dt);
            body.applyForceToCenter(force, true);
            anyAcceleration = true;
        }

        if (steeringOutput.angular != 0) {
            body.applyTorque(steeringOutput.angular * dt, true);
            anyAcceleration = true;
        } else {
            Vector2 linVel = getLinearVelocity();
            if (!linVel.isZero()) {
                float newOrientation = vectorToAngle(linVel);
                body.setAngularVelocity((newOrientation - getAngularVelocity()) * dt);
                body.setTransform(body.getPosition(), newOrientation);
            }
        }

        if (anyAcceleration) {
            Vector2 velocity = body.getLinearVelocity();
            float currentSpeedSquare = velocity.len2();
            if (currentSpeedSquare > maxLinearSpeed * maxLinearSpeed) {
                body.setLinearVelocity(velocity.scl(maxLinearSpeed / (float) Math.sqrt(currentSpeedSquare)));

            }
        }
        if (body.getAngularVelocity() > maxAngularSpeed) {
            body.setAngularVelocity(maxAngularSpeed);
        }


    }
}
