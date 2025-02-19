import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class ChessBoardWithDice extends JFrame {

    private JPanel boardPanel;
    private JButton diceButton;
    private JLabel diceResultLabel;
    private int diceValue;
    private int tokenPosition; // 0-63 representing the board squares
    private JLabel token;


    public ChessBoardWithDice() {
        setTitle("Chess Board with Dice");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Board Panel
        boardPanel = new JPanel(new GridLayout(8, 8));
        for (int i = 0; i < 64; i++) {
            JPanel square = new JPanel();
            square.setBackground((i + i / 8) % 2 == 0 ? Color.WHITE : Color.LIGHT_GRAY); // Checkerboard pattern
            boardPanel.add(square);
        }

        // Token
        token = new JLabel("T");  // Or use an image icon
        token.setFont(new Font("Arial", Font.BOLD, 20));
        tokenPosition = 0; // Start at the first square (index 0)
        JPanel startSquare = (JPanel) boardPanel.getComponent(tokenPosition);
        startSquare.add(token);


        // Dice Panel
        JPanel dicePanel = new JPanel();
        diceButton = new JButton("Roll Dice");
        diceResultLabel = new JLabel("Dice: ");
        dicePanel.add(diceButton);
        dicePanel.add(diceResultLabel);

        diceButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                rollDice();
            }
        });

        add(boardPanel, BorderLayout.CENTER);
        add(dicePanel, BorderLayout.SOUTH);

        pack();
        setVisible(true);
    }

    private void rollDice() {
        Random random = new Random();
        diceValue = random.nextInt(6) + 1; // Generates 1-6
        diceResultLabel.setText("Dice: " + diceValue);

        moveToken();
    }

    private void moveToken() {
        // Remove token from current position
        JPanel currentSquare = (JPanel) boardPanel.getComponent(tokenPosition);
        currentSquare.remove(token);
        currentSquare.revalidate(); // Important: revalidate the panel
        currentSquare.repaint();

        // Calculate and update the new token position (handle wrapping around the board)
        tokenPosition = (tokenPosition + diceValue) % 64; // Modulo to wrap around
        if (tokenPosition < 0) {
            tokenPosition = 63 + tokenPosition; // Handle negative modulo results if needed
        }

        // Add token to the new position
        JPanel newSquare = (JPanel) boardPanel.getComponent(tokenPosition);
        newSquare.add(token);
        newSquare.revalidate();
        newSquare.repaint();
    }



    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ChessBoardWithDice());
    }
}
