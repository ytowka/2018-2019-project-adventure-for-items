package com.mygdx.adventure;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.PolygonMapObject;
import com.badlogic.gdx.maps.objects.PolylineMapObject;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.ChainShape;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.adventure.actors.Map;

import java.awt.Polygon;
import java.util.ArrayList;

public class Collision {
    public static Body createRect(BodyDef.BodyType type, float width, float height) {
        Body createable;

        BodyDef CollisionDef = new BodyDef();
        CollisionDef.position.set(0, 0);
        CollisionDef.type = type;
        createable = game.gameWorld.createBody(CollisionDef);
        PolygonShape CollisionShape = new PolygonShape();
        CollisionShape.setAsBox(width, height);
        createable.createFixture(CollisionShape, 1);

        return createable;
    }

    public static Body createRect(BodyDef.BodyType type, float width, float height, float x, float y, boolean lockRotation) {
        Body createable;

        BodyDef CollisionDef = new BodyDef();
        CollisionDef.position.set(x, y);
        CollisionDef.type = type;
        CollisionDef.fixedRotation = lockRotation;
        createable = game.gameWorld.createBody(CollisionDef);
        PolygonShape CollisionShape = new PolygonShape();
        CollisionShape.setAsBox(width, height);
        createable.createFixture(CollisionShape, 1);

        return createable;
    }

    public static Body createRect(BodyDef.BodyType type, float width, float height, float x, float y, float weight) {
        Body createable;

        BodyDef CollisionDef = new BodyDef();
        CollisionDef.position.set(x, y);
        CollisionDef.type = type;
        createable = game.gameWorld.createBody(CollisionDef);
        PolygonShape CollisionShape = new PolygonShape();
        CollisionShape.setAsBox(width, height);
        createable.createFixture(CollisionShape, weight);

        return createable;
    }

    public static Body createPoly(BodyDef.BodyType type, Vector2[] vertices, int x, int y) {
        Body creatable;

        BodyDef collDef = new BodyDef();
        collDef.position.set(x, y);
        collDef.type = type;
        creatable = game.gameWorld.createBody(collDef);
        ChainShape shape = new ChainShape();
        shape.createChain(vertices);
        creatable.createFixture(shape, 1);

        return creatable;
    }

    public static Body createPoly(BodyDef.BodyType type, float[] vertices, int x, int y,float density) {
        Body creatable;

        BodyDef collDef = new BodyDef();
        collDef.position.set(x, y);
        collDef.type = type;
        creatable = game.gameWorld.createBody(collDef);
        ChainShape shape = new ChainShape();
        shape.createChain(vertices);
        creatable.createFixture(shape, density);

        return creatable;
    }

    public static Body createRect(BodyDef.BodyType type, float width, float height, World world) {
        Body createable;

        BodyDef CollisionDef = new BodyDef();
        CollisionDef.position.set(0, 0);
        CollisionDef.type = type;
        createable = world.createBody(CollisionDef);
        PolygonShape CollisionShape = new PolygonShape();
        CollisionShape.setAsBox(width, height);
        createable.createFixture(CollisionShape, 1);

        return createable;
    }

    public static Body createCircle(BodyDef.BodyType type, float radius, float x, float y, float weight) {
        Body createable;

        BodyDef collisionDef = new BodyDef();
        collisionDef.position.set(x, y);
        collisionDef.type = type;
        createable = game.gameWorld.createBody(collisionDef);
        CircleShape collisionShape = new CircleShape();
        collisionShape.setRadius(radius);
        createable.createFixture(collisionShape, weight);

        return createable;
    }

    public static Body createCircle(BodyDef.BodyType type, float radius, float x, float y, float weight, boolean lockRotation) {
        Body createable;

        BodyDef collisionDef = new BodyDef();
        collisionDef.position.set(x, y);
        collisionDef.type = type;
        collisionDef.fixedRotation = lockRotation;
        createable = game.gameWorld.createBody(collisionDef);
        CircleShape collisionShape = new CircleShape();
        collisionShape.setRadius(radius);
        createable.createFixture(collisionShape, weight);

        return createable;
    }

