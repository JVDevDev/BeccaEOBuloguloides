package main;

import world.World;

import java.awt.*;
import java.io.*;

public class Menu {

    public String[] options = {"novo jogo", "carregar jogo", "sair"};

    public int currentOption = 0;
    public int maxOption = options.length - 1;

    public boolean up,down,enter;

    public static boolean pause = false;

    public static boolean saveExists = false;
    public static boolean saveGame = false;

    public void update(){
        File file = new File("save.txt");
        if(file.exists()){
            saveExists = true;
        } else {
            saveExists = false;
        }
        if (up) {
            up = false;
            currentOption--;
            if (currentOption < 0){
                currentOption = maxOption;
            }
        }
        if (down) {
            down = false;
            currentOption++;
            if (currentOption > maxOption){
                currentOption = 0;
            }
        }
        if (enter) {
            enter = false;
            if (options[currentOption] == "novo jogo" || options[currentOption] == "continuar") {
                Game.gameState = "NORMAL";
                pause = false;
                file = new File("save.txt");
                file.delete();
            } else if(options[currentOption] == "carregar jogo"){
              file = new File("save.txt");
              if(file.exists()){
                  String saver = loadGame(10);
                  applySave(saver);
              }
            } else if (options[currentOption] == "sair") {
                System.exit(1);
            }
        }
    }

    public static void applySave(String str){
        String[] spl = str.split("/");
        for (int i = 0; i < spl.length; i++){
            String[] spl2 = spl[i].split(":");
            switch (spl2[0]){
                case "level":
                    World.restartGame("level" + spl2[1] + ".png");
                    Game.gameState = "NORMAL";
                    pause = false;
                    break;
            }
        }
    }

    public static String loadGame(int encode){
        String line = "";
        File file = new File("save.txt");
        if(file.exists()){
            try {
                String singleLine = null;
                BufferedReader reader = new BufferedReader(new FileReader("save.txt"));
                try{
                    while ((singleLine = reader.readLine()) != null){
                        String[] transition = singleLine.split(":");
                        char[] val = transition[1].toCharArray();
                        transition[1] = "";
                        for (int i = 0; i < val.length; i++){
                            val[i] -= encode;
                            transition[1] += val[i];
                        }
                        line += transition[0];
                        line += ":";
                        line += transition[1];
                        line += "/";
                    }
                }catch (IOException e){}
            } catch (FileNotFoundException e) {}
        }
        return line;
    }

    public static void saveGame(String[] val1, int[] val2, int encode){
        BufferedWriter escrever = null;
        try{
            escrever =  new BufferedWriter(new FileWriter("save.txt"));
        }catch(IOException e){
            e.printStackTrace();
        }
        for (int i = 0; i < val1.length; i++){
            String current = val1[i];
            current+=":";
            char[] value = Integer.toString(val2[i]).toCharArray();
            for(int n = 0; n < value.length; n++){
                value[n] += encode;
                current += value[n];
            }
            try{
                escrever.write(current);
                if (i < val1.length - 1){
                    escrever.newLine();
                }
            }catch (IOException e){}
        }
        try {
            escrever.flush();
            escrever.close();
        }catch (IOException e){}
    }
    public void render(Graphics g){
        g.setColor(new Color(71, 122, 42));
        g.fillRect(0, 0, Game.WIDTH * Game.SCALE, Game.HEIGHT * Game.SCALE);
        g.setColor(new Color(187, 78, 78));
        g.setFont(new Font("Arial", Font.BOLD, 48));
        g.drawString("Anya e os Gulobiloides", (Game.WIDTH * Game.SCALE)/2 - 255, (Game.HEIGHT * Game.SCALE) / 2 - 80);

        //opções do menu

        g.setColor(new Color(255, 255, 255));
        g.setFont(new Font("Arial", Font.BOLD, 38));
        if (!pause){
            g.drawString("Novo Jogo", (Game.WIDTH * Game.SCALE)/2 - 100, (Game.HEIGHT * Game.SCALE) / 2 + 50);
        } else{
            g.drawString("Continuar", (Game.WIDTH * Game.SCALE)/2 - 100, (Game.HEIGHT * Game.SCALE) / 2 + 50);
        }

        g.setColor(new Color(255, 255, 255));
        g.drawString("Carregar Jogo", (Game.WIDTH * Game.SCALE)/2 - 135, (Game.HEIGHT * Game.SCALE) / 2 + 110);
        g.setColor(new Color(255, 255, 255));
        g.drawString("Sair", (Game.WIDTH * Game.SCALE)/2 - 40, (Game.HEIGHT * Game.SCALE) / 2 + 170);

        if (!pause){
            if (options[currentOption] == "novo jogo"){
                g.setColor(new Color(187, 78, 78));
                g.drawString("Novo Jogo", (Game.WIDTH * Game.SCALE)/2 - 100, (Game.HEIGHT * Game.SCALE) / 2 + 50);
            }
        }else {
            if (options[currentOption] == "novo jogo"){
                g.setColor(new Color(187, 78, 78));
                g.drawString("Continuar", (Game.WIDTH * Game.SCALE)/2 - 100, (Game.HEIGHT * Game.SCALE) / 2 + 50);
            }
        }

        if (options[currentOption] == "carregar jogo"){
            g.setColor(new Color(187, 78, 78));
            g.drawString("Carregar Jogo", (Game.WIDTH * Game.SCALE)/2 - 135, (Game.HEIGHT * Game.SCALE) / 2 + 110);
        }
        if (options[currentOption] == "sair"){
            g.setColor(new Color(187, 78, 78));
            g.drawString("Sair", (Game.WIDTH * Game.SCALE)/2 - 40, (Game.HEIGHT * Game.SCALE) / 2 + 170);
        }
    }
}