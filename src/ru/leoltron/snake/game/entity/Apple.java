package ru.leoltron.snake.game.entity;

public class Apple extends FieldObject {

    @Override
    public void onCollisionWith(FieldObject object) {
        setDead();
    }

}
