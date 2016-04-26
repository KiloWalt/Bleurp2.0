package com.mygdx.bleurp.States;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.bleurp.Bleurp;

import java.util.Stack;

/**
 * Created by Albert on 2016-03-28.
 */
public class GSM {
    public Stack<State> states;

    public GSM() {
        states = new Stack<State>();
    }

    public void push(State s) {
        states.push(s);
    }

    public void pop() {
        states.pop().dispose();
    }

    public void set(State s) {
        states.pop();
        states.push(s);
    }

    public void update(float dt) {
        states.peek().update(dt);
    }
    public void render(SpriteBatch sb) {
        states.peek().render(sb);

    }
    public void dispose(){
        states.peek().dispose();

    }

}
