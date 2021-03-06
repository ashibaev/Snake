package ru.leoltron.snake;

import lombok.val;
import ru.leoltron.snake.game.ClassicSnakeController;
import ru.leoltron.snake.game.Game;
import ru.leoltron.snake.game.generators.ClassicAppleGenerator;
import ru.leoltron.snake.game.generators.ClassicGameFieldGenerator;
import ru.leoltron.snake.gui.GamePanel;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.IOException;

import static ru.leoltron.snake.game.Direction.*;

public class Main {
    private static void setFrame(JFrame frame,
                                 GamePanel gamePanel,
                                 int panelWidth,
                                 int panelHeight,
                                 KeyListener listener) {
        frame.add(gamePanel);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.getContentPane().add(gamePanel, BorderLayout.CENTER);
        frame.getContentPane().setPreferredSize(new Dimension(panelWidth, panelHeight));
        frame.setResizable(false);
        frame.pack();
        frame.setVisible(true);
        frame.addKeyListener(listener);
        frame.setLocationRelativeTo(null);
        frame.setTitle("Snake");
        try {
            frame.setIconImage(ImageIO.read(new File(String.join(File.separator,
                    "resources", "textures", "snake", "head.png"))));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws InterruptedException, IOException {
        val scale = 0.25d;

        val fieldWidth = 64;
        val fieldHeight = 32;
        val panelWidth = (int) (fieldWidth * 64 * scale);
        val panelHeight = (int) (fieldHeight * 64 * scale);
        val game = new Game(
                new ClassicAppleGenerator(),
                new ClassicGameFieldGenerator(),
                new ClassicSnakeController(),
                fieldWidth,
                fieldHeight);
        game.startNewGame();
        val gui = new GamePanel(fieldWidth, fieldHeight, game, scale);
        val frame = new JFrame();

        val listener = new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_UP:
                    case KeyEvent.VK_W:
                        game.setCurrentDirection(UP);
                        break;
                    case KeyEvent.VK_DOWN:
                    case KeyEvent.VK_S:
                        game.setCurrentDirection(DOWN);
                        break;
                    case KeyEvent.VK_RIGHT:
                    case KeyEvent.VK_D:
                        game.setCurrentDirection(RIGHT);
                        break;
                    case KeyEvent.VK_LEFT:
                    case KeyEvent.VK_A:
                        game.setCurrentDirection(LEFT);
                        break;
                    case KeyEvent.VK_R:
                        game.startNewGame();
                        break;
                    case KeyEvent.VK_P:
                    case KeyEvent.VK_PAUSE:
                        game.switchPaused();
                        break;
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
            }
        };
        setFrame(frame, gui, panelWidth, panelHeight, listener);
        val period = 100;
        val timer = new Timer(period, actionEvent -> {
            game.tick();
            SwingUtilities.updateComponentTreeUI(frame);
        });
        timer.start();

    }
}