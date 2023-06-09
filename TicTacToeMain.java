import javax.swing.JFrame;

  public class TicTacToeMain
  {
  public static void main(String[] args) {
        JFrame frame = new JFrame("TicTacToe");
       
        TicTacToe gamePanel = new TicTacToe(); //Create object
        frame.add(gamePanel); //Add the game panel
        frame.pack(); //Display the game panel based on dimensions set
        frame.setVisible(true); //Display game panel
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //Close panel when click 'X'
    }
}