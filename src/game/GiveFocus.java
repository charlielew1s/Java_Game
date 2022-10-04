package game;

public class GiveFocus implements java.awt.event.MouseListener {
    private final GameView view;

    public GiveFocus(GameView v) {
        this.view = v;
    }

    public void mouseClicked(java.awt.event.MouseEvent e) {
    }

    public void mousePressed(java.awt.event.MouseEvent e) {
    }

    public void mouseReleased(java.awt.event.MouseEvent e) {
    }

    public void mouseEntered(java.awt.event.MouseEvent e) {
        view.requestFocus();
        System.out.println("mouseEntered() executed");
    }
    public void mouseExited(java.awt.event.MouseEvent e) {
    }
}
