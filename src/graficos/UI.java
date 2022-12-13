package graficos;

import entities.Player;
import main.Game;

import java.awt.*;

public class UI {

    public void render(Graphics g){
        g.setColor(new Color(133, 31, 31));
        g.fillRect(8, 4, 100,8);
        g.setColor(new Color(56, 140, 50));
        g.fillRect(8, 4, (int)((Game.player.life / Game.player.maxLife) * 100) ,8);
        /*
        g.setColor(Color.white);
        g.setFont(new Font("arial", Font.BOLD, 8));
        g.drawString((int) Player.life + "/" + (int) Player.maxLife, 45, 11);
         */
    }
}
