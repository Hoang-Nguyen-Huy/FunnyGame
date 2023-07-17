package GameMenu;
import FlappyBird.flappyBird;
import Snake.GameFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Menu extends JFrame implements ActionListener {

    private JButton snakeButton;
    private JButton pingPongButton;
    private JButton flappyBirdButton;

    public Menu() {
        setTitle("Game Menu");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 600);
        setLocationRelativeTo(null);

        //Đặt nền background là màu đen
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.black);

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.insets = new Insets(10, 10, 10, 10);

        snakeButton = new JButton("Snake Game");
        pingPongButton = new JButton("Ping Pong");
        flappyBirdButton = new JButton("Flappy Bird");

        // Đặt màu nền và màu chữ cho các nút
        snakeButton.setBackground(Color.black);
        snakeButton.setForeground(Color.yellow);
        pingPongButton.setBackground(Color.black);
        pingPongButton.setForeground(Color.yellow);
        flappyBirdButton.setBackground(Color.black);
        flappyBirdButton.setForeground(Color.yellow);

        // Đặt font chữ cho các nút
        Font buttonFont = new Font("Ink Free", Font.BOLD, 24);
        snakeButton.setFont(buttonFont);
        pingPongButton.setFont(buttonFont);
        flappyBirdButton.setFont(buttonFont);

        // Đặt các nút vào giữa màn hình
        panel.add(snakeButton, constraints);

        constraints.gridy = 1;
        panel.add(pingPongButton, constraints);

        constraints.gridy = 2;
        panel.add(flappyBirdButton, constraints);

        snakeButton.addActionListener(this);
        pingPongButton.addActionListener(this);
        flappyBirdButton.addActionListener(this);

        add(panel);
        setVisible(true);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == snakeButton) {
            dispose(); // Đóng cửa sổ menu
            new GameFrame(); // Khởi chạy trò chơi Snake
        } else if (e.getSource() == pingPongButton) {
            // Xử lý khi người dùng chọn Ping Pong Game
            // ...
        } else if (e.getSource() == flappyBirdButton) {
            dispose();
            new flappyBird().go();
        }
    }
}
