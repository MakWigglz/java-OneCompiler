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
    private final JLabel token;
    private String[] subjects = {
        "Astronomy", "Biology", "Chemistry", "Physics", "Geology", "Mathematics", "Computer Science", "Artificial Intelligence",
        "Medicine", "Psychology", "Engineering", "Environmental Science", "Climate Change", "Ecology", "Genetics", "Quantum Physics",
        "Ancient History", "Medieval History", "Modern History", "World War I", "World War II", "The Cold War", "Political Science", "Economics",
        "Sociology", "Anthropology", "Archaeology", "Geography", "History of Art", "History of Music", "Philosophy", "Religion",
        "Poetry", "Drama", "Novels", "Short Stories", "Literary Criticism", "Painting", "Sculpture", "Architecture", "Music Theory",
        "Film Studies", "Photography", "Dance", "Theatre", "The Internet", "Mobile Technology", "Space Exploration", "Renewable Energy",
        "Nanotechnology", "Biotechnology", "The Printing Press", "The Steam Engine", "The Automobile", "The Airplane", "The Television",
        "Mammals", "Birds", "Reptiles", "Amphibians", "Fish", "Insects", "Plants", "Fungi"
    };

    public ChessBoardWithDice() {
        setTitle("Chess Board with Dice");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Board Panel
        boardPanel = new JPanel(new GridLayout(8, 8));
        for (int i = 0; i < 64; i++) {
            JPanel square = new JPanel(new BorderLayout());
            square.setBackground((i + i / 8) % 2 == 0 ? Color.WHITE : Color.LIGHT_GRAY); // Checkerboard pattern
            javax.swing.JLabel subjectLabel = new javax.swing.JLabel(subjects[i], SwingConstants.CENTER);
            subjectLabel.setFont(new Font("Arial", Font.PLAIN, 10));
            square.add(subjectLabel, BorderLayout.CENTER);
            boardPanel.add(square);
        }

        // Token
        token = new JLabel("T");  // Or use an image icon
        token.setFont(new Font("Arial", Font.BOLD, 20));
        tokenPosition = 0; // Start at the first square (index 0)
        JPanel startSquare = (JPanel) boardPanel.getComponent(tokenPosition);
        startSquare.add(token, BorderLayout.NORTH);

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
        newSquare.add(token, BorderLayout.NORTH);
        newSquare.revalidate();
        newSquare.repaint();

        // Display the subject of the current square
        String currentSubject = subjects[tokenPosition];
        JOptionPane.showMessageDialog(this, "You landed on: " + currentSubject);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ChessBoardWithDice());
    }
}
