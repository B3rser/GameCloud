package Game;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {

    private boolean upKey, downKey, leftKey, rigthKey;

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_W:
                upKey = true;
                break;
            case KeyEvent.VK_S:
                downKey = true;
                break;
            case KeyEvent.VK_A:
                leftKey = true;
                break;
            case KeyEvent.VK_D:
                rigthKey = true;
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_W:
                upKey = false;
                break;
            case KeyEvent.VK_S:
                downKey = false;
                break;
            case KeyEvent.VK_A:
                leftKey = false;
                break;
            case KeyEvent.VK_D:
                rigthKey = false;
                break;
        }
    }

    public boolean getUpKey() {
        return this.upKey;
    }

    public boolean getDownKey() {
        return this.downKey;
    }

    public boolean getLeftKey() {
        return this.leftKey;
    }

    public boolean getRigthKey() {
        return this.rigthKey;
    }
}
