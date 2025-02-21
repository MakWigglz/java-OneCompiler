import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Polygon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class ChessBoardWithDice extends JFrame {
    private final Map<String, String> subjectParagraphs;
    private final JButton diceButton;
    private final JPanel boardPanel;
    private final JButton[][] boardSquares;
    private int tokenX = 0;
    private int tokenY = 0;

    public ChessBoardWithDice() {
        subjectParagraphs = new HashMap<>();
        loadParagraphs();
        diceButton = new JButton("Roll Dice");
        boardPanel = new JPanel(new GridLayout(8, 8));
        boardSquares = new JButton[8][8];
        initializeUI();
    }

    private void loadParagraphs() {
        subjectParagraphs.put("0,0", "Paragraph for square 0,0");
        subjectParagraphs.put("0,1", "Paragraph for square 0,1");
        // Add more paragraphs as needed
    }

    private void initializeUI() {
        setTitle("Chess Board with Dice");
        setSize(600, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                JButton square = new JButton();
                square.setBackground(getRandomColor());
                String key = i + "," + j;
                if (subjectParagraphs.containsKey(key)) {
                    square.setToolTipText(subjectParagraphs.get(key));
                }
                boardSquares[i][j] = square;
                boardPanel.add(square);
            }
        }

        diceButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                rollDice();
            }
        });

        add(boardPanel, BorderLayout.CENTER);
        add(diceButton, BorderLayout.NORTH);
        add(new CustomToken(), BorderLayout.SOUTH);
    }

    private Color getRandomColor() {
        return new Color((int) (Math.random() * 0x1000000));
    }

    private void rollDice() {
        Random rand = new Random();
        int diceRoll = rand.nextInt(6) + 1;
        moveToken(diceRoll);
    }

    private void moveToken(int steps) {
        tokenX = (tokenX + steps) % 8;
        tokenY = (tokenY + (tokenX + steps) / 8) % 8;
        repaint();
    }

    private class CustomToken extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            int[] xPoints = {10, 20, 30, 20};
            int[] yPoints = {30, 10, 30, 50};
            Polygon tokenShape = new Polygon(xPoints, yPoints, xPoints.length);
            g.setColor(Color.RED);
            g.fillPolygon(tokenShape);
            g.setColor(Color.BLACK);
            g.drawString("Token", tokenX * 70 + 35, tokenY * 70 + 35);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ChessBoardWithDice frame = new ChessBoardWithDice();
            frame.setVisible(true);
        });
    }
}
