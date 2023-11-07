import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Input extends KeyAdapter {
    private boolean[] keyStates = new boolean[4];

    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        if (keyCode == KeyEvent.VK_UP) {
            keyStates[0] = true;
        } else if (keyCode == KeyEvent.VK_DOWN) {
            keyStates[1] = true;
        } else if (keyCode == KeyEvent.VK_LEFT) {
            keyStates[2] = true;
        } else if (keyCode == KeyEvent.VK_RIGHT) {
            keyStates[3] = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int keyCode = e.getKeyCode();
        if (keyCode == KeyEvent.VK_UP) {
            keyStates[0] = false;
        } else if (keyCode == KeyEvent.VK_DOWN) {
            keyStates[1] = false;
        } else if (keyCode == KeyEvent.VK_LEFT) {
            keyStates[2] = false;
        } else if (keyCode == KeyEvent.VK_RIGHT) {
            keyStates[3] = false;
        }
    }

    public boolean[] getKeyStates() {
        return keyStates;
    }
}