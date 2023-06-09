import java.awt.Dimension;
import java.awt.Color;
import java.awt.event.*;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.ImageIcon;

public class TicTacToe extends JPanel implements ActionListener {

    JButton jButton; 

    //Grid Settings
    int lineWidth = 14; // Lines width
    int lineLength = 280; // Lines length
    int x = 15, y = 100; // First line position
    int offset = 95; // Box width
    int a = 0; // For drawing XO
    int b = 5; // For drawing XO
    int selectX = 0; // Square X selected
    int selectY = 0; // Square O selected

    //Variables
    boolean gameDone = false; // Game over when = true
    boolean playX; // If true then X turn, if not then O turn
    int winner = -1; // 0 if X wins, 1 if O wins, -1 if no winner yet
    int P1wins = 0, P2wins = 0; // Win counter
    int[][] grid = new int[3][3]; // 3x3 grid, 0 = empty box, 1 = X in box, 2 = O in box

     //Colours in Game
     Color gameBox = new Color(255, 255, 255); //Box background colour
     Color infoColor = new Color(255, 255, 255);
     Color gameLines = new Color(0, 0, 0);
     Color gameInfo = new Color(0, 0, 0);
     
    //Constructor
    public TicTacToe() {
        Dimension size = new Dimension(420, 300); // Set Window Size
        setMaximumSize(size);
        setMinimumSize(size);
        setPreferredSize(size);
        jButton = new JButton("Play Again"); // Button text
        jButton.addActionListener(this); // Add ActionListener
        jButton.setBounds(0, 20, 0, 10); // Set button location
        addMouseListener(new XOListener()); // Add MouserListener
        add(jButton); // Add button
        resetGame();} // Call method to reset game

    //Draws crucial parts for game
    public void paintComponent(Graphics page) {
        super.paintComponent(page);
        drawBoard(page); //Draw game board
        drawGame(page); //Draw X and O
        drawUI(page); //Draw info section
    }

    //Draws game board details (Colour, positions)
    public void drawBoard(Graphics page) {
        setBackground(gameBox);
        page.setColor(gameLines);
        page.fillRoundRect(x, y, lineLength, lineWidth, 5, 30);
        page.fillRoundRect(x, y + offset, lineLength, lineWidth, 5, 30);
        page.fillRoundRect(y, x, lineWidth, lineLength, 30, 5);
        page.fillRoundRect(y + offset, x, lineWidth, lineLength, 30, 5);
    }

    //Draws game info (Colour, info panel)
    public void drawUI(Graphics page) {
        page.setColor(gameInfo); //Set  text colour
        page.fillRect(300, 0, 120, 300); //Set rectangle position
        Font font = new Font("Helvetica", Font.PLAIN, 20); //Set text font
        page.setFont(font);

        //Display total win for players
        page.setColor(infoColor);
        page.drawString("Total Win(s)", 307, 50);
        page.drawString("= " + P1wins, 362, 100);
        page.drawString("= " + P2wins, 362, 143);

        //Display X image on info panel
        ImageIcon IconX = new ImageIcon("whitex.png");
        Image ImageX = IconX.getImage();
        Image newImageX = ImageX.getScaledInstance(27, 27, java.awt.Image.SCALE_SMOOTH);
        ImageIcon newIconX = new ImageIcon(newImageX);
        page.drawImage(newIconX.getImage(), 44 + offset * 1 + 190, 80 + offset * 0, null);

        //Display O image on info panel
        page.setColor(infoColor);
        page.fillOval(43 + 190 + offset, 120, 30, 30);
        page.setColor(gameLines);
        page.fillOval(49 + 190 + offset, 126, 19, 19);

        //Set font and colour for which player turn
        page.setColor(infoColor);
        Font font1 = new Font("Times New Roman", Font.BOLD, 18);
        page.setFont(font1);

        //Display the winner text or draw
        if (gameDone) {
            if (winner == 1) { // x
                page.drawString("The winner is", 310, 150);
                page.drawImage(ImageX, 335, 160, null);
            } else if (winner == 2) { // o
                page.drawString("The winner is", 310, 150);
                page.setColor(infoColor);
                page.fillOval(332, 160, 50, 50);
                page.setColor(gameLines);
                page.fillOval(342, 170, 30, 30);
            } else if (winner == 3) { // tie
                page.drawString("It's a Draw :(", 312, 190);
            }
        } else {
            Font font2 = new Font("Times New Roman", Font.BOLD, 20);
            page.setFont(font2);
            page.drawString("", 350, 160);
            if (playX) {
                page.drawString("X 's Turn", 325, 180);
            } else {
                page.drawString("O 's Turn", 325, 180);
            }
        }
    }

