import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.ImageObserver;
import java.util.Random;


public class GamePanel extends JPanel implements ActionListener {

    static final int SCREEN_WIDTH = 600;
    static final int SCREEN_HEIGHT = 600;
    static final int UNIT_SIZE = 25;
    static final int GAME_UNITS = (SCREEN_WIDTH * SCREEN_HEIGHT) / UNIT_SIZE;
    int delay = 75;
    final int[] x = new int[GAME_UNITS];
    final int[] y = new int[GAME_UNITS];
    int bodyParts = 6;
    int applesEaten;
    int appleX;
    int appleY;
    char direction = 'R';
    Timer timer;
    Random random;
    private enum STATE {
        MENU, GAME, END_SCREEN;
    };
    private STATE state = STATE.MENU;


    GamePanel(){
        random = new Random();
        this.setPreferredSize(new Dimension(SCREEN_WIDTH + 200, SCREEN_HEIGHT));
        this.setBackground(Color.black);
        this.setFocusable(true);
        this.addKeyListener(new myKeyAdapter());
        startGame();
    }

    public void startGame() {
        newApple();
        state = STATE.MENU;
        timer = new Timer(delay, this);
        timer.start();

    }

    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        draw(g);

    }
    public void draw(Graphics g) {

        if(state == STATE.MENU){
            g.setColor(new Color(100, 150, 100));
            g.setFont(new Font("ink free", Font.BOLD, 50));
            FontMetrics metrics = getFontMetrics(g.getFont());
            g.drawString("Welcome to Snake!", ((SCREEN_WIDTH+200) - metrics.stringWidth("Welcome to Snake!"))/2, 200);

            g.setColor(Color.red);
            g.setFont(new Font("ink free", Font.BOLD, 50));
            FontMetrics metrics2 = getFontMetrics(g.getFont());
            g.drawString("Press Space to begin", ((SCREEN_WIDTH+200) - metrics2.stringWidth("Press Space to begin"))/2, 300);


            ImageIcon icon = new ImageIcon("SnakeGameImage.png");


            Image image = icon.getImage().getScaledInstance(
                    150 ,
                    150 ,
                    Image.SCALE_SMOOTH);
            icon = new ImageIcon(image, icon.getDescription());

            g.drawImage(image, 325, 400, new ImageObserver() {
                @Override
                public boolean imageUpdate(Image img, int infoflags, int x, int y, int width, int height) {
                    return false;
                }
            });
            validate();



            g.setColor(Color.white);
            g.setFont(new Font("arial", Font.BOLD, 40));
            FontMetrics metrics4 = getFontMetrics(g.getFont());
            g.drawString("Snake Fact", ((SCREEN_WIDTH+150) - metrics4.stringWidth("Snake Fact")), 200);


            Random randomNum = new Random();
            int randomNumber = randomNum.nextInt(10) + 1;

            if(randomNumber <= 1){
                g.setColor(Color.white);
                g.setFont(new Font("arial", Font.BOLD, 20));
                FontMetrics metrics3 = getFontMetrics(g.getFont());
                g.drawString("There Are 3,686 Species of Snakes", ((SCREEN_WIDTH+150) - metrics3.stringWidth("There Are 3,686 Species of Snakes")), 300);
            }
            if(randomNumber <= 2){
                g.setColor(Color.white);
                g.setFont(new Font("arial", Font.BOLD, 20));
                FontMetrics metrics3 = getFontMetrics(g.getFont());
                g.drawString("Snakes Can Slither 12.5 Miles Per Hour", ((SCREEN_WIDTH+150) - metrics3.stringWidth("There Are 3,686 Species of Snakes")), 300);
            }
            if(randomNumber <= 3){
                g.setColor(Color.white);
                g.setFont(new Font("arial", Font.BOLD, 20));
                FontMetrics metrics3 = getFontMetrics(g.getFont());
                g.drawString("1.1 Million People Own Pet Snakes", ((SCREEN_WIDTH+150) - metrics3.stringWidth("There Are 3,686 Species of Snakes")), 300);
            }
            if(randomNumber <= 4){
                g.setColor(Color.white);
                g.setFont(new Font("arial", Font.BOLD, 20));
                FontMetrics metrics3 = getFontMetrics(g.getFont());
                g.drawString("They Can Survive For Months Without Eating", ((SCREEN_WIDTH+150) - metrics3.stringWidth("There Are 3,686 Species of Snakes")), 300);
            }
            if(randomNumber <= 5){
                g.setColor(Color.white);
                g.setFont(new Font("arial", Font.BOLD, 20));
                FontMetrics metrics3 = getFontMetrics(g.getFont());
                g.drawString(" They Smell The Air To Hunt", ((SCREEN_WIDTH+150) - metrics3.stringWidth("There Are 3,686 Species of Snakes")), 300);
            }
            if(randomNumber <= 6){
                g.setColor(Color.white);
                g.setFont(new Font("arial", Font.BOLD, 20));
                FontMetrics metrics3 = getFontMetrics(g.getFont());
                g.drawString("Reticulated Pythons Can Reach 33 Feet In Length", ((SCREEN_WIDTH+150) - metrics3.stringWidth("There Are 3,686 Species of Snakes")), 300);
            }
            if(randomNumber <= 7){
                g.setColor(Color.white);
                g.setFont(new Font("arial", Font.BOLD, 20));
                FontMetrics metrics3 = getFontMetrics(g.getFont());
                g.drawString("They Can Survive For Months Without Eating", ((SCREEN_WIDTH+150) - metrics3.stringWidth("There Are 3,686 Species of Snakes")), 300);
            }
            if(randomNumber <= 8){
                g.setColor(Color.white);
                g.setFont(new Font("arial", Font.BOLD, 20));
                FontMetrics metrics3 = getFontMetrics(g.getFont());
                g.drawString("Snakes Do Not Have External Ears – But Can Still Hear", ((SCREEN_WIDTH+150) - metrics3.stringWidth("There Are 3,686 Species of Snakes")), 300);
            }
            else{
                g.setColor(Color.white);
                g.setFont(new Font("arial", Font.BOLD, 20));
                FontMetrics metrics3 = getFontMetrics(g.getFont());
                g.drawString("Snakes Evolved 142 Million Years Ago", ((SCREEN_WIDTH+150) - metrics3.stringWidth("There Are 3,686 Species of Snakes")), 300);
            }


        }
        if (state == STATE.GAME) {
            for (int i = 0; i < SCREEN_HEIGHT / UNIT_SIZE; i++) {
                g.setColor(Color.gray);
                g.drawLine(i * UNIT_SIZE, 0, i * UNIT_SIZE, SCREEN_HEIGHT);
                g.drawLine(0, i * UNIT_SIZE, SCREEN_WIDTH, i * UNIT_SIZE);
            }
            g.setColor(Color.red);
            g.fillOval(appleX, appleY, UNIT_SIZE, UNIT_SIZE);

            for (int i = 0; i < bodyParts; i++) {
                if (i == 0) {
                    g.setColor(new Color(45, 150, 0));
                    g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
                } else {
                    g.setColor(new Color(45, 200, 0));
                    g.setColor(new Color(random.nextInt(255), random.nextInt(255), random.nextInt(255)));
                    g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
                }
            }
            g.setColor(Color.blue);
            g.setFont(new Font("arial", Font.BOLD, 40));
            FontMetrics metrics = getFontMetrics(g.getFont());
            g.drawString("Score: " + applesEaten, ((SCREEN_WIDTH+150) - metrics.stringWidth("Score" + applesEaten)), g.getFont().getSize());


        }
        else if(state == STATE.END_SCREEN){
            g.setColor(Color.red);
            g.setFont(new Font("ink free", Font.BOLD, 75));
            FontMetrics metrics = getFontMetrics(g.getFont());
            g.drawString("Game Over", ((SCREEN_WIDTH+200) - metrics.stringWidth("Game Over"))/2, 250);

            g.setColor(Color.blue);
            g.setFont(new Font("ink free", Font.BOLD, 70));
            FontMetrics metrics2 = getFontMetrics(g.getFont());
            g.drawString("Score: " + applesEaten, ((SCREEN_WIDTH+200) - metrics2.stringWidth("Score: " + applesEaten))/2, 350);

            g.setColor(new Color(150, 200, 50));
            g.setFont(new Font("ink free", Font.BOLD, 25));
            FontMetrics metrics3 = getFontMetrics(g.getFont());
            g.drawString("Press R to Restart", 500, 550 );

            g.setColor(new Color(150, 100, 50));
            g.setFont(new Font("ink free", Font.BOLD, 25));
            FontMetrics metrics4 = getFontMetrics(g.getFont());
            g.drawString("Press M to go back to Menu", ((SCREEN_WIDTH+200)- metrics3.stringWidth("Press M to go back to Menu"))/8, 550 );
        }
    }
    public void newApple(){
        appleX = random.nextInt((int)(SCREEN_WIDTH/UNIT_SIZE)) * UNIT_SIZE;
        appleY = random.nextInt((int)(SCREEN_HEIGHT/UNIT_SIZE)) * UNIT_SIZE;

    }
    public void move(){
        for(int i = bodyParts; i > 0; i--){
            x[i] = x[i-1];
            y[i] = y[i-1];
        }

        switch(direction){
            case 'U':
                y[0] = y[0] - UNIT_SIZE;
                break;
            case 'D':
                y[0] = y[0] + UNIT_SIZE;
                break;
            case 'L':
                x[0] = x[0] - UNIT_SIZE;
                break;
            case 'R':
                x[0] = x[0] + UNIT_SIZE;
                break;
        }

    }
    public void checkApple(){
        if((x[0]==appleX) && (y[0]==appleY)){
            bodyParts++;
            applesEaten++;
            newApple();
        }

    }
    public void checkCollisions(){
        //Checks if head collides with body
        for(int i = bodyParts; i > 0; i--){
            if((x[0]==x[i]) && (y[0]==y[i])){
                state = STATE.END_SCREEN;
            }
        }
        //Checks if head touches left border
        if(x[0] < 0){
            state = STATE.END_SCREEN;
        }
        //Checks if head touches right border
        if(x[0] > SCREEN_WIDTH){
            state = STATE.END_SCREEN;
        }
        //Checks if head touches bottom border
        if(y[0] < 0){
            state = STATE.END_SCREEN;
        }
        //Checks if head touches top border
        if(y[0] > SCREEN_HEIGHT){
            state = STATE.END_SCREEN;
        }

        if(state == STATE.END_SCREEN){
            timer.stop();
        }
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if(state == STATE.GAME){
            move();
            checkApple();
            checkCollisions();
        }


        repaint();
    }

    public class myKeyAdapter extends KeyAdapter {
       @Override
       public void keyPressed(KeyEvent e){

           if(e.getKeyCode() == KeyEvent.VK_M){
               timer.stop();
               state = state.MENU;
               repaint();
               timer.start();
           }

           if(e.getKeyCode() == KeyEvent.VK_SPACE){
               timer.stop();
               direction = 'R';
               state = STATE.GAME;
               bodyParts = 6;
               applesEaten = 0;
               newApple();
               for(int i = 0; i < bodyParts; i++) {
                   x[i] = 0;
                   y[i] = 0;
               }
               repaint();
               timer.start();
           }
           if(e.getKeyCode() == KeyEvent.VK_R){
               timer.stop();
               direction = 'R';
               state = STATE.GAME;
               bodyParts = 6;
               applesEaten = 0;
               newApple();
               for(int i = 0; i < bodyParts; i++) {
                   x[i] = 0;
                   y[i] = 0;
               }
               repaint();
               timer.start();



           }
           switch(e.getKeyCode()){
               case KeyEvent.VK_LEFT:
                   if(direction != 'R'){
                       direction = 'L';
                   }
                   break;
               case KeyEvent.VK_RIGHT:
                   if(direction != 'L'){
                       direction = 'R';
                   }
                   break;
               case KeyEvent.VK_UP:
                   if(direction != 'D'){
                       direction = 'U';
                   }
                   break;
               case KeyEvent.VK_DOWN:
                   if(direction != 'U'){
                       direction = 'D';
                   }
                   break;

           }

       }
    }
}