    public static Body createRect(BodyDef bodyDef, float width, float height, float weight) {
        Body createable;
        createable = game.gameWorld.createBody(bodyDef);

        PolygonShape CollisionShape = new PolygonShape();
        CollisionShape.setAsBox(width, height);
        createable.createFixture(CollisionShape, weight);

        return createable;
    }

    public static Body createCircle(BodyDef bodyDef, float radius, float weight) {
        Body createable;
        createable = game.gameWorld.createBody(bodyDef);

        createable = game.gameWorld.createBody(bodyDef);
        CircleShape collisionShape = new CircleShape();
        collisionShape.setRadius(radius);
        createable.createFixture(collisionShape, weight);

        return createable;
    }

    public static BodyDef createDef(float x, float y, BodyDef.BodyType type, boolean lockRotation) {
        BodyDef collisionDef = new BodyDef();
        collisionDef.position.set(x, y);
        collisionDef.type = type;
        collisionDef.fixedRotation = lockRotation;
        return collisionDef;
    }

    public static void tiledMapCollision(Map map) {
        MapObjects objects = map.collision;
        for (MapObject i : objects) {
            Shape shape;
            if (i instanceof PolylineMapObject) {
                shape = createPolyLine((PolylineMapObject) i,map);
            }else continue;

            Body mapBody;

            BodyDef collDef = new BodyDef();
            collDef.type = BodyDef.BodyType.StaticBody;
            mapBody = game.gameWorld.createBody(collDef);
            mapBody.createFixture(shape,1f);
            shape.dispose();

        }
    }

    public static ArrayList<Body> tiledMapBodies(Map map) {
        MapObjects objects = map.collision;

        ArrayList<Body> bodies = new ArrayList<Body>();
        for (MapObject i : objects) {
            Shape shape;
            if (i instanceof PolylineMapObject) {
                shape = createPolyLine((PolylineMapObject) i,map);
            }else continue;

            Body mapBody;

            BodyDef collDef = new BodyDef();
            collDef.type = BodyDef.BodyType.StaticBody;
            mapBody = game.gameWorld.createBody(collDef);
            mapBody.createFixture(shape,1f);
            shape.dispose();

            bodies.add(mapBody);
        }
        return bodies;
    }
    public static Body tiledMapBody(Map map) {
        MapObjects objects = map.collision;

        ArrayList<Fixture> fixtures = new ArrayList<Fixture>();
        Body body;
        BodyDef collDef = new BodyDef();
        collDef.type = BodyDef.BodyType.StaticBody;
        body = game.gameWorld.createBody(collDef);
        for (MapObject i : objects) {
            Shape shape;
            if (i instanceof PolylineMapObject) {
                shape = createPolyLine((PolylineMapObject) i,map);
            }else continue;

            //Body mapBody;
            body.createFixture(shape,1f);
            shape.dispose();
        }
        return body;
    }

    public static ChainShape createPolyLine(PolylineMapObject mapPolyObj,Map map) {
        float[] vertices = mapPolyObj.getPolyline().getTransformedVertices();
        Vector2[] vectorVertices = new Vector2[vertices.length/2];

        for(int i = 0;i< vectorVertices.length;i++){
            vectorVertices[i] = new Vector2(vertices[i*2]*map.scale,vertices[i*2+1]*map.scale);
        }
        ChainShape cs = new ChainShape();
        cs.createChain(vectorVertices);
        return cs;
    }
    public static PolygonShape createPolygon(PolylineMapObject mapPolyObj,Map map) {
        float[] vertices = mapPolyObj.getPolyline().getTransformedVertices();
        //Vector2[] vectorVertices = new Vector2[vertices.length/2];

        for(int i = 0;i< vertices.length;i++){
            //vectorVertices[i] = new Vector2(vertices[i*2]*map.scale,vertices[i*2+1]*map.scale);
            vertices[i] = vertices[i]*map.scale;
        }
        PolygonShape cs = new PolygonShape();
        cs.set(vertices);
        return cs;
    }
}