    //Draws X and O images on game board
    public void drawGame(Graphics page) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (grid[i][j] == 0) {
                } 
                else if (grid[i][j] == 1) {
                    ImageIcon IconX = new ImageIcon("blackx.png");
                    Image ImageX = IconX.getImage();
                    page.drawImage(ImageX, 30 + offset * i, 30 + offset * j, null);
                } 
                else if (grid[i][j] == 2) {
                    ImageIcon IconO = new ImageIcon("blacko.png");
                    Image oImg = IconO.getImage();
                    page.drawImage(oImg, 30 + offset * i, 30 + offset * j, null);
                }
            }
        }
        repaint(); //Do draw and erase X and O on board
    }

    //Check if there is a winner
    public void checkWinner() {
        if (gameDone == true) {
            System.out.print("gameDone");
            return;
        }
        //Vertical grid check
        int temp = -1;
        if ((grid[0][0] == grid[0][1])
                && (grid[0][1] == grid[0][2])
                && (grid[0][0] != 0)) {
            temp = grid[0][0];
        } else if ((grid[1][0] == grid[1][1])
                && (grid[1][1] == grid[1][2])
                && (grid[1][0] != 0)) {
            temp = grid[1][1];
        } else if ((grid[2][0] == grid[2][1])
                && (grid[2][1] == grid[2][2])
                && (grid[2][0] != 0)) {
            temp = grid[2][1];

        //Horizontal grid check
        } else if ((grid[0][0] == grid[1][0])
                && (grid[1][0] == grid[2][0])
                && (grid[0][0] != 0)) {
            temp = grid[0][0];
        } else if ((grid[0][1] == grid[1][1])
                && (grid[1][1] == grid[2][1])
                && (grid[0][1] != 0)) {
            temp = grid[0][1];
        } else if ((grid[0][2] == grid[1][2])
                && (grid[1][2] == grid[2][2])
                && (grid[0][2] != 0)) {
            temp = grid[0][2];

        //Diagonal grid check
        } else if ((grid[0][0] == grid[1][1])
                && (grid[1][1] == grid[2][2])
                && (grid[0][0] != 0)) {
            temp = grid[0][0];
        } else if ((grid[0][2] == grid[1][1])
                && (grid[1][1] == grid[2][0])
                && (grid[0][2] != 0)) {
            temp = grid[0][2];
        } else {

        //Check for draw
            boolean noWinner = false;
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (grid[i][j] == 0) {
                        noWinner = true;
                        break;
                    }
                }
            }
            if (noWinner == false) {
                temp = 3;
            }
        }

        //Increment score for winner
        if (temp > 0) {
            winner = temp;
            if (winner == 1) {
                P1wins++;
                System.out.println("winner is X!");
            } else if (winner == 2) {
                P2wins++;
                System.out.println("winner is O!");
            } else if (winner == 3) {
                System.out.println("It's a tie :(");
            }
            gameDone = true;
            getJButton().setVisible(true);
        }
    }

    //Method to create new game
    public void resetGame() {
        playX = true;
        winner = -1;
        gameDone = false;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                grid[i][j] = 0;
            }
        }
        getJButton().setVisible(false); //Hide play again button
    }

    public JButton getJButton() {
        return jButton;
    }
    public void setPlayXWins(int a) {
        P1wins = a;
    }
    public void setPlayOWins(int a) {
        P2wins = a;
    }

    private class XOListener implements MouseListener {

        //Mouse interaction on board based on range set and player's turn
        public void mouseClicked(MouseEvent event) {
            selectX = -1;
            selectY = -1;
            if (gameDone == false) {
                a = event.getX();
                b = event.getY();
                int selectX = 0, selectY = 0;

                //Place to draw X on board
                if (a > 12 && a < 99) {
                    selectX = 0;
                } else if (a > 103 && a < 195) {
                    selectX = 1;
                } else if (a > 200 && a < 287) {
                    selectX = 2;
                } else {
                    selectX = -1;
                }

                //Place to draw O on board
                if (b > 12 && b < 99) {
                    selectY = 0;
                } else if (b > 103 && b < 195) {
                    selectY = 1;
                } else if (b > 200 && b < 287) {
                    selectY = 2;
                } else {
                    selectY = -1;
                }

                //Check if grid is occupied
                if (selectX != -1 && selectY != -1) {
                    if (grid[selectX][selectY] == 0) {
                        if (playX) {
                            grid[selectX][selectY] = 1;
                            playX = false;
                        } else {
                            grid[selectX][selectY] = 2;
                            playX = true;
                        }
                        checkWinner();
                        System.out.println(" CLICK= x:" + a + ",y: " + b + "; selectX,selectY: " + selectX + "," + selectY);
                    }
                } 

                //Message if player click outside of grid
                else {
                    System.out.println("Please click in the board area only!");
                }
            }
        }
        public void mouseReleased(MouseEvent event) {
        }
        public void mouseEntered(MouseEvent event) {
        }
        public void mouseExited(MouseEvent event) {
        }
        public void mousePressed(MouseEvent event) {
        }
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        resetGame();
    }
}
